package com.example.emailmanagerdagger.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class AppExecutors {
    private final Executor mDiskIO;
    private final Executor mNetWorkIO;
    private final Executor mMainThread;

    public AppExecutors(Executor diskIO, Executor netWorkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetWorkIO = netWorkIO;
        this.mMainThread = mainThread;
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getNetWorkIO() {
        return mNetWorkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }

    public static class MainThreadExecutor implements Executor{
        private Handler mainHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainHandler.post(command);
        }
    }
}
