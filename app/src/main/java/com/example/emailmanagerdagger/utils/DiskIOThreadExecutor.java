package com.example.emailmanagerdagger.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

@Singleton
public class DiskIOThreadExecutor implements Executor {
    private final Executor mExecutor;

    public DiskIOThreadExecutor() {
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable command) {
        mExecutor.execute(command);
    }
}
