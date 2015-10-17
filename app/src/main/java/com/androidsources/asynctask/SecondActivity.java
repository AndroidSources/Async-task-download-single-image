package com.androidsources.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gowtham Chandrasekar on 12-10-2015.
 */
public class SecondActivity extends AppCompatActivity implements OnClickListener {

    NonUITaskFragment fragment;
    String url = "http://www.androidsources.com/wp-content/uploads/2015/09/android-flashlight-app-tutorial.png";
    ProgressBar loadingSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        if (savedInstanceState == null) {
            fragment = new NonUITaskFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, "TaskFragment").commit();
        } else {
            fragment = (NonUITaskFragment) getSupportFragmentManager().findFragmentByTag("TaskFragment");
        }
        Button downloadButton = (Button) findViewById(R.id.download_image);
        loadingSection = (ProgressBar) findViewById(R.id.progressLayout);
        downloadButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_image:
                fragment.beginTask(url);
                break;
        }

    }

    public void showProgressDialog() {
        if (fragment.myTask != null) {
            if (loadingSection.getVisibility() != View.VISIBLE && loadingSection.getProgress() != loadingSection.getMax()) {
                loadingSection.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgressDialog() {
        if (fragment.myTask != null) {
            if (loadingSection.getVisibility() == View.VISIBLE) {
                loadingSection.setVisibility(View.GONE);
            }
        }
        Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void progressUpdate(int progress) {
        loadingSection.setProgress(progress);
    }

}
