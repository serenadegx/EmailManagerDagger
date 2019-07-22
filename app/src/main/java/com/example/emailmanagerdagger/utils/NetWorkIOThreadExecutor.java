package com.example.emailmanagerdagger.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

@Singleton
public class NetWorkIOThreadExecutor implements Executor {
    private final Executor mExecutor;

    public NetWorkIOThreadExecutor() {
        long keepAliveTime = 3000;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue workQueue = new ArrayBlockingQueue<>(3);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        mExecutor = new ThreadPoolExecutor(
                3,
                3,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);
//        this.mExecutor = Executors.newFixedThreadPool(3);
    }

    @Override
    public void execute(Runnable command) {
        mExecutor.execute(command);
    }
}
