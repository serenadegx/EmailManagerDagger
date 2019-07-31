package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;

import java.io.File;

public interface SendEmailContract {
    interface View extends BaseView {
        void show();

        void showSelectAttachmentUi();

        void showSaveDialog();

        void showWaitingView(String msg);

        void closeWaitingView();

        void saveSuccess();

        void sendSuccess();

        void handleError(String msg);

        void showDownloadDialog();

        void downloadStart(int index);

        void downloadProgress(int index, float percent);

        void downloadFinish(int index);

        void downloadError(int index);
    }

    interface Presenter extends BasePresenter<View> {

        void start();

        void send(Account account, Email email, boolean isSave);

        void reply(Account account, Email email, boolean isSave);

        void forward(Account account, Email email, boolean isSave);

        void addAttachment();

        void downloadAttachment(Account account, File file, EmailParams params, long total);

        void save2Drafts(Account account, Email email);
    }
}
