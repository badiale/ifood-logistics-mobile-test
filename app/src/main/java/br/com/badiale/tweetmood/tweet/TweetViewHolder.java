package br.com.badiale.tweetmood.tweet;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.eventbus.EventBusUtils;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class TweetViewHolder extends RecyclerView.ViewHolder {

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
                .into(profilePicture);
    }

    private void updateDate() {
        date.setText(tweet.getCreatedAt());
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
