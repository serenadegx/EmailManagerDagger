package com.example.emailmanagerdagger.emails;

import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.di.ViewScoped;
import com.example.emailmanagerdagger.emails.drafts.DraftsFragment;
import com.example.emailmanagerdagger.emails.inbox.InboxFragment;
import com.example.emailmanagerdagger.emails.sent.SentFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EmailsModule {
    @ViewScoped
    @ContributesAndroidInjector
    abstract InboxFragment inboxFragment();

    @ViewScoped
    @ContributesAndroidInjector
    abstract SentFragment sentFragment();

    @ViewScoped
    @ContributesAndroidInjector
    abstract DraftsFragment draftsFragment();

    @ActivityScoped
    @Binds
    abstract EmailsContract.Presenter emailsPresenter(EmailsPresenter presenter);
}
