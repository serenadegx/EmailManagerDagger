package com.example.emailmanagerdagger.emails.inbox;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emails.EmailsContract;
import com.example.emailmanagerdagger.emails.EmailsPresenter;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class InboxFragment extends DaggerFragment implements EmailsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    EmailsPresenter mPresenter;
    private RecyclerView rv;
    private LinearLayout mEmptyView;
    private SwipeRefreshLayout srl;

    InboxListAdapter.ItemListener mItemListener = new InboxListAdapter.ItemListener() {
        @Override
        public void onEmailItemClick(Email data) {

        }
    };
    private InboxListAdapter listAdapter;

    public static Fragment newInstance() {
        return new InboxFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        srl = root.findViewById(R.id.srl);
        rv = root.findViewById(R.id.rv);
        mEmptyView = root.findViewById(R.id.ll_empty);
        srl.setOnRefreshListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new EMDecoration(getContext(), EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        listAdapter = new InboxListAdapter(mItemListener, new ArrayList<Email>(0));
        rv.setAdapter(listAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        srl.setRefreshing(true);
        mPresenter.loadInbox(EmailApplication.getAccount());
    }

    @Override
    public boolean isActive() {
        return isActive();
    }

    @Override
    public void showEmail(List<Email> data) {
        srl.setRefreshing(false);
        listAdapter.setNewData(data);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoEmail() {
        srl.setRefreshing(false);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingEmailError(String msg) {
        srl.setRefreshing(false);
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
