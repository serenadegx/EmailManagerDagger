package com.example.emailmanagerdagger.emaildetail;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Attachment;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.data.EmailParams;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emaildetail.adapter.AttachmentListAdapter;
import com.example.multifile.XRMultiFile;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class EmailDetailActivity extends DaggerAppCompatActivity implements EmailDetailContract.View, View.OnClickListener {

    public static final int REQUEST_PERMISSIONS = 1;

    @Inject
    EmailDetailContract.Presenter mPresenter;
    private EmailParams mEmailParams;
    private TextView receiver, cc, bcc, subject, date, attachmentDes;
    private LinearLayout mAttachmentView;
    private RecyclerView rv;
    private Button reply, forward, delete;
    private Toolbar toolbar;
    private WebView webView;
    private AttachmentListAdapter listAdapter;

    AttachmentListAdapter.ItemListener mItemListener = new AttachmentListAdapter.ItemListener() {
        @Override
        public void onEmailItemClick(int index, Attachment item) {
            if (ContextCompat.checkSelfPermission(EmailDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (!item.isDownload()) {
                    File dir = new File(Environment.getExternalStorageDirectory(), "EmailManager");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    final File file = new File(dir, item.getFileName());
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    mEmailParams.setIndex(index);
                    mPresenter.downloadAttachment(EmailApplication.getAccount(), file, mEmailParams, item.getTotal());
                } else {
                    XRMultiFile.get().with(EmailDetailActivity.this).custom(new File(Environment.getExternalStorageDirectory(), "/EmailManager")).browse();
                }
            } else {
                ActivityCompat.requestPermissions(EmailDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_detail);
        toolbar = findViewById(R.id.toolbar);
        receiver = findViewById(R.id.tv_receiver);
        cc = findViewById(R.id.tv_cc);
        bcc = findViewById(R.id.tv_bcc);
        subject = findViewById(R.id.tv_subject);
        date = findViewById(R.id.tv_date);
        mAttachmentView = findViewById(R.id.ll_accessory);
        attachmentDes = findViewById(R.id.tv_accessory);
        rv = findViewById(R.id.rv_attachment);
        webView = findViewById(R.id.webView);
        reply = findViewById(R.id.bt_reply);
        forward = findViewById(R.id.bt_forward);
        delete = findViewById(R.id.bt_delete);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new EMDecoration(this, EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));

        setupToolbar();
        setupListAdapter();
        setupListener();
        mEmailParams = getIntent().getParcelableExtra("params");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPresenter.getEmail(EmailApplication.getAccount(), mEmailParams);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void showEmailDetail(Email email) {
        toolbar.setTitle(TextUtils.isEmpty(email.getPersonal()) ? email.getFrom() : email.getPersonal());
        receiver.append(email.getTo());
        cc.setVisibility(TextUtils.isEmpty(email.getCc()) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(email.getCc())) {
            cc.append(email.getCc());
        }
        bcc.setVisibility(TextUtils.isEmpty(email.getBcc()) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(email.getBcc())) {
            bcc.append(email.getBcc());
        }
        subject.append(email.getSubject());
        date.append(email.getDate());
        mAttachmentView.setVisibility(email.isHasAttach() ? View.VISIBLE : View.GONE);
        if (email.isHasAttach()) {
            attachmentDes.append(email.getAttachments().size() + "个附件");
            listAdapter.setNewData(email.getAttachments());
        }
        webView.loadDataWithBaseURL(null, email.getContent(), "text/html", "utf-8", null);

    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteUi() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("是否删除邮件")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mPresenter.delete(mEmailParams);
                    }
                }).show();
    }

    @Override
    public void showLoading(String hint) {
        ProgressDialog dialog = ProgressDialog.show(this, "", hint, false, false);
    }

    @Override
    public void showDeleteSuccess() {
        Snackbar.make(getCurrentFocus(), "删除成功", Snackbar.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showForwardUi() {
        Snackbar.make(getCurrentFocus(), "Forward", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showReplyUi() {
        Snackbar.make(getCurrentFocus(), "Reply", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadProgress(int index, float percent) {
        listAdapter.updateProgress(index, percent);
    }

    @Override
    public void downloadFinish(int index) {
        listAdapter.downloadFinish(index);
    }

    @Override
    public void downloadError(int index) {
        listAdapter.downloadError(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reply:
                mPresenter.reply();
                break;
            case R.id.bt_forward:
                mPresenter.forward();
                break;
            case R.id.bt_delete:
                showDeleteUi();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupListAdapter() {
        listAdapter = new AttachmentListAdapter(mItemListener, new ArrayList<Attachment>(0));
        rv.setAdapter(listAdapter);
    }

    private void setupListener() {
        reply.setOnClickListener(this);
        forward.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    public static void start2EmailDetailActivity(Context context, EmailParams params) {
        context.startActivity(new Intent(context, EmailDetailActivity.class)
                .putExtra("params", params));
    }
}
