package com.example.jobscheduler_ncai_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView textView;
    Button btnScheduleJob, btnCancelJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtView);
        btnScheduleJob = (Button) findViewById(R.id.btnScheduleJob);
        btnCancelJob = (Button) findViewById(R.id.btnCancelJob);

        // Schedule Job
        btnScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ComponentName componentName = new ComponentName(this, JobService.class);

                JobInfo info = new JobInfo.Builder(123, new ComponentName(getPackageName(), ExampleJobService.class.getName()))
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(true)
                        .setPeriodic(2 * 60 * 1000) // 2 Minutes
                        .build();

                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "Job Scheduled");
                    textView.setText("Job Scheduled");
                } else {
                    Log.d(TAG, "Job scheduling failed");
                    textView.setText("JOb Scheduling failed");
                }
            }
        });

        // Cancel Job
        btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancel(123);
                Log.d(TAG, "Job Cancelled");
                textView.setText("Job Cancelled");
            }
        });
    }

}