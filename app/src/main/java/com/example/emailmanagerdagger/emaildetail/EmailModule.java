package com.example.emailmanagerdagger.emaildetail;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EmailModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract EmailDetailActivity emailDetailActivity();

    @ActivityScoped
    @Binds
    abstract EmailDetailContract.Presenter emailPresenter(EmailPresenter presenter);
}
