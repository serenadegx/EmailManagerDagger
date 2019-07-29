package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SendEmailModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract SendEmailActivity sendEmailActivity();

    @ActivityScoped
    @Binds
    abstract SendEmailContract.Presenter sendEmailPresenter(SendEmailPresenter presenter);
}
