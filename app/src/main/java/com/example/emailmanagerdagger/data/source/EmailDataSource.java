package com.example.emailmanagerdagger.data.source;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;

import java.util.List;

public interface EmailDataSource {
    interface CallBack {
        void onSuccess();

        void onError();
    }

    interface GetEmailsCallBack {

        void onEmailsLoaded(List<Email> emails);

        void onDataNotAvailable();
    }

    interface GetEmailCallBack {

        void onEmailLoaded(Email email);

        void onDataNotAvailable();
    }

    interface DownloadCallback {

        void onProgress(int index, float percent);

        void onFinish(int index);

        void onError(int index);
    }

    void getEmails(Account account, EmailParams params, GetEmailsCallBack callBack);

    void getEmail(Account account, EmailParams params, GetEmailCallBack callBack);

    void delete(Account account, EmailParams params, CallBack callBack);
}
