package com.example.emailmanagerdagger.emaildetail;

import com.example.emailmanagerdagger.BasePresenter;
import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;

import java.io.File;

public interface EmailDetailContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showEmailDetail(Email email);

        void showErrorMsg(String msg);

        void showDeleteUi();

        void showLoading(String hint);

        void showDeleteSuccess();

        void showForwardUi();

        void showReplyUi();

        void downloadStart(int index);

        void downloadProgress(int index, float percent);

        void downloadFinish(int index);

        void downloadError(int index);
    }

    interface Presenter extends BasePresenter<View> {
        void getEmail(Account data, EmailParams params);

        void delete(EmailParams params);

        void reply();

        void forward();

        void downloadAttachment(Account account, File file, EmailParams params, long total);
    }
}
