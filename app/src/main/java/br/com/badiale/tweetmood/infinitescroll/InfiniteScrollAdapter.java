package br.com.badiale.tweetmood.infinitescroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.badiale.tweetmood.R;

public class InfiniteScrollAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LOADING_VIEW_TYPE = 857984654;

    private RecyclerView.Adapter<T> delegate;
    private boolean loading = false;

    public InfiniteScrollAdapter(final RecyclerView.Adapter<T> delegate) {
        this.delegate = delegate;
        delegate.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
    }

    public void setLoading(final boolean loading) {
        if (this.loading != loading) {
            this.loading = loading;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (loading && position == getItemCount() - 1) {
            return LOADING_VIEW_TYPE;
        }
        return delegate.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType != LOADING_VIEW_TYPE) {
            return delegate.onCreateViewHolder(parent, viewType);
        }
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.infinite_scroll_adapter_view_last_item, parent, false);
        return new InfiniteScrollAdapterView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() != LOADING_VIEW_TYPE) {
            delegate.onBindViewHolder((T) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return delegate.getItemCount() + (loading ? 1 : 0);
    }
}
