package com.example.emailmanagerdagger.di;

import com.example.emailmanagerdagger.data.AttachmentDao;
import com.example.emailmanagerdagger.data.DaoSession;
import com.example.emailmanagerdagger.data.EmailDao;
import com.example.emailmanagerdagger.data.source.Local;
import com.example.emailmanagerdagger.data.source.Remote;
import com.example.emailmanagerdagger.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerdagger.data.source.remote.EmailRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class EmailRepositoryModule {
    @Singleton
    @Local
    @Binds
    abstract EmailLocalDataSource provideEmailLocalDataSource(EmailLocalDataSource emailLocalDataSource);

    @Singleton
    @Remote
    @Binds
    abstract EmailRemoteDataSource provideEmailRemoteDataSource(EmailRemoteDataSource emailRemoteDataSource);


    @Singleton
    @Provides
    static EmailDao provideEmailDao(DaoSession ds) {
        return ds.getEmailDao();
    }

    @Singleton
    @Provides
    static AttachmentDao provideAttachmentDao(DaoSession ds) {
        return ds.getAttachmentDao();
    }

//    @Singleton
//    @Provides
//    static AppExecutors provideAppExecutors() {
//        return new AppExecutors(new DiskIOThreadExecutor(), new NetWorkIOThreadExecutor(),
//                new AppExecutors.MainThreadExecutor());
//    }
}
