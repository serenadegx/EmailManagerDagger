package com.example.emailmanagerdagger.settings;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkManager;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.emails.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class AdvancedFragment extends DaggerFragment implements SettingsContract.AdvancedView {
    @Inject
    SettingsContract.Presenter mPresenter;

    private EditText etAccount, etPwd, etHost, etPort, etSendHost, etSendPort;
    private Switch safeReceive, safeSend;
    private Button btCur, btCancel;
    private Account mAccount;

    @Inject
    public AdvancedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced, container, false);
        etAccount = root.findViewById(R.id.et_account);
        etPwd = root.findViewById(R.id.et_pwd);
        etHost = root.findViewById(R.id.et_host);
        etPort = root.findViewById(R.id.et_port);
        etSendHost = root.findViewById(R.id.et_send_host);
        etSendPort = root.findViewById(R.id.et_send_port);
        safeReceive = root.findViewById(R.id.safe);
        safeSend = root.findViewById(R.id.safe_send);
        btCur = root.findViewById(R.id.bt_cur);
        btCancel = root.findViewById(R.id.bt_cancel);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.advancedEdit(mAccount);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        bindDetail(mAccount = getArguments().getParcelable("account"));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void bindDetail(Account account) {
        etAccount.setText(account.getAccount());
        etPwd.setText(account.getPwd());
        etHost.setText(account.getConfig().getReceiveHostValue());
        etPort.setText(account.getConfig().getReceivePortValue());
        safeReceive.setChecked(account.getConfig().getReceiveEncryptValue());
        etSendHost.setText(account.getConfig().getSendHostValue());
        etSendPort.setText(account.getConfig().getSendPortValue());
        safeSend.setChecked(account.getConfig().getSendEncryptValue());
    }

    @Override
    public void editSuccess() {
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(SettingsActivity.SETTINGS);
        Snackbar.make(getView(), "修改成功", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setCurSuccess(Account account) {
        EmailApplication.setAccount(account);
        WorkManager.getInstance().cancelAllWork();
        MainActivity.startNewEmailWorker();
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(SettingsActivity.SETTINGS);
        Snackbar.make(getView(), "设置成功", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void cancelSuccess() {
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(SettingsActivity.SETTINGS);
        Snackbar.make(getView(), "已退出", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private void setupListener() {
        btCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setCur(mAccount);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancel(mAccount);
            }
        });
    }
}
