# Background jobs

[![](https://jitpack.io/v/bishoybasily-fidelyo/messenger.svg)](https://jitpack.io/#bishoybasily-fidelyo/messenger)

## Overview

Easy background tasks.

## Example android kotlin

**Full example**

1- create the worker

``` java
import android.content.Intent;

import com.fidelyo.worker.annotations.Job;
import com.fidelyo.worker.annotations.Worker;

@Worker
public class Uploader {

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
```

2- define your worker as a service in the manifest

``` xml
<service
            android:name="com.fidelyo.sample.Uploader"
            android:exported="false" />
```
            
4- build the project and then extend the generated class

``` java
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
```

5- execute your job

``` java
Intent intent = new Intent();
intent.putExtra("A", 1);
intent.putExtra("B", 2);

Uploader.executeUploadAudioJob(this, intent);

Uploader.executeUploadImageJob(this, intent);

Uploader.executeUploadVideoJob(this, intent);
```