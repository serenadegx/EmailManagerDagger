package com.example.emailmanagerdagger.di;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.emailmanagerdagger.data.DaoMaster;
import com.example.emailmanagerdagger.data.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoSessionModule {
    @Singleton
    @Provides
    static DaoSession provideDaoSession(Application application){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, "email_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}
