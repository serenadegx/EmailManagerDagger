package com.example.emailmanagerdagger;

import android.util.Log;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.AccountDataSource;
import com.example.emailmanagerdagger.data.AccountRepository;
import com.example.emailmanagerdagger.data.Configuration;

import java.util.List;

import javax.inject.Inject;

public class SplashPresenter implements SplashContract.Presenter {

    private AccountRepository mAccountRepository;
    private SplashContract.View mSplashView;

    @Inject
    public SplashPresenter(AccountRepository mAccountRepository) {
        this.mAccountRepository = mAccountRepository;
        Log.i("mango", "AccountRepository:" + mAccountRepository);
    }

    @Override
    public void config(List<Configuration> data) {
        mAccountRepository.config(data);
    }

    @Override
    public void getCurAccount() {
        mAccountRepository.getAccount(new AccountDataSource.AccountCallBack() {
            @Override
            public void onAccountLoaded(Account data) {
                if (mSplashView == null || !mSplashView.isActive()) {
                    return;
                }
                mSplashView.jump2Main();
            }

            @Override
            public void onDataNotAvailable() {
                if (mSplashView == null || !mSplashView.isActive()) {
                    return;
                }
                mSplashView.jump2AddAccount();
            }
        });
    }

    @Override
    public void takeView(SplashContract.View view) {
        this.mSplashView = view;
    }

    @Override
    public void dropView() {
        this.mSplashView = null;
    }
}
