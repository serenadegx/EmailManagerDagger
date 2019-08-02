package com.example.emailmanagerdagger.settings;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;

import java.util.List;

public interface SettingsContract {
    interface SettingsView extends BaseView {
        boolean isActive();

        void showSettingsDetail(List<Account> data, String personal,String sign);

        void showEditPersonalUi();

        void showEditSignUi();

        void handleError(String msg);
    }

    interface EditPersonalView extends BaseView {
        boolean isActive();

        void bindPersonal();

        void editSuccess();

        void handleError(String msg);
    }

    interface EditSignView extends BaseView {
        boolean isActive();

        void bindSign();

        void editSuccess();

        void handleError(String msg);
    }

    interface Presenter extends BasePresenter<BaseView> {
        void getSettings();

        void editPersonal(String personal);

        void editSign(String sign);

    }
}
