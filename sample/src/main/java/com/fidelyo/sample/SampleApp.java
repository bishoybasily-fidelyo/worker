package com.fidelyo.sample;

import android.app.Application;

import com.fidelyo.worker.annotations.EnableWorker;

/**
 * Created by bishoy on 12/26/17.
 */
@EnableWorker
public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
