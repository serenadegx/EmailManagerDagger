package com.example.emailmanagerdagger.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emails.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class VerifyActivity extends DaggerAppCompatActivity implements AccountContract.AccountView {
    @Inject
    AccountPresenter mPresenter;
    private EditText name;
    private EditText pwd;
    private Button verify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        name = findViewById(R.id.et_email);
        pwd = findViewById(R.id.et_pwd);
        verify = findViewById(R.id.bt);

        name.setText("1099805713@qq.com");
        pwd.setText("fowzlpckwniyhadg");

        final Configuration config = getIntent().getParcelableExtra("config");
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account();
                account.setConfigId(config.getCategoryId());
                account.setAccount(name.getText().toString());
                account.setPwd(pwd.getText().toString());
                account.setConfig(config);
                account.setCur(true);
                mPresenter.verify(account);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    public void onVerifySuccess() {
        Snackbar.make(getCurrentFocus(), "登陆成功", Snackbar.LENGTH_SHORT).show();
        SystemClock.sleep(500);
        MainActivity.start2MainActivity(this);
    }

    @Override
    public void onVerifyFailure(String msg) {
        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
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

    public static void start2VerifyActivity(Context context, Configuration config) {
        context.startActivity(new Intent(context, VerifyActivity.class)
                .putExtra("config", config));
    }
}
