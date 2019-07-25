package com.example.emailmanagerdagger.di;

import com.example.emailmanagerdagger.SplashActivity;
import com.example.emailmanagerdagger.SplashModule;
import com.example.emailmanagerdagger.account.AccountModule;
import com.example.emailmanagerdagger.account.EmailCategoryActivity;
import com.example.emailmanagerdagger.account.VerifyActivity;
import com.example.emailmanagerdagger.emaildetail.EmailDetailActivity;
import com.example.emailmanagerdagger.emaildetail.EmailModule;
import com.example.emailmanagerdagger.emails.EmailsModule;
import com.example.emailmanagerdagger.emails.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract EmailCategoryActivity emailCategoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract VerifyActivity verifyActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EmailsModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EmailModule.class)
    abstract EmailDetailActivity emailDetailActivity();
}
