package com.example.emailmanagerdagger.settings;

import com.example.emailmanagerdagger.BaseView;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.source.AccountDataSource;
import com.example.emailmanagerdagger.data.source.AccountRepository;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;

import java.util.List;

import javax.inject.Inject;

public class SettingsPresenter implements SettingsContract.Presenter {

    private final EmailRepository mEmailRepository;
    private final AccountRepository mRepository;
    private BaseView mView;
    private Account cur;

    @Inject
    public SettingsPresenter(AccountRepository repository, EmailRepository emailRepository) {
        this.mRepository = repository;
        this.mEmailRepository = emailRepository;
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
    public void jumpEditPersonal() {
        final SettingsContract.SettingsView settingsView = (SettingsContract.SettingsView) mView;
        settingsView.showEditPersonalUi();
    }

    @Override
    public void jumpEditSign() {
        final SettingsContract.SettingsView settingsView = (SettingsContract.SettingsView) mView;
        settingsView.showEditSignUi();
    }

    @Override
    public void getPersonal() {
        final SettingsContract.EditPersonalView editPersonalView = (SettingsContract.EditPersonalView) mView;
        mRepository.getAccount(new AccountDataSource.AccountCallBack() {
            @Override
            public void onAccountLoaded(Account data) {
                if (editPersonalView != null && !editPersonalView.isActive()) {
                    return;
                }
                editPersonalView.bindPersonal(data.getPersonal());
            }

            @Override
            public void onDataNotAvailable() {
                if (editPersonalView != null && !editPersonalView.isActive()) {
                    return;
                }
                editPersonalView.handleError("获取失败");
            }
        });
    }

    @Override
    public void getSign() {
        final SettingsContract.EditSignView editSignView = (SettingsContract.EditSignView) mView;
        mRepository.getAccount(new AccountDataSource.AccountCallBack() {
            @Override
            public void onAccountLoaded(Account data) {
                if (editSignView != null && !editSignView.isActive()) {
                    return;
                }
                editSignView.bindSign(data.getRemark());
            }

            @Override
            public void onDataNotAvailable() {
                if (editSignView != null && !editSignView.isActive()) {
                    return;
                }
                editSignView.handleError("获取失败");
            }
        });
    }

    @Override
    public void advancedEdit(Account account) {
        final SettingsContract.AdvancedView advancedView = (SettingsContract.AdvancedView) mView;
        mRepository.update(account, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                advancedView.editSuccess();
            }

            @Override
            public void onError(String msg) {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                advancedView.handleError(msg);
            }
        });
    }

    @Override
    public void setCur(final Account account) {
        final SettingsContract.AdvancedView advancedView = (SettingsContract.AdvancedView) mView;
        mRepository.setCurAccount(account, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                mEmailRepository.deleteAll();
                advancedView.setCurSuccess(account);

            }

            @Override
            public void onError(String msg) {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                advancedView.handleError(msg);
            }
        });
    }

    @Override
    public void cancel(Account account) {
        final SettingsContract.AdvancedView advancedView = (SettingsContract.AdvancedView) mView;
        mRepository.delete(account, new AccountDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                advancedView.cancelSuccess();
            }

            @Override
            public void onError(String msg) {
                if (advancedView != null && !advancedView.isActive()) {
                    return;
                }
                advancedView.handleError(msg);
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
