package com.example.emailmanagerdagger.data.source.local;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailDao;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.utils.AppExecutors;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmailLocalDataSource implements EmailDataSource {
    private final EmailDao mEmailDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public EmailLocalDataSource(EmailDao emailDao, AppExecutors appExecutors) {
        this.mEmailDao = emailDao;
        this.mAppExecutors = appExecutors;
    }

    @Override
    public void getEmails(Account account, final EmailParams params, final GetEmailsCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Email> emails = mEmailDao.queryBuilder().where(EmailDao.Properties.Category
                        .eq(params.getCategory())).list();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onEmailsLoaded(emails);
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void getEmail(Account account, final EmailParams params, final GetEmailCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                QueryBuilder<Email> queryBuilder = mEmailDao.queryBuilder();
                WhereCondition and = queryBuilder.and(EmailDao.Properties.Id.eq(params.getId()), EmailDao.Properties.Category.eq(params.getCategory()));
                final List<Email> emails = queryBuilder.where(and).list();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (emails.isEmpty()) {
                            callBack.onDataNotAvailable();
                        } else {
                            callBack.onEmailLoaded(emails.get(0));
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void delete(Account account, final Email email, final CallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mEmailDao.delete(email);
                callBack.onSuccess();
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void deleteAll(final EmailParams params) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Email> emails = mEmailDao.queryBuilder().where(EmailDao.Properties.Category
                        .eq(params.getCategory())).list();
                mEmailDao.deleteInTx(emails);
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    public void signRead(final Email email) {
        mAppExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                email.setRead(true);
                mEmailDao.update(email);
            }
        });
    }

    public void saveAll(final List<Email> emails){
        mAppExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mEmailDao.insertInTx(emails);
            }
        });
    }

}
