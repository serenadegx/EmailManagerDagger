package com.example.emailmanagerdagger;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SplashModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @Binds
    abstract SplashContract.Presenter splashPresenter(SplashPresenter splashPresenter);
}
