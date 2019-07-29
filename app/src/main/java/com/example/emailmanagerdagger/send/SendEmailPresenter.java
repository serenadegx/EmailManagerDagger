package com.example.emailmanagerdagger.send;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.source.EmailRepository;

import javax.inject.Inject;

public class SendEmailPresenter implements SendEmailContract.Presenter {
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

    }

    @Override
    public void reply(Account account, Email email, boolean isSave) {

    }

    @Override
    public void forward(Account account, Email email, boolean isSave) {

    }

    @Override
    public void addAttachment() {

    }

    @Override
    public void save2Drafts(Account account, Email email) {

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
