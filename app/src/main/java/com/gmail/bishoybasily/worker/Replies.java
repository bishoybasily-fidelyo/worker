package com.gmail.bishoybasily.worker;

import android.content.Intent;

import com.app.generated.WorkerReplies;
import com.fidelyo.worker.annotations.Job;
import com.fidelyo.worker.annotations.Worker;

/**
 * Created by bishoy on 9/16/17.
 */
@Worker
public class Replies extends WorkerReplies {

    @Job
    public void start(Intent intent) {

    }

    @Job
    public void stop(Intent intent) {

    }

}
