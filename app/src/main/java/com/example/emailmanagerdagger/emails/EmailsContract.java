package com.example.emailmanagerdagger.emails;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;

import java.util.List;

public interface EmailsContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showEmail(List<Email> data);

        void showNoEmail();

        void showLoadingEmailError(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void loadInbox(Account data);

        void loadSent(Account data);

        void loadDrafts(Account data);

        void refresh();
    }
}
