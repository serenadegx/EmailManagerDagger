package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;

import java.io.File;

import javax.inject.Inject;

public class SendEmailPresenter implements SendEmailContract.Presenter, EmailDataSource.CallBack {
    private SendEmailContract.View mView;
    private EmailRepository mRepository;

    @Inject
    public SendEmailPresenter(EmailRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void start() {
        mView.show();
    }

    @Override
    public void send(Account account, Email email, boolean isSave) {
        mView.showWaitingView("正在发送...");
        mRepository.send(account, email, isSave, this);
    }

    @Override
    public void reply(Account account, Email email, boolean isSave) {
        mView.showWaitingView("正在回复...");
        mRepository.reply(account, email, isSave, this);
    }

    @Override
    public void forward(Account account, Email email, boolean isSave) {
        mView.showWaitingView("正在转发...");
        mRepository.forward(account, email, isSave, this);
    }

    @Override
    public void addAttachment() {
        mView.showSelectAttachmentUi();
    }

    @Override
    public void downloadAttachment(Account account, File file, EmailParams params, long total) {
        mView.downloadStart(params.getIndex());
        mRepository.download(account, file, params, total, new EmailDataSource.DownloadCallback() {
            @Override
            public void onProgress(int index, float percent) {
                if (mView == null) {
                    return;
                }
                mView.downloadProgress(index, percent);
            }

            @Override
            public void onFinish(int index) {
                if (mView == null) {
                    return;
                }
                mView.downloadFinish(index);
            }

            @Override
            public void onError(int index) {
                if (mView == null) {
                    return;
                }
                mView.downloadError(index);
            }
        });
    }

    @Override
    public void save2Drafts(Account account, Email email) {
        mView.showWaitingView("正在保存...");
        mRepository.save2Drafts(account, email, new EmailDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (mView == null) {
                    return;
                }
                mView.closeWaitingView();
                mView.saveSuccess();
            }

            @Override
            public void onError() {
                if (mView == null) {
                    return;
                }
                mView.closeWaitingView();
                mView.handleError("保存失败");
            }
        });
    }

    @Override
    public void onSuccess() {
        if (mView == null) {
            return;
        }
        mView.closeWaitingView();
        mView.sendSuccess();
    }

    @Override
    public void onError() {
        mView.closeWaitingView();
        mView.handleError("发送失败");
    }

    @Override
    public void takeView(SendEmailContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
