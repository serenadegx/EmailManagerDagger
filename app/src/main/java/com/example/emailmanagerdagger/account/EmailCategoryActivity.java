package com.example.emailmanagerdagger.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.account.adapter.EmailCategoryListAdapter;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.multifile.ui.EMDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class EmailCategoryActivity extends DaggerAppCompatActivity implements AccountContract.CategoryView {

    private Toolbar toolbar;
    private RecyclerView rv;
    private EmailCategoryListAdapter listAdapter;
    @Inject
    AccountPresenter mPresenter;

    EmailCategoryListAdapter.ItemListener mItemListener = new EmailCategoryListAdapter.ItemListener() {
        @Override
        public void onCategoryClick(Configuration config) {
            mPresenter.openAccountVerify(config);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_category);
        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new EMDecoration(this, EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        setupToolbar();
        setupListAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPresenter.loadEmailCategory();
    }

    @Override
    public void showEmailCategory(List<Configuration> data) {
        listAdapter.setNewData(data);
    }

    @Override
    public void showVerifyUi(Configuration data) {
        VerifyActivity.start2VerifyActivity(this, data);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupListAdapter() {
        listAdapter = new EmailCategoryListAdapter(new ArrayList<Configuration>(0), mItemListener);
        rv.setAdapter(listAdapter);
    }

    public static void start2EmailCategoryActivity(Context context) {
        context.startActivity(new Intent(context, EmailCategoryActivity.class));
    }
}
