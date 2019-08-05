package com.example.emailmanagerdagger.settings;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract SettingsFragment settingsFragment();

    @ViewScoped
    @ContributesAndroidInjector
    abstract EditPersonalFragment editPersonalFragment();

    @ViewScoped
    @ContributesAndroidInjector
    abstract EditSignFragment editSignFragment();

    @ViewScoped
    @ContributesAndroidInjector
    abstract AdvancedFragment advancedFragment();

    @ActivityScoped
    @Binds
    abstract SettingsContract.Presenter settingsPresenter(SettingsPresenter presenter);
}
