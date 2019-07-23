package com.example.emailmanagerdagger.di;

import com.example.emailmanagerdagger.utils.AppExecutors;
import com.example.emailmanagerdagger.utils.DiskIOThreadExecutor;
import com.example.emailmanagerdagger.utils.NetWorkIOThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppExecutorModule {
    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(), new NetWorkIOThreadExecutor(),
                new AppExecutors.MainThreadExecutor());
    }
}
