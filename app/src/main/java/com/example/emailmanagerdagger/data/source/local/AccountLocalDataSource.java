package com.example.emailmanagerdagger.data.source.local;

import android.os.SystemClock;
import android.util.Log;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.AccountDao;
import com.example.emailmanagerdagger.data.source.AccountDataSource;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.data.ConfigurationDao;
import com.example.emailmanagerdagger.utils.AppExecutors;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountLocalDataSource implements AccountDataSource {

    private final AppExecutors mAppExecutors;

    private final AccountDao mAccountDao;
    private final ConfigurationDao mConfigurationDao;

    @Inject
    public AccountLocalDataSource(AppExecutors mAppExecutors, AccountDao accountDao, ConfigurationDao configurationDao) {
        this.mAppExecutors = mAppExecutors;
        this.mAccountDao = accountDao;
        this.mConfigurationDao = configurationDao;
        Log.i("mango", "ConfigurationDao:" + mConfigurationDao);
    }

    @Override
    public void add(final Account account, final CallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //清除当前账号状态
                QueryBuilder<Account> queryBuilder = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq("true"));
                List<Account> list = queryBuilder.list();
                if (list != null && list.size() > 0) {
                    for (Account accountDetail : list) {
                        accountDetail.setCur(false);
                    }
                    mAccountDao.updateInTx(list);
                }
                final long id = mAccountDao.insert(account);
                if (id > -1) {
                    callBack.onSuccess();
                } else {
                    callBack.onError("保存账户失败");
                }
//                mAppExecutors.getMainThread().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (id > -1) {
//                            callBack.onSuccess();
//                        } else {
//                            callBack.onError("保存账户失败");
//                        }
//                    }
//                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void deleteAll() {
        mAppExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAccountDao.deleteAll();
            }
        });
    }

    public void update(final Account account, final CallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mAccountDao.update(account);
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess();
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void setCurAccount(final Account account) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Account> accounts = mAccountDao.loadAll();
                for (int i = 0; i < accounts.size(); i++) {
                    Account _account = accounts.get(i);
                    if (_account.equals(account)) {
                        _account.setCur(true);
                    } else {
                        _account.setCur(false);
                    }
                }
                mAccountDao.updateInTx(accounts);
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void getAccounts(final AccountsCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Account> accounts = mAccountDao.loadAll();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (accounts.isEmpty()) {
                            callBack.onDataNotAvailable();
                        } else {
                            callBack.onAccountsLoaded(accounts);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void getAccount(final AccountCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                QueryBuilder<Account> qb = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq("true"));
                final List<Account> data = qb.list();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (data.isEmpty()) {
                            callBack.onDataNotAvailable();
                        } else {
                            callBack.onAccountLoaded(data.get(0));
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void config(final List<Configuration> data, final CallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Configuration> configurations = mConfigurationDao.loadAll();
                if (configurations.isEmpty())
                    mConfigurationDao.insertInTx(data);
                callBack.onSuccess();
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void getConfigs(final ConfigsCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Configuration> configs = mConfigurationDao.loadAll();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (configs.isEmpty()) {
                            callBack.onDataNotAvailable();
                        } else {
                            callBack.onConfigsLoaded(configs);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }
}
