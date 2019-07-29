package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;

public interface SendEmailContract {
    interface View extends BaseView {
        void show();

        void showSelectAttachmentUi();

        void showSaveDialog();

        void saveSuccess();

        void sendSuccess();

        void handleError(String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void start();

        void send(Account account, Email email, boolean isSave);

        void reply(Account account, Email email, boolean isSave);

        void forward(Account account, Email email, boolean isSave);

        void addAttachment();

        void save2Drafts(Account account, Email email);
    }
}
