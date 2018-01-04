package br.com.badiale.tweetmood.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.preference.PreferenceHelper;
import br.com.badiale.tweetmood.tweet.TweetListActivity;
import br.com.badiale.tweetmood.twitter.TwitterSearchResult;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;

public class TweetUpdaterJobService extends JobService {
    private static final String TAG = TweetUpdaterJobService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;
    public static final int WINDOW_START_SECONDS = 29 * 60;
    public static final int WINDOW_END_SECONDS = 31 * 60;

    private PreferenceHelper preferenceHelper;
    private NotificationManager notificationManager;

    public static void schedule(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        dispatcher.schedule(dispatcher.newJobBuilder()
                .setService(TweetUpdaterJobService.class)
                .setTag(TAG)
                .setRecurring(true)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(WINDOW_START_SECONDS, WINDOW_END_SECONDS))
                .build());
    }

    public TweetUpdaterJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        Crashlytics.log(Log.DEBUG, TAG, "Starting job service");
        final TwitterService twitterService = TwitterService.getInstance();
        preferenceHelper = PreferenceHelper.from(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        twitterService.searchTweetsByQueryString(preferenceHelper.getLastUpdateUrl())
                .subscribe(twitterSearchResult -> {
                    notifyNewTweets(twitterSearchResult);
                    preferenceHelper.setLastUpdateUrl(twitterSearchResult.getMetadata().getRefreshUrl());
                    jobFinished(job, true);
                });
        return true;
    }

    private void notifyNewTweets(final TwitterSearchResult twitterSearchResult) {
        final List<TwitterSearchResultStatus> statuses = twitterSearchResult.getStatuses();
        if (statuses.isEmpty()) {
            return;
        }

        final String userName = statuses.get(0).getUser().getName();
        final Notification notification = new NotificationCompat.Builder(this, TAG)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getResources().getString(R.string.tweet_notification_title, userName))
                .setContentIntent(navigateToTweetActivity())
                .setContentText(getResources().getString(R.string.tweet_notification_content))
                .setStyle(createExpandedStyle(statuses))
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private NotificationCompat.Style createExpandedStyle(final List<TwitterSearchResultStatus> statuses) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (int i = 0; i < Math.min(statuses.size(), 3); i++) {
            inboxStyle.addLine(statuses.get(i).getText());
        }
        if (statuses.size() > 3) {
            inboxStyle.setSummaryText(getResources().getString(R.string.tweet_notification_content_count, statuses.size() - 3));
        } else {
            inboxStyle.setSummaryText(getResources().getString(R.string.tweet_notification_go_to_app));
        }
        return inboxStyle;
    }

    private PendingIntent navigateToTweetActivity() {
        Intent resultIntent = new Intent(this, TweetListActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TweetListActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public boolean onStopJob(final JobParameters job) {
        return true;
    }
}
