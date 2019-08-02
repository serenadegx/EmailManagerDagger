package com.example.emailmanagerdagger.settings;

import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.source.AccountDataSource;
import com.example.emailmanagerdagger.data.source.AccountRepository;

import java.util.List;

import javax.inject.Inject;

public class SettingsPresenter implements SettingsContract.Presenter {

    private AccountRepository mRepository;
    private BaseView mView;
    private Account cur;

    @Inject
    public SettingsPresenter(AccountRepository repository) {
        this.mRepository = repository;
        cur = new Account();
    }

    @Override
    public void getSettings() {
        final SettingsContract.SettingsView settingsView = (SettingsContract.SettingsView) mView;
        mRepository.getAccounts(new AccountDataSource.AccountsCallBack() {
            @Override
            public void onAccountsLoaded(List<Account> data) {
                for (Account account : data) {
                    if (account.isCur()) {
                        cur = account;
                        break;
                    }
                }
                if (settingsView == null && !settingsView.isActive()) {
                    return;
                }
                settingsView.showSettingsDetail(data, cur.getPersonal(), cur.getRemark());
            }

            @Override
            public void onDataNotAvailable() {
                settingsView.handleError("获取账户信息失败");
            }
        });
    }

    @Override
    public void editPersonal(String personal) {
        final SettingsContract.EditPersonalView personalView = (SettingsContract.EditPersonalView) mView;
        cur.setPersonal(personal);
        mRepository.update(cur, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (personalView == null && !personalView.isActive()) {
                    return;
                }
                personalView.editSuccess();
            }

            @Override
            public void onError(String msg) {
                if (personalView == null && !personalView.isActive()) {
                    return;
                }
                personalView.handleError(msg);
            }
        });
    }

    @Override
    public void editSign(String sign) {
        final SettingsContract.EditSignView editSignView = (SettingsContract.EditSignView) mView;
        cur.setRemark(sign);
        mRepository.update(cur, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (editSignView == null && !editSignView.isActive()) {
                    return;
                }
                editSignView.editSuccess();
            }

            @Override
            public void onError(String msg) {
                if (editSignView == null && !editSignView.isActive()) {
                    return;
                }
                editSignView.handleError(msg);
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
