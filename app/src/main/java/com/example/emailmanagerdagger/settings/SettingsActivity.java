package com.example.emailmanagerdagger.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.emails.inbox.InboxFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SettingsActivity extends DaggerAppCompatActivity {
    public static final String SETTINGS = "settings";
    public static final String SIGN_TAG = "editSignature";
    public static final String PERSONAL_TAG = "editPersonal";
    public static final String ADVANCED_TAG = "advanced";

    @Inject
    SettingsFragment settingsFragment;
    @Inject
    EditPersonalFragment editPersonalFragment;
    @Inject
    EditSignFragment editSignFragment;
    @Inject
    AdvancedFragment advancedFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();
        replaceFragmentInActivity(SETTINGS);
    }

    @Override
    public void onBackPressed() {
        backSelf();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSelf();
            }
        });
    }

    private void backSelf() {
        if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof SettingsFragment) {
            finish();
        } else {
            toolbar.setTitle("设置");
            replaceFragmentInActivity(SETTINGS);
        }
    }

    public void replaceFragmentInActivity(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            switch (tag) {
                case SETTINGS:
                    fragment = settingsFragment;
                    break;
                case SIGN_TAG:
                    fragment = editSignFragment;
                    break;
                case PERSONAL_TAG:
                    fragment = editPersonalFragment;
                    break;
                default:
                    fragment = settingsFragment;
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        }
    }

    public void replaceFragmentInActivity(Account account) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        Bundle bundle = new Bundle();
        bundle.putParcelable("account", account);
        if (fragment == null) {
            fragment = advancedFragment;
        }
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }


    public static void startForResult2SettingsActivity(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
