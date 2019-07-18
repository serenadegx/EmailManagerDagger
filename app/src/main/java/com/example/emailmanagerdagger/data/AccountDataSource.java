package com.example.emailmanagerdagger.data;

import java.util.List;

public interface AccountDataSource {
    interface CallBack {
        void onSuccess();

        void onError(String msg);
    }

    interface AccountsCallBack {
        void onAccountsLoaded(List<Account> data);

        void onDataNotAvailable();
    }

    interface AccountCallBack {
        void onAccountLoaded(Account data);

        void onDataNotAvailable();
    }

    interface ConfigsCallBack {
        void onConfigsLoaded(List<Configuration> data);

        void onDataNotAvailable();
    }

    void add(Account account, CallBack callBack);
}
