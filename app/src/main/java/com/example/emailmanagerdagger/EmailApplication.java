package com.example.emailmanagerdagger;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.source.AccountRepository;
import com.example.emailmanagerdagger.data.source.EmailRepository;
import com.example.emailmanagerdagger.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class EmailApplication extends DaggerApplication {

    private static Account mAccount;

    public static void setAccount(Account account) {
        mAccount = account;
    }

    public static Account getAccount() {
        return mAccount;
    }

    @Inject
    AccountRepository mAccountRepository;

    @Inject
    EmailRepository mEmailRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    public EmailRepository getEmailRepository() {
        return mEmailRepository;
    }
}
