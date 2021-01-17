package com.example.jobscheduler_ncai_project;

import android.app.job.JobParameters;
import android.util.Log;


public class ExampleJobService extends android.app.job.JobService {

    private static final String TAG = "JobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        System.out.println(" I AM Here");
        Log.d(TAG,"Job Started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters jobParameters)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                {
                    Log.d(TAG, "run: " + i);
                    System.out.println("Run: " + i);
                    if (jobCancelled)
                    {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG,"Job Finished");
                System.out.println("Job Finished");
                jobFinished(jobParameters,false);

            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"Job Cancelled before completion");
        System.out.println("Job Cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
