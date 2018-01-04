package br.com.badiale.tweetmood.tweet;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.Subscribe;

import br.com.badiale.tweetmood.BuildConfig;
import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.activity.BaseActivity;
import butterknife.BindView;

public class TweetListActivity extends BaseActivity {

    private TweetAdapter tweetAdapter = new TweetAdapter();
    private TweetListViewModel viewModel;

    @BindView(R.id.tweet_list_recycler_view)
    RecyclerView recyclerView;

    public TweetListActivity() {
        super(R.layout.activity_tweet_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tweetAdapter);

        viewModel = ViewModelProviders.of(this).get(TweetListViewModel.class);
        viewModel.getStatuses().observe(this, twitterSearchResultStatuses -> tweetAdapter.update(twitterSearchResultStatuses));

        if (BuildConfig.DEBUG) {
            viewModel.searchUser("ifood");
        }
    }

    @Subscribe
    public void analyseTweet(TweetClickedEvent ev) {
        viewModel.analyse(ev.getTweet());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_list_menu, menu);
        configureSearchView(menu.findItem(R.id.tweet_list_search));
        return super.onCreateOptionsMenu(menu);
    }

    private void configureSearchView(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                viewModel.searchUser(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return false;
            }
        });

    }
}
