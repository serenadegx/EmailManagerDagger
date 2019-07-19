package com.example.emailmanagerdagger;

import com.example.emailmanagerdagger.data.AccountRepository;
import com.example.emailmanagerdagger.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class EmailApplication extends DaggerApplication {

    @Inject
    AccountRepository mAccountRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
