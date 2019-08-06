package com.example.emailmanagerdagger.account;

import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.source.AccountDataSource;
import com.example.emailmanagerdagger.data.source.AccountRepository;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;

import java.util.List;

import javax.inject.Inject;

public class AccountPresenter implements AccountContract.Presenter {
    private final AccountRepository mAccountRepository;
    private final EmailRepository mEmailRepository;
    private BaseView mView;

    @Inject
    public AccountPresenter(AccountRepository mAccountRepository, EmailRepository emailRepository) {
        this.mAccountRepository = mAccountRepository;
        this.mEmailRepository = emailRepository;
    }

    @Override
    public void loadEmailCategory() {
        mAccountRepository.getConfigs(new AccountDataSource.ConfigsCallBack() {
            @Override
            public void onConfigsLoaded(List<Configuration> data) {
                if (mView != null) {
                    AccountContract.CategoryView categoryView = (AccountContract.CategoryView) mView;
                    if (categoryView.isActive()) {
                        categoryView.showEmailCategory(data);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void openAccountVerify(Configuration data) {
        if (mView != null) {
            AccountContract.CategoryView categoryView = (AccountContract.CategoryView) mView;
            categoryView.showVerifyUi(data);
        }
    }

    @Override
    public void verify(final Account account) {
        final AccountContract.AccountView accountView = (AccountContract.AccountView) mView;
        mAccountRepository.add(account, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (accountView == null) {
                    return;
                }
                EmailApplication.setAccount(account);
                mEmailRepository.deleteAll(new EmailDataSource.CallBack() {
                    @Override
                    public void onSuccess() {
                        accountView.onVerifySuccess();
                    }

                    @Override
                    public void onError() {
                    }
                });
            }

            @Override
            public void onError(String msg) {
                if (accountView == null) {
                    return;
                }
                accountView.onVerifyFailure(msg);
            }
        });
    }

    @Override
    public void takeView(BaseView view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
