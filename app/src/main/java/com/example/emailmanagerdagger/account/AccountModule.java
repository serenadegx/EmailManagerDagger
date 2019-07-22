package com.example.emailmanagerdagger.account;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AccountModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract EmailCategoryActivity emailCategoryActivity();

    @ViewScoped
    @ContributesAndroidInjector
    abstract VerifyActivity verifyActivity();

    @ActivityScoped
    @Binds
    abstract AccountContract.Presenter accountPresenter(AccountPresenter presenter);
}
