package com.example.emailmanagerdagger.data.source.remote;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.source.AccountDataSource;
import com.example.emailmanagerdagger.utils.AppExecutors;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

@Singleton
public class AccountRemoteDataSource implements AccountDataSource {
    private final AppExecutors mAppExecutors;

    @Inject
    public AccountRemoteDataSource(AppExecutors executors) {
        mAppExecutors = executors;
    }

    @Override
    public void add(final Account account, final CallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Properties props = System.getProperties();
                props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
                props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
                props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                account.getAccount(), account.getPwd());
                    }
                });
                session.setDebug(true);
                Store store = null;
                try {
                    store = session.getStore(account.getConfig().getReceiveProtocol());
                    store.connect();
                    mAppExecutors.getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess();
                        }
                    });
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                    callBack.onError("未知错误");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    callBack.onError("账号或密码错误");
                } finally {
                    try {
                        if (store != null)
                            store.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mAppExecutors.getNetWorkIO().execute(runnable);

    }
}
