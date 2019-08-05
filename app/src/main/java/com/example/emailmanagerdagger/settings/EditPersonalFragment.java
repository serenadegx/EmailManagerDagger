package com.example.emailmanagerdagger.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.di.ActivityScoped;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class EditPersonalFragment extends DaggerFragment implements SettingsContract.EditPersonalView {
    @Inject
    SettingsContract.Presenter mPresenter;
    private EditText editText;

    @Inject
    public EditPersonalFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_personal, container, false);
        editText = root.findViewById(R.id.et);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.editPersonal(editText.getText().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPresenter.getPersonal();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void bindPersonal(String personal) {
        editText.setText(personal);
    }

    @Override
    public void editSuccess() {
        ((SettingsActivity) getActivity()).replaceFragmentInActivity(SettingsActivity.SETTINGS);
        Snackbar.make(getView(), "修改成功", Snackbar.LENGTH_SHORT).show();
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
}
