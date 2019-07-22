package com.example.emailmanagerdagger.di;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.emailmanagerdagger.data.DaoMaster;
import com.example.emailmanagerdagger.data.DaoSession;
import com.example.emailmanagerdagger.data.EmailDao;
import com.example.emailmanagerdagger.data.source.Local;
import com.example.emailmanagerdagger.data.source.Remote;
import com.example.emailmanagerdagger.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerdagger.data.source.remote.EmailRemoteDataSource;
import com.example.emailmanagerdagger.utils.AppExecutors;
import com.example.emailmanagerdagger.utils.DiskIOThreadExecutor;
import com.example.emailmanagerdagger.utils.NetWorkIOThreadExecutor;

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
    static DaoSession provideDs(Application application) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, "email_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    static EmailDao provideEmailDao(DaoSession ds) {
        return ds.getEmailDao();
    }

//    @Singleton
//    @Provides
//    static AppExecutors provideAppExecutors() {
//        return new AppExecutors(new DiskIOThreadExecutor(), new NetWorkIOThreadExecutor(),
//                new AppExecutors.MainThreadExecutor());
//    }
}
