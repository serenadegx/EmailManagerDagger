package com.example.emailmanagerdagger.data.source;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerdagger.data.source.remote.EmailRemoteDataSource;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmailRepository implements EmailDataSource {

    private final EmailLocalDataSource mLocalDataSource;
    private final EmailRemoteDataSource mRemoteDataSource;
    private boolean isCache = true;

    @Inject
    public EmailRepository(@Local EmailLocalDataSource mLocalDataSource, @Remote EmailRemoteDataSource mRemoteDataSource) {
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    @Override
    public void getEmails(final Account account, final EmailParams params, final GetEmailsCallBack callBack) {
        if (isCache) {
            mLocalDataSource.getEmails(account, params, new GetEmailsCallBack() {
                @Override
                public void onEmailsLoaded(List<Email> emails) {
                    callBack.onEmailsLoaded(emails);
                }

                @Override
                public void onDataNotAvailable() {
                    //读取不到本地仓库，再去远程仓库拿，所谓的二级缓存
                    getEmailsFromRemoteDataSource(account, params, callBack);
                }
            });
        } else {
            getEmailsFromRemoteDataSource(account, params, callBack);
        }
    }

    @Override
    public void getEmail(final Account account, final EmailParams params, final GetEmailCallBack callBack) {
        mLocalDataSource.getEmail(account, params, new GetEmailCallBack() {
            @Override
            public void onEmailLoaded(Email email) {
                callBack.onEmailLoaded(email);
            }

            @Override
            public void onDataNotAvailable() {
                mRemoteDataSource.getEmail(account, params, callBack);
            }
        });
    }

    @Override
    public void delete(final Account account, final EmailParams params, final CallBack callBack) {
        mRemoteDataSource.delete(account, params, new CallBack() {
            @Override
            public void onSuccess() {
                mLocalDataSource.delete(account, params, callBack);
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
    }

    public void send(final Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.send(account, email, save2Sent, callBack);
    }

    public void reply(final Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.reply(account, email, save2Sent, callBack);
    }

    public void forward(final Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.forward(account, email, save2Sent, callBack);
    }

    public void save2Drafts(final Account account, Email data, CallBack callBack) {
        mRemoteDataSource.save2Drafts(account, data, callBack);
    }

    public void addEmails(List<Email> data){
        mLocalDataSource.saveAll(data);
    }

    public void deleteAll(){
        mLocalDataSource.deleteAll();
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        isCache = false;
    }

    public void download(Account account, File file, EmailParams params, long total, DownloadCallback callback) {
        mRemoteDataSource.downloadAttachment(account, file, params, total, callback);
    }

    private void getEmailsFromRemoteDataSource(Account account, final EmailParams params, final GetEmailsCallBack callBack) {
        mRemoteDataSource.getEmails(account, params, new GetEmailsCallBack() {
            @Override
            public void onEmailsLoaded(List<Email> emails) {
                refreshLocalDataSource(params, emails);
                callBack.onEmailsLoaded(emails);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(EmailParams params, List<Email> emails) {
        mLocalDataSource.deleteAll(params);
        mLocalDataSource.saveAll(emails);
        isCache = true;
    }
}
