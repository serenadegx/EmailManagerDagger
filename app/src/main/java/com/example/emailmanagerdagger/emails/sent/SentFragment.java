package com.example.emailmanagerdagger.emails.sent;


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
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emaildetail.EmailDetailActivity;
import com.example.emailmanagerdagger.emails.EmailsContract;
import com.example.emailmanagerdagger.emails.EmailsPresenter;
import com.example.emailmanagerdagger.utils.EasyListAdapter;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class SentFragment extends DaggerFragment implements EmailsContract.View, SwipeRefreshLayout.OnRefreshListener, EasyListAdapter.LoadMoreListener {

    @Inject
    EmailsPresenter mPresenter;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private LinearLayout mEmptyView;
//    private SentListAdapter listAdapter;

//    SentListAdapter.ItemListener mItemListener = new SentListAdapter.ItemListener() {
//        @Override
//        public void onEmailItemClick(Email data) {
//            mPresenter.jumpEmailDetail(data);
//        }
//    };
    private SentEasyListAdapter listAdapter;

    @Inject
    public SentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sent, container, false);
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

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        srl.setRefreshing(true);
        mPresenter.loadSent(EmailApplication.getAccount());
    }

    @Override
    public void onRefresh() {
        listAdapter.setEnable(false);
        mPresenter.refresh();
    }

    @Override
    public void onLoadMoreListener() {
        srl.setEnabled(false);
        mPresenter.loadSentMore();
    }

    @Override
    public void showEmail(List<Email> data) {
        listAdapter.setEnable(true);
        srl.setRefreshing(false);
        mEmptyView.setVisibility(View.INVISIBLE);
        listAdapter.setNewData(data);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showNoEmail() {
        listAdapter.setEnable(true);
        srl.setRefreshing(false);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMoreEnd(List<Email> emails) {
        srl.setEnabled(true);
        listAdapter.loadEnd(emails);
    }

    @Override
    public void showLoadingEmailError(String msg) {
        listAdapter.setEnable(true);
        srl.setRefreshing(false);
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showEmailDetailUi(EmailParams params) {
        EmailDetailActivity.start2EmailDetailActivity(getContext(), params);
    }

    @Override
    public void showNextPageEmail(List<Email> emails) {
        srl.setEnabled(true);
        listAdapter.addData(emails);
    }

    @Override
    public void showLoadMoreError() {
        srl.setEnabled(true);
        listAdapter.loadMoreFailure();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private void setupListAdapter() {
        listAdapter = new SentEasyListAdapter(new ArrayList<Email>(0),rv,this);
//        listAdapter = new SentListAdapter(mItemListener, new ArrayList<Email>(0));
        rv.setAdapter(listAdapter);
        listAdapter.setItemClickListener(new EasyListAdapter.ItemClickListener<Email>() {
            @Override
            public void onItemClickListener(View view, int position, Email item) {
                mPresenter.jumpEmailDetail(item);
            }
        });
    }
}
