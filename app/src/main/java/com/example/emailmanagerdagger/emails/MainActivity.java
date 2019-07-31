package com.example.emailmanagerdagger.emails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emailmanagerdagger.EmailApplication;
import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.emails.drafts.DraftsFragment;
import com.example.emailmanagerdagger.emails.inbox.InboxFragment;
import com.example.emailmanagerdagger.emails.sent.SentFragment;
import com.example.emailmanagerdagger.send.SendEmailActivity;
import com.example.emailmanagerdagger.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    InboxFragment inboxFragment;
    @Inject
    SentFragment sentFragment;
    @Inject
    DraftsFragment draftsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendEmailActivity.start2SendEmailActivity(MainActivity.this, new Email(), SendEmailActivity.SEND);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.textView)).setText(EmailApplication.getAccount().getAccount());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        replaceInboxFragmentInActivity(getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            replaceInboxFragmentInActivity(getSupportFragmentManager());
        } else if (id == R.id.nav_send) {
            replaceSentFragmentInActivity(getSupportFragmentManager());
        } else if (id == R.id.nav_drafts) {
            replaceDraftsFragmentInActivity(getSupportFragmentManager());
        } else if (id == R.id.nav_tools) {
            SettingsActivity.startForResult2SettingsActivity(this);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceInboxFragmentInActivity(FragmentManager fragmentManager) {
        InboxFragment fragment = null;
        if (fragmentManager
                .findFragmentById(R.id.container) instanceof InboxFragment) {
            fragment = (InboxFragment) fragmentManager
                    .findFragmentById(R.id.container);
        }

        if (fragment == null) {
            fragment = inboxFragment;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    private void replaceSentFragmentInActivity(FragmentManager fragmentManager) {
        SentFragment fragment = null;
        if (fragmentManager
                .findFragmentById(R.id.container) instanceof SentFragment) {
            fragment = (SentFragment) fragmentManager
                    .findFragmentById(R.id.container);
        }

        if (fragment == null) {
            fragment = sentFragment;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    private void replaceDraftsFragmentInActivity(FragmentManager fragmentManager) {
        DraftsFragment fragment = null;
        if (fragmentManager
                .findFragmentById(R.id.container) instanceof DraftsFragment) {
            fragment = (DraftsFragment) fragmentManager
                    .findFragmentById(R.id.container);
        }

        if (fragment == null) {
            fragment = draftsFragment;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    public static void start2MainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
