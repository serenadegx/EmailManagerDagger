package com.example.emailmanagerdagger.send;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.multifile.XRMultiFile;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class SendEmailActivity extends DaggerAppCompatActivity implements SendEmailContract.View {
    @Inject
    SendEmailContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
    }

    @Override
    public void show() {

    }

    @Override
    public void showSelectAttachmentUi() {
        XRMultiFile.get().with(this)
                .lookHiddenFile(false)
                .limit(3)
                .browse();
    }

    @Override
    public void showSaveDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("是否存入草稿箱")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Email email = new Email();
                        mPresenter.save2Drafts(EmailApplication.getAccount(), email);
                    }
                }).show();
    }

    @Override
    public void saveSuccess() {
        Snackbar.make(getCurrentFocus(),"保存成功",Snackbar.LENGTH_SHORT).show();
        SystemClock.sleep(500);
        finish();
    }

    @Override
    public void sendSuccess() {

    }

    @Override
    public void handleError(String msg) {

    }
}
