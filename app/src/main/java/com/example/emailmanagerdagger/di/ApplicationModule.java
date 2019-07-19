package com.example.emailmanagerdagger.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * 我们使用Dagger.module在APPComponent将Application绑定为Context；
 * 通过Dagger Android，我们不需要将Application的实例传递到任何模块；
 * Application和Activities会被提供在图表里{@link AppComponent}
 */
@Module
public abstract class ApplicationModule {
    @Binds
    abstract Context bindContext(Application application);
}
