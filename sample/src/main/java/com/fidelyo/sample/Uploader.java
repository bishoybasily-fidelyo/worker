package com.fidelyo.sample;

import android.content.Intent;

import com.app.generated.WorkerUploader;
import com.fidelyo.worker.annotations.Job;
import com.fidelyo.worker.annotations.Worker;

@Worker
public class Uploader extends WorkerUploader {

    @Job
    public void uploadImage(Intent intent) {
        // image uploading logic goes here
    }

    @Job
    public void uploadVideo(Intent intent) {
        // video uploading logic goes here
    }

    @Job
    public void uploadAudio(Intent intent) {
        // audio uploading logic goes here
    }

}
