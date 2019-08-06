package com.example.emailmanagerdagger.di;

import com.example.emailmanagerdagger.newEmail.NewEmailWorker;

import dagger.Component;

@Component
public interface NewEmailComponent {
    void inject(NewEmailWorker worker);
}
