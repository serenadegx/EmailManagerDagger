package com.example.emailmanagerdagger.di;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.emailmanagerdagger.data.AccountDao;
import com.example.emailmanagerdagger.data.ConfigurationDao;
import com.example.emailmanagerdagger.data.DaoMaster;
import com.example.emailmanagerdagger.data.DaoSession;
import com.example.emailmanagerdagger.data.Local;
import com.example.emailmanagerdagger.data.Remote;
import com.example.emailmanagerdagger.data.local.AccountLocalDataSource;
import com.example.emailmanagerdagger.data.remote.AccountRemoteDataSource;
import com.example.emailmanagerdagger.utils.AppExecutors;
import com.example.emailmanagerdagger.utils.DiskIOThreadExecutor;
import com.example.emailmanagerdagger.utils.NetWorkIOThreadExecutor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AccountRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract AccountLocalDataSource provideAccountLocalDataSource(AccountLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract AccountRemoteDataSource provideAccountRemoteDataSource(AccountRemoteDataSource dataSource);


    @Singleton
    @Provides
    static DaoSession provideDs(Application application){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, "email_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    static AccountDao provideAccountDao(DaoSession ds){
        return ds.getAccountDao();
    }

    @Singleton
    @Provides
    static ConfigurationDao provideConfigurationDao(DaoSession ds){
        return ds.getConfigurationDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors(){
        return new AppExecutors(new DiskIOThreadExecutor(),new NetWorkIOThreadExecutor(),
                new AppExecutors.MainThreadExecutor());
    }


}
