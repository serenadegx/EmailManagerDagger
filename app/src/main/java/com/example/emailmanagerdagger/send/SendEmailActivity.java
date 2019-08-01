package com.example.emailmanagerdagger.send;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.example.emailmanagerdagger.emaildetail.EmailDetailActivity;
import com.example.emailmanagerdagger.send.adapter.AttachmentListAdapter;
import com.example.emailmanagerdagger.utils.ThreadPoolFactory;
import com.example.multifile.XRMultiFile;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class SendEmailActivity extends DaggerAppCompatActivity implements SendEmailContract.View {
    public static final int SEND = 1;
    public static final int REPLY = 2;
    public static final int FORWARD = 3;
    public static final int DRAFTS = 4;
    private static final int REQUEST_PERMISSIONS = 715;

    @Inject
    SendEmailContract.Presenter mPresenter;
    private Email mEmail;
    private int mAction;
    private boolean isSave;
    private Toolbar toolbar;
    private EditText receiver, cc, bcc, send, subject, content;
    private RecyclerView rvAttachment;
    private String mPersonal;
    private AttachmentListAdapter listAdapter;
    private List<Attachment> items = new ArrayList<>();

    AttachmentListAdapter.ItemListener mItemListener = new AttachmentListAdapter.ItemListener() {
        @Override
        public void onEmailItemClick(int position, Attachment data) {
            listAdapter.delete(position);
        }
    };
    private LinearLayout mWaitingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        toolbar = findViewById(R.id.toolbar);
        receiver = findViewById(R.id.et_receiver);
        cc = findViewById(R.id.et_copy);
        bcc = findViewById(R.id.et_secret);
        send = findViewById(R.id.et_send);
        subject = findViewById(R.id.et_subject);
        content = findViewById(R.id.et_content);
        rvAttachment = findViewById(R.id.rv_attachment);
        mWaitingView = findViewById(R.id.waiting);

        rvAttachment.setLayoutManager(new LinearLayoutManager(this));
        rvAttachment.addItemDecoration(new EMDecoration(this, EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));

        mEmail = getIntent().getParcelableExtra("email");
        mAction = getIntent().getIntExtra("action", 0);
        isSave = getSharedPreferences("email", MODE_PRIVATE).getBoolean("isSave2Sent", false);
        mPersonal = getSharedPreferences("email", MODE_PRIVATE).getString("personal", "EmailManager");

        setupToolbar();
        setupAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver.setText("1099805713@qq.com");
        mPresenter.takeView(this);
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Email email = new Email();
        email.setPersonal(mPersonal);
        email.setFrom(TextUtils.isEmpty(send.getText()) ? null : send.getText().toString());
        email.setTo(TextUtils.isEmpty(receiver.getText()) ? null : receiver.getText().toString());
        email.setCc(TextUtils.isEmpty(cc.getText()) ? null : cc.getText().toString());
        email.setBcc(TextUtils.isEmpty(bcc.getText()) ? null : bcc.getText().toString());
        email.setSubject(subject.getText().toString());
        email.setContent(content.getText().toString());
        email.setAttachments(items);
        int itemId = item.getItemId();
        if (itemId == R.id.action_send) {
            switch (mAction) {
                case SEND:
                    mPresenter.send(EmailApplication.getAccount(), email, isSave);
                    break;
                case REPLY:
                    email.setMessageId(mEmail.getMessageId());
                    email.setAppend(content.getText().toString());
                    mPresenter.reply(EmailApplication.getAccount(), email, isSave);
                    break;
                case FORWARD:
                    email.setAppend(content.getText().toString());
                    email.setMessageId(mEmail.getMessageId());
                    mPresenter.forward(EmailApplication.getAccount(), email, isSave);
                    break;
                case DRAFTS:
                    mPresenter.send(EmailApplication.getAccount(), email, isSave);
                    break;
            }
        } else if (itemId == R.id.action_attach) {
            mPresenter.addAttachment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 715 && data != null) {
            ArrayList<String> list = XRMultiFile.getSelectResult(data);
            for (String str : list) {
                Attachment attachment = new Attachment();
                attachment.setPath(str);
                attachment.setFileName(str.substring(str.lastIndexOf("/") + 1));
                attachment.setSize(getPrintSize(str));
                attachment.setDownload(true);
                attachment.setEnable(true);
                items.add(attachment);
            }
            listAdapter.setNewData(items);
        }
    }

    @Override
    public void show() {
        String account = EmailApplication.getAccount().getAccount();
        if (mAction == REPLY) {
            receiver.setText(mEmail.getFrom());
            subject.setText("回复:" + mEmail.getSubject());
            content.setText(TextUtils.isEmpty(EmailApplication.getAccount().getRemark()) ? null :
                    "\n" + EmailApplication.getAccount().getRemark());
        } else if (mAction == FORWARD) {
            subject.setText("转发:" + mEmail.getSubject());
            content.setText(TextUtils.isEmpty(EmailApplication.getAccount().getRemark()) ? null :
                    "\n" + EmailApplication.getAccount().getRemark());
            if (mEmail.getAttachments() != null && mEmail.getAttachments().size() > 0) {
                items = mEmail.getAttachments();
                listAdapter.setNewData(items);
                if (shouldDownload(items)) {
                    showDownloadDialog();
                }
            }
        } else if (mAction == DRAFTS) {
            receiver.setText(mEmail.getTo().substring(0, mEmail.getTo().lastIndexOf(";")));
            cc.setText(mEmail.getCc());
            bcc.setText(mEmail.getBcc());
            subject.setText(mEmail.getSubject());
            content.setText(mEmail.getContent());
            if (mEmail.getAttachments() != null && mEmail.getAttachments().size() > 0) {
                items = mEmail.getAttachments();
                listAdapter.setNewData(items);
                if (shouldDownload(items)) {
                    showDownloadDialog();
                }
            }
        }else {

        }
        send.setText(account);
    }

    @Override
    public void showSelectAttachmentUi() {
        XRMultiFile.get().with(this)
                .lookHiddenFile(false)
                .limit(3)
                .select(this, 715);
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
                        email.setPersonal(mPersonal);
                        email.setFrom(TextUtils.isEmpty(send.getText()) ? null : send.getText().toString());
                        email.setTo(TextUtils.isEmpty(receiver.getText()) ? null : receiver.getText().toString());
                        email.setCc(TextUtils.isEmpty(cc.getText()) ? null : cc.getText().toString());
                        email.setBcc(TextUtils.isEmpty(bcc.getText()) ? null : bcc.getText().toString());
                        email.setSubject(subject.getText().toString());
                        email.setContent(content.getText().toString());
                        email.setAttachments(items);
                        mPresenter.save2Drafts(EmailApplication.getAccount(), email);
                    }
                }).show();
    }

    @Override
    public void showWaitingView(String msg) {
        mWaitingView.setVisibility(View.VISIBLE);
        ((TextView) mWaitingView.findViewById(R.id.tv_loading)).setText(msg);
    }

    @Override
    public void closeWaitingView() {
        mWaitingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void saveSuccess() {
        ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(getCurrentFocus(), "保存成功", Snackbar.LENGTH_SHORT).show();
                SystemClock.sleep(500);
                finish();
            }
        });
    }

    @Override
    public void sendSuccess() {
        ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(getCurrentFocus(), "发送成功", Snackbar.LENGTH_SHORT).show();
                SystemClock.sleep(500);
                finish();
            }
        });
    }

    @Override
    public void handleError(String msg) {
        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDownloadDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("转发需要下载邮件中的附件，否则转发无附件")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(SendEmailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            autoDownload();
                        } else {
                            ActivityCompat.requestPermissions(SendEmailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                        }

                    }
                }).show();
    }

    @Override
    public void downloadStart(int index) {
        listAdapter.downloadStart(index);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            autoDownload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private boolean shouldDownload(List<Attachment> items) {
        boolean flag = false;
        for (Attachment attachment : items) {
            if (!attachment.isDownload()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void autoDownload() {
        for (int i = 0; i < items.size(); i++) {
            Attachment item = items.get(i);
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
            EmailParams emailParams = new EmailParams();
            emailParams.setIndex(i);
            emailParams.setId(mEmail.getMessageId());
            emailParams.setCategory(mEmail.getCategory());
            mPresenter.downloadAttachment(EmailApplication.getAccount(), file, emailParams, item.getTotal());
        }
    }

    private void setupAdapter() {
        listAdapter = new AttachmentListAdapter(mItemListener, new ArrayList<Attachment>(0));
        rvAttachment.setAdapter(listAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }
        });
    }

    public static void start2SendEmailActivity(Context context, Email email, int action) {
        context.startActivity(new Intent(context, SendEmailActivity.class)
                .putExtra("email", email)
                .putExtra("action", action));
    }

    public static String getPrintSize(String path) {
        long size = getSize(path);
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + " B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + " KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size * 100 / 1024 % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + " GB";
        }
    }

    public static long getSize(String path) {
        long size = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
            size = fis.getChannel().size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }
}
