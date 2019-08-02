package com.example.emailmanagerdagger.emails;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;

import java.util.List;

import javax.inject.Inject;

public class EmailsPresenter implements EmailsContract.Presenter, EmailDataSource.GetEmailsCallBack {
    private final EmailRepository mEmailRepository;
    private EmailsContract.View mView;
    private EmailParams params;
    private Account mAccount;

    @Inject
    public EmailsPresenter(EmailRepository mEmailRepository) {
        this.mEmailRepository = mEmailRepository;
        params = new EmailParams();
    }

    @Override
    public void loadInbox(Account data) {
        this.mAccount = data;
        params.setCategory(EmailParams.Category.INBOX);
        mEmailRepository.getEmails(data, params, this);
    }

    @Override
    public void loadSent(Account data) {
        this.mAccount = data;
        params.setCategory(EmailParams.Category.SENT);
        mEmailRepository.getEmails(mAccount, params, this);
    }

    @Override
    public void loadDrafts(Account data) {
        this.mAccount = data;
        params.setCategory(EmailParams.Category.DRAFTS);
        mEmailRepository.getEmails(mAccount, params, this);
    }

    @Override
    public void refresh() {
        mEmailRepository.refresh();
        mEmailRepository.getEmails(mAccount, params, this);
    }

    @Override
    public void jumpEmailDetail(Email email) {
        if (mView == null || !mView.isActive()) {
            return;
        }
        EmailParams params = new EmailParams();
        params.setId(email.getMessageId());
        params.setCategory(email.getCategory());
        mView.showEmailDetailUi(params);
    }

    @Override
    public void takeView(EmailsContract.View view) {
        this.mView = view;
    }


    @Override
    public void onEmailsLoaded(List<Email> emails) {
        if (mView == null || !mView.isActive()) {
            return;
        }
        if (emails.isEmpty()) {
            mView.showNoEmail();
        } else {
            mView.showEmail(emails);
        }
    }

    @Override
    public void onDataNotAvailable() {
        mView.showLoadingEmailError("网络错误");
    }

    @Override
    public void dropView() {
        mView = null;
    }


}
