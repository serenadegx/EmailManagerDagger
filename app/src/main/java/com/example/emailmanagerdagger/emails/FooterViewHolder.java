package com.example.emailmanagerdagger.emails;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.utils.LoadMoreViewHolder;

public class FooterViewHolder extends LoadMoreViewHolder {

    public FooterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading;
    }

    @Override
    protected int getEndViewId() {
        return R.id.ll_end;
    }

    @Override
    protected int getErrorViewId() {
        return R.id.ll_error;
    }
}