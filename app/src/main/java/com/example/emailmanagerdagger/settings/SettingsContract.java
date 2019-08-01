package com.example.emailmanagerdagger.settings;

import com.example.emailmanagerdagger.BaseView;

public interface SettingsContract {
    interface View extends BaseView {
        boolean isActive();

        void showSettingsDetail();

        void showEditPersonalUi();

        void showEditSignUi();
    }


}
