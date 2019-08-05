package com.example.emailmanagerdagger.data.source;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.data.source.local.AccountLocalDataSource;
import com.example.emailmanagerdagger.data.source.remote.AccountRemoteDataSource;

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

    public void setCurAccount(Account account, CallBack callBack) {
        mAccountLocalDataSource.setCurAccount(account, callBack);
    }

    public void config(List<Configuration> data, CallBack callBack) {
        mAccountLocalDataSource.config(data, callBack);
    }

    public void getConfigs(ConfigsCallBack callBack) {
        mAccountLocalDataSource.getConfigs(callBack);
    }

    public void delete(Account account, CallBack callBack) {
        mAccountLocalDataSource.delete(account, callBack);
    }
}
