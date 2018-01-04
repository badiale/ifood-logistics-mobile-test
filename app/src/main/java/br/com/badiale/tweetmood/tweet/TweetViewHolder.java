package br.com.badiale.tweetmood.tweet;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.eventbus.EventBusUtils;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

class TweetViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = TweetViewHolder.class.getSimpleName();

    private final SimpleDateFormat twitterDateFormatter;
    private final SimpleDateFormat dateFormatter;

    private TwitterSearchResultStatus tweet;

    @BindView(R.id.tweet_layout)
    ViewGroup layout;

    @BindView(R.id.tweet_profile_picture)
    ImageView profilePicture;

    @BindView(R.id.tweet_user_name)
    TextView userName;

    @BindView(R.id.tweet_text)
    TextView text;

    @BindView(R.id.tweet_date)
    TextView date;

    TweetViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        final String twitterDateFormat = itemView.getContext().getString(R.string.twitter_date_format);
        twitterDateFormatter = new SimpleDateFormat(twitterDateFormat, Locale.US);

        final String dateFormat = itemView.getContext().getString(R.string.date_time_format);
        dateFormatter = new SimpleDateFormat(dateFormat);
    }

    void setTweet(final TwitterSearchResultStatus tweet) {
        this.tweet = tweet;
        updatePicture();
        userName.setText(tweet.getUser().getName());
        text.setText(tweet.getText());
        updateDate();
        updateSentiment();
    }

    private void updatePicture() {
        Picasso.with(itemView.getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .error(R.drawable.ic_person_black_24dp)
                .into(profilePicture);
    }

    private void updateDate() {
        final Date tweetDate = parseDate();
        date.setText(formatDate(tweetDate));
    }

    private Date parseDate() {
        try {
            return twitterDateFormatter.parse(tweet.getCreatedAt());
        } catch (ParseException e) {
            Timber.w(e, "Failed to parse date '%s'", tweet.getCreatedAt());
            return null;
        }
    }

    private String formatDate(final Date tweetDate) {
        if (tweetDate == null) {
            return tweet.getCreatedAt();
        }
        return dateFormatter.format(tweetDate);
    }

    private void updateSentiment() {
        if (tweet.getSentiment() == null) {
            layout.setBackground(null);
            return;
        }

        final Resources resources = itemView.getContext().getResources();
        switch (tweet.getSentiment()) {
            case HAPPY:
                layout.setBackground(resources.getDrawable(R.drawable.happy_background));
                break;
            case NEUTRAL:
                layout.setBackground(resources.getDrawable(R.drawable.neutral_background));
                break;
            case SAD:
                layout.setBackground(resources.getDrawable(R.drawable.sad_background));
                break;
            default:
                throw new IllegalStateException(tweet.getSentiment() + " not configured");
        }
    }

    @OnClick(R.id.tweet_view_card)
    void tweetClicked() {
        EventBusUtils.post(new TweetClickedEvent(tweet));
    }
}
