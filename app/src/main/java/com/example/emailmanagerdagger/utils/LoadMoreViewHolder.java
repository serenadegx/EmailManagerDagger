package com.example.emailmanagerdagger.utils;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class LoadMoreViewHolder extends RecyclerView.ViewHolder {
    View mLoadingView;
    View mEndView;
    View mErrorView;

    public LoadMoreViewHolder(@NonNull View itemView) {
        super(itemView);
        mLoadingView = itemView.findViewById(getLoadingViewId());
        mEndView = itemView.findViewById(getEndViewId());
        mErrorView = itemView.findViewById(getErrorViewId());
    }

    protected abstract @IdRes
    int getLoadingViewId();

    protected abstract @IdRes
    int getEndViewId();

    protected abstract @IdRes
    int getErrorViewId();
}
