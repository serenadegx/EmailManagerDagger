package com.example.emailmanagerdagger.data;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Attachment;

import com.example.emailmanagerdagger.data.EmailDao;
import com.example.emailmanagerdagger.data.ConfigurationDao;
import com.example.emailmanagerdagger.data.AccountDao;
import com.example.emailmanagerdagger.data.AttachmentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig emailDaoConfig;
    private final DaoConfig configurationDaoConfig;
    private final DaoConfig accountDaoConfig;
    private final DaoConfig attachmentDaoConfig;

    private final EmailDao emailDao;
    private final ConfigurationDao configurationDao;
    private final AccountDao accountDao;
    private final AttachmentDao attachmentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        emailDaoConfig = daoConfigMap.get(EmailDao.class).clone();
        emailDaoConfig.initIdentityScope(type);

        configurationDaoConfig = daoConfigMap.get(ConfigurationDao.class).clone();
        configurationDaoConfig.initIdentityScope(type);

        accountDaoConfig = daoConfigMap.get(AccountDao.class).clone();
        accountDaoConfig.initIdentityScope(type);

        attachmentDaoConfig = daoConfigMap.get(AttachmentDao.class).clone();
        attachmentDaoConfig.initIdentityScope(type);

        emailDao = new EmailDao(emailDaoConfig, this);
        configurationDao = new ConfigurationDao(configurationDaoConfig, this);
        accountDao = new AccountDao(accountDaoConfig, this);
        attachmentDao = new AttachmentDao(attachmentDaoConfig, this);

        registerDao(Email.class, emailDao);
        registerDao(Configuration.class, configurationDao);
        registerDao(Account.class, accountDao);
        registerDao(Attachment.class, attachmentDao);
    }
    
    public void clear() {
        emailDaoConfig.clearIdentityScope();
        configurationDaoConfig.clearIdentityScope();
        accountDaoConfig.clearIdentityScope();
        attachmentDaoConfig.clearIdentityScope();
    }

    public EmailDao getEmailDao() {
        return emailDao;
    }

    public ConfigurationDao getConfigurationDao() {
        return configurationDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public AttachmentDao getAttachmentDao() {
        return attachmentDao;
    }

}
