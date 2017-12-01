package com.fidelyo.sample

import android.content.Intent
import android.util.Log
import com.app.generated.WorkerVisits

import com.fidelyo.worker.annotations.Job
import com.fidelyo.worker.annotations.Worker

/**
 * Created by bishoy on 9/16/17.
 */
@Worker
class Visits : WorkerVisits() {

    @Job
    override fun workOne(intent: Intent) {
        Log.w("##", "workOne called with extras")
        for (key in intent.extras.keySet()) {
            Log.w("##", "\tkey: " + key + ", value: " + intent.extras.get(key))
        }
    }

    @Job
    override fun workTwo(intent: Intent) {
        Log.w("##", "workTwo called with extras")
        for (key in intent.extras.keySet()) {
            Log.w("##", "\tkey: " + key + ", value: " + intent.extras.get(key))
        }
    }

}
