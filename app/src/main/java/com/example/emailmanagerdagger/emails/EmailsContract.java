package com.example.emailmanagerdagger.emails;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;

import java.util.List;

public interface EmailsContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showEmail(List<Email> data);

        void showNoEmail();

        void loadMoreEnd(List<Email> emails);

        void showLoadingEmailError(String msg);

        void showEmailDetailUi(EmailParams params);

        void showNextPageEmail(List<Email> emails);

        void showLoadMoreError();
    }

    interface Presenter extends BasePresenter<View> {
        void loadInbox(Account data);

        void loadSent(Account data);

        void loadDrafts(Account data);

        void refresh();

        void jumpEmailDetail(Email email);
    }
}
