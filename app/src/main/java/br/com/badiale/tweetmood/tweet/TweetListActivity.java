package br.com.badiale.tweetmood.tweet;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.activity.BaseActivity;
import br.com.badiale.tweetmood.infinitescroll.InfiniteScrollAdapter;
import br.com.badiale.tweetmood.infinitescroll.LastItemVisibleReachedEvent;
import br.com.badiale.tweetmood.infinitescroll.LastItemVisibleScrollListener;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import butterknife.BindView;

public class TweetListActivity extends BaseActivity {

    private TweetAdapter tweetAdapter = new TweetAdapter();
    private InfiniteScrollAdapter<TweetViewHolder> infiniteScrollAdapter = new InfiniteScrollAdapter<>(tweetAdapter);

    private TweetListViewModel viewModel;

    @BindView(R.id.tweet_list_empty_text)
    TextView emptyText;

    @BindView(R.id.tweet_list_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.tweet_list_recycler_view)
    RecyclerView recyclerView;

    public TweetListActivity() {
        super(R.layout.activity_tweet_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(TweetListViewModel.class);
        viewModel.getStatuses().observe(this, this::updateStatus);
        viewModel.isLoading().observe(this, refreshLayout::setRefreshing);
        viewModel.getError().observe(this, this::showError);
        viewModel.hasMorePages().observe(this, this::enableInfiniteScroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(infiniteScrollAdapter);
        refreshLayout.setOnRefreshListener(viewModel::refresh);
    }

    private void enableInfiniteScroll(final Boolean hasMorePages) {
        if (hasMorePages) {
            recyclerView.addOnScrollListener(LastItemVisibleScrollListener.getInstance());
        } else {
            recyclerView.removeOnScrollListener(LastItemVisibleScrollListener.getInstance());
        }
        infiniteScrollAdapter.setLoading(hasMorePages);
    }

    private void updateStatus(final List<TwitterSearchResultStatus> twitterSearchResultStatuses) {
        final boolean hasTweets = !twitterSearchResultStatuses.isEmpty();
        emptyText.setVisibility(hasTweets ? View.GONE : View.VISIBLE);
        recyclerView.setVisibility(!hasTweets ? View.GONE : View.VISIBLE);
        tweetAdapter.update(twitterSearchResultStatuses);
    }

    @Subscribe
    public void analyseTweet(TweetClickedEvent ev) {
        viewModel.analyse(ev.getTweet());
    }

    private void showError(final Integer stringId) {
        if (stringId == null) {
            return;
        }
        Snackbar.make(recyclerView, stringId, Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void loadMoreResults(LastItemVisibleReachedEvent e) {
        viewModel.loadNextPage();
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
