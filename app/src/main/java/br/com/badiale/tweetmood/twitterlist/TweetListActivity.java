package br.com.badiale.tweetmood.twitterlist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import br.com.badiale.tweetmood.BuildConfig;
import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.activity.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class TweetListActivity extends BaseActivity {

    private TweetAdapter tweetAdapter = new TweetAdapter();
    private TweetListViewModel viewModel;

    @BindView(R.id.tweet_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tweet_list_search_text)
    EditText searchText;

    public TweetListActivity() {
        super(R.layout.activity_tweet_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tweetAdapter);

        viewModel = ViewModelProviders.of(this).get(TweetListViewModel.class);
        viewModel.getStatuses().observe(this, twiiterSearchResultStatuses -> tweetAdapter.update(twiiterSearchResultStatuses));

        if (BuildConfig.DEBUG) {
            viewModel.searchUser("ifood");
        }
    }

    @OnClick(R.id.tweet_list_search_button)
    public void search() {
        viewModel.searchUser(searchText.getText().toString());
    }

    @Subscribe
    public void expandTweet(TweetClickedEvent ev) {
        Toast.makeText(this, String.valueOf(ev.getTweet()), Toast.LENGTH_SHORT).show();
    }
}
