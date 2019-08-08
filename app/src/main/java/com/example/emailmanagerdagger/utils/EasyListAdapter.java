package com.example.emailmanagerdagger.utils;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;


public abstract class EasyListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    public static final int LOADING_MORE = 1;
    public static final int LOAD_ERROR = 2;
    public static final int LOAD_COMPLETE = 3;
    public static final int LOAD_END = 4;
    private int mLoadState = LOAD_COMPLETE;
    public List<T> mData;

    private ItemClickListener<T> mItemClickListener;
    private LoadMoreListener mLoadMoreListener;
    private RecyclerView mRecyclerView;
    private boolean isSlidingUpward;
    private boolean enable = true;

    public EasyListAdapter(List<T> data, RecyclerView recyclerView, LoadMoreListener loadMoreListener) {
        this.mData = data;
        this.mRecyclerView = recyclerView;
        this.mLoadMoreListener = loadMoreListener;
        setupScrollListener();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {
            return getNormalViewHolder(viewGroup);
        } else {
            return getLoadMoreViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == TYPE_ITEM) {
            onNormalBindViewHolder(viewHolder, position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null)
                        mItemClickListener.onItemClickListener(v, position, mData.get(position));
                }
            });
        } else {
            LoadMoreViewHolder footerViewHolder = (LoadMoreViewHolder) viewHolder;
            switch (mLoadState) {
                case LOAD_COMPLETE:
                    footerViewHolder.mLoadingView.setVisibility(View.GONE);
                    footerViewHolder.mEndView.setVisibility(View.GONE);
                    footerViewHolder.mErrorView.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    footerViewHolder.mLoadingView.setVisibility(View.VISIBLE);
                    footerViewHolder.mEndView.setVisibility(View.GONE);
                    footerViewHolder.mErrorView.setVisibility(View.GONE);
                    break;
                case LOAD_ERROR:
                    footerViewHolder.mErrorView.setVisibility(View.VISIBLE);
                    footerViewHolder.mLoadingView.setVisibility(View.GONE);
                    footerViewHolder.mEndView.setVisibility(View.GONE);
                    break;
                case LOAD_END:
                    footerViewHolder.mEndView.setVisibility(View.VISIBLE);
                    footerViewHolder.mLoadingView.setVisibility(View.GONE);
                    footerViewHolder.mErrorView.setVisibility(View.GONE);
                    break;
            }
            footerViewHolder.mErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLoadState(LOADING_MORE);
                    if (mLoadMoreListener != null)
                        mLoadMoreListener.onLoadMoreListener();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    protected abstract LoadMoreViewHolder getLoadMoreViewHolder(ViewGroup viewGroup);

    protected abstract RecyclerView.ViewHolder getNormalViewHolder(ViewGroup viewGroup);

    protected abstract void onNormalBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    @SuppressLint("RestrictedApi")
    public void setNewData(List<T> data) {
        setLoadState(LOAD_COMPLETE);
        checkNotNull(data);
        mData = data;
        notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    public void addData(List<T> data) {
        setLoadState(LOAD_COMPLETE);
        checkNotNull(data);
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void loadMoreFailure() {
        setLoadState(LOAD_ERROR);
        notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    public void loadEnd(List<T> data) {
        setLoadState(LOAD_END);
        checkNotNull(data);
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setLoadState(int loadState) {
        this.mLoadState = loadState;
        notifyDataSetChanged();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private void setupScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    if (isSlidingUpward && lastItemPosition == (itemCount - 1) && mLoadState == LOAD_COMPLETE && enable) {
                        setLoadState(LOADING_MORE);
                        if (mLoadMoreListener != null)
                            mLoadMoreListener.onLoadMoreListener();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                isSlidingUpward = dy > 0;
            }
        });
    }


    public interface ItemClickListener<T> {
        void onItemClickListener(View view, int position, T item);
    }

    public interface LoadMoreListener {
        void onLoadMoreListener();
    }
}
