package com.example.emailmanagerdagger;

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
