package com.example.emailmanagerdagger.di;

import android.app.Application;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.data.source.AccountRepository;
import com.example.emailmanagerdagger.data.source.EmailRepository;
import com.example.emailmanagerdagger.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Dagger 的组件，在Application中使用{@link EmailApplication}
 *
 * @Singleton 虽标识为单例，但还是要指定单例的范围。想要实现应用全局的单例，需要在Application中完成创建{@link EmailApplication}
 * {@link ApplicationModule}暴露Application
 * {@link AndroidSupportInjectionModule}是Dagger Android中的一种Module，他帮助我们生成子Component
 */
@Singleton
@Component(modules = {ActivityBindingModule.class,
        AccountRepositoryModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<EmailApplication> {

    AccountRepository getAccountRepository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
