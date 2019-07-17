package com.example.emailmanagerdagger.account;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.emailmanagerdagger.R;

import dagger.android.support.DaggerAppCompatActivity;

public class EmailCategoryActivity extends DaggerAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_category);
    }
}
