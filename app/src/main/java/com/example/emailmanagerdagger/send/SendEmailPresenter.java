package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;

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
        mRepository.send(account, email, isSave, this);
    }

    @Override
    public void reply(Account account, Email email, boolean isSave) {
        mRepository.reply(account, email, isSave, this);
    }

    @Override
    public void forward(Account account, Email email, boolean isSave) {
        mRepository.forward(account, email, isSave, this);
    }

    @Override
    public void addAttachment() {
        mView.showSelectAttachmentUi();
    }

    @Override
    public void save2Drafts(Account account, Email email) {
        mRepository.save2Drafts(account, email, new EmailDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (mView == null) {
                    return;
                }
                mView.saveSuccess();
            }

            @Override
            public void onError() {
                if (mView == null) {
                    return;
                }
                mView.handleError("保存失败");
            }
        });
    }

    @Override
    public void onSuccess() {
        if (mView == null) {
            return;
        }
        mView.sendSuccess();
    }

    @Override
    public void onError() {
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
