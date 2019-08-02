package com.example.emailmanagerdagger.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.example.emailmanagerdagger.settings.adapter.AccountListAdapter;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.emailmanagerdagger.settings.SettingsActivity.PERSONAL_TAG;
import static com.example.emailmanagerdagger.settings.SettingsActivity.SIGN_TAG;

@ActivityScoped
public class SettingsFragment extends DaggerFragment implements SettingsContract.SettingsView {

    @Inject
    SettingsContract.Presenter mPresenter;
    private RecyclerView rv;
    private RelativeLayout editPersonal, editSign;
    private Switch save, remind;
    private AccountListAdapter listAdapter;
    private TextView tvPersonal, tvSign;

    AccountListAdapter.ItemListener mItemListener = new AccountListAdapter.ItemListener() {
        @Override
        public void onEmailItemClick(Account account) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        rv = root.findViewById(R.id.rv);
        editPersonal = root.findViewById(R.id.rl_personal);
        editSign = root.findViewById(R.id.rl_sign);
        save = root.findViewById(R.id.save_toggle);
        remind = root.findViewById(R.id.remind_toggle);
        tvPersonal = root.findViewById(R.id.tv_personal);
        tvSign = root.findViewById(R.id.tv_sign);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new EMDecoration(getContext(), EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
        setupListener();
    }

    private void setupListener() {
        editPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        editSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    private void setupListAdapter() {
        listAdapter = new AccountListAdapter(mItemListener, new ArrayList<Account>(0));
        rv.setAdapter(listAdapter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showSettingsDetail(List<Account> data, String personal, String sign) {
        listAdapter.setNewData(data);
        tvPersonal.setText(personal);
        tvSign.setText(sign);
        save.setChecked(getActivity().getSharedPreferences("email", Context.MODE_PRIVATE)
                .getBoolean("isSave", false));
        remind.setChecked(getActivity().getSharedPreferences("email", Context.MODE_PRIVATE)
                .getBoolean("isRemind", false));
    }

    @Override
    public void showEditPersonalUi() {
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(PERSONAL_TAG);
    }

    @Override
    public void showEditSignUi() {
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(SIGN_TAG);
    }

    @Override
    public void handleError(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
