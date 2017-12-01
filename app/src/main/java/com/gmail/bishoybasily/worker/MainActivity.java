package com.gmail.bishoybasily.worker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textViewOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
            }
        });

//        Intent intent = new Intent();
//        intent.putExtra("A", 1);
//        intent.putExtra("B", 2);
//        Visits.executeWorkOneJob(this, intent);

    }
}
