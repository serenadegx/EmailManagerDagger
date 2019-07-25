package com.example.emailmanagerdagger.emaildetail;

import android.os.Environment;

import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Attachment;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.data.source.EmailDataSource;
import com.example.emailmanagerdagger.data.source.EmailRepository;
import com.example.emailmanagerdagger.di.ActivityScoped;

import java.io.File;

import javax.inject.Inject;

@ActivityScoped
public class EmailPresenter implements EmailDetailContract.Presenter {

    private EmailRepository mEmailRepository;
    private EmailDetailContract.View mView;
    private Account mAccount;

    @Inject
    public EmailPresenter(EmailRepository emailRepository) {
        this.mEmailRepository = emailRepository;
    }

    @Override
    public void getEmail(Account data, EmailParams params) {
        this.mAccount = data;
        mEmailRepository.getEmail(data, params, new EmailDataSource.GetEmailCallBack() {
            @Override
            public void onEmailLoaded(Email email) {
                if (mView == null) {
                    return;
                }
                for (Attachment attachment : email.getAttachments()) {
                    attachment.setDownload(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/EmailManager", attachment.getFileName()).exists());
                }
                mView.showEmailDetail(email);
            }

            @Override
            public void onDataNotAvailable() {
                if (mView == null) {
                    return;
                }
                mView.showErrorMsg("网络错误");
            }
        });
    }

    @Override
    public void delete(EmailParams params) {
        mEmailRepository.delete(mAccount, params, new EmailDataSource.CallBack() {
            @Override
            public void onSuccess() {
                if (mView == null) {
                    return;
                }
                mView.showDeleteSuccess();
            }

            @Override
            public void onError() {
                if (mView == null) {
                    return;
                }
                mView.showErrorMsg("删除失败");
            }
        });
    }

    @Override
    public void reply() {
        mView.showReplyUi();
    }

    @Override
    public void forward() {
        mView.showForwardUi();
    }

    @Override
    public void downloadAttachment(Account account, File file, EmailParams params, long total) {
        mEmailRepository.download(account, file, params, total, new EmailDataSource.DownloadCallback() {
            @Override
            public void onProgress(int index, float percent) {
                if (mView == null) {
                    return;
                }
                mView.downloadProgress(index, percent);
            }

            @Override
            public void onFinish(int index) {
                if (mView == null) {
                    return;
                }
                mView.downloadFinish(index);
            }

            @Override
            public void onError(int index) {
                if (mView == null) {
                    return;
                }
                mView.downloadError(index);
            }
        });
    }

    @Override
    public void takeView(EmailDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
