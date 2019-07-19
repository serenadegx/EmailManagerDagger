package com.example.emailmanagerdagger.data;

import android.util.Log;

import com.example.emailmanagerdagger.data.local.AccountLocalDataSource;
import com.example.emailmanagerdagger.data.remote.AccountRemoteDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountRepository implements AccountDataSource {
    private final AccountRemoteDataSource mAccountRemoteDataSource;
    private final AccountLocalDataSource mAccountLocalDataSource;

    @Inject
    public AccountRepository(@Remote AccountRemoteDataSource mAccountRemoteDataSource,
                             @Local AccountLocalDataSource mAccountLocalDataSource) {
        this.mAccountRemoteDataSource = mAccountRemoteDataSource;
        this.mAccountLocalDataSource = mAccountLocalDataSource;
        Log.i("mango", "AccountLocalDataSource:" + mAccountLocalDataSource);
    }

    @Override
    public void add(final Account account, final CallBack callBack) {
        mAccountRemoteDataSource.add(account, new CallBack() {
            @Override
            public void onSuccess() {
                mAccountLocalDataSource.add(account, callBack);
            }

            @Override
            public void onError(String msg) {
                callBack.onError(msg);
            }
        });
    }

    public void update(Account account, CallBack callBack) {
        mAccountLocalDataSource.update(account, callBack);
    }

    public void getAccounts(AccountsCallBack callBack) {
        mAccountLocalDataSource.getAccounts(callBack);
    }

    public void getAccount(AccountCallBack callBack) {
        mAccountLocalDataSource.getAccount(callBack);
    }

    public void setCurAccount(Account account) {
        mAccountLocalDataSource.setCurAccount(account);
    }

    public void config(List<Configuration> data) {
        mAccountLocalDataSource.config(data);
    }
}
