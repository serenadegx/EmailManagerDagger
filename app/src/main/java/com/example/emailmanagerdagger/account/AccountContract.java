package com.example.emailmanagerdagger.account;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Configuration;

import java.util.List;

public interface AccountContract {
    interface AccountView extends BaseView<Presenter> {
        void onVerifySuccess();

        void onVerifyFailure(String msg);

        boolean isActive();
    }

    interface CategoryView extends BaseView<Presenter> {
        void showEmailCategory(List<Configuration> data);

        void showVerifyUi(Configuration data);

        boolean isActive();
    }


    interface Presenter extends BasePresenter<BaseView> {
        void loadEmailCategory();

        void openAccountVerify(Configuration data);

        void verify(Account account);
    }
}
