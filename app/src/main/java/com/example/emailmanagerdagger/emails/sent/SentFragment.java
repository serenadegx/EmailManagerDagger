package com.example.emailmanagerdagger.emails.sent;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emails.EmailsPresenter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class SentFragment extends DaggerFragment {

    @Inject
    EmailsPresenter mPresenter;

    @Inject
    public SentFragment() {
    }

    public static Fragment newInstance() {
        return new SentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sent, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
