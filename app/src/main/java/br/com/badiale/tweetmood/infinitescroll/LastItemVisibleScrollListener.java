package br.com.badiale.tweetmood.infinitescroll;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.badiale.tweetmood.eventbus.EventBusUtils;

public class LastItemVisibleScrollListener extends RecyclerView.OnScrollListener {
    private static final LastItemVisibleScrollListener INSTANCE = new LastItemVisibleScrollListener();

    public static LastItemVisibleScrollListener getInstance() {
        return INSTANCE;
    }

    private LastItemVisibleScrollListener() {
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
            EventBusUtils.post(new LastItemVisibleReachedEvent());
        }
    }
}
