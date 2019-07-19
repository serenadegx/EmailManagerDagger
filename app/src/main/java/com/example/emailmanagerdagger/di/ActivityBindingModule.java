package com.example.emailmanagerdagger.di;

import com.example.emailmanagerdagger.SplashActivity;
import com.example.emailmanagerdagger.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();
}
