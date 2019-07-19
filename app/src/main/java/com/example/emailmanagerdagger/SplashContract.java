package com.example.emailmanagerdagger;

import com.example.emailmanagerdagger.data.Configuration;

import java.util.List;

public interface SplashContract {
    interface View extends BaseView {
        boolean isActive();

        void jump2Main();

        void jump2AddAccount();
    }

    interface Presenter extends BasePresenter<View> {
        void config(List<Configuration> data);

        void getCurAccount();

    }
}
