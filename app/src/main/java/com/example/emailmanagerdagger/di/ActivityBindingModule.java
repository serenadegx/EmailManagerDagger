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
import com.example.emailmanagerdagger.send.SendEmailActivity;
import com.example.emailmanagerdagger.send.SendEmailModule;
import com.example.emailmanagerdagger.settings.SettingsActivity;
import com.example.emailmanagerdagger.settings.SettingsModule;

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

    @ActivityScoped
    @ContributesAndroidInjector(modules = SendEmailModule.class)
    abstract SendEmailActivity sendEmailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsActivity settingsActivity();
}
