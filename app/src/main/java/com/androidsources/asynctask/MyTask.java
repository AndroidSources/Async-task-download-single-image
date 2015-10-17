package com.androidsources.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gowtham Chandrasekar on 15-10-2015.
 */
class MyTask extends AsyncTask<String, Integer, Boolean> {

    int contentLength = -1;
    int counter = 0;
    int calculatedProgress = 0;
    Activity activity;

    public MyTask(Activity activity) {
        onAttach(activity);
    }

    public void onAttach(Activity activity) {
        this.activity = activity;
    }

    public void onDetach() {
        activity = null;
    }

    @Override
    protected void onPreExecute() {
        ((SecondActivity) activity).showProgressDialog();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean downloadStatus = false;
        File file = null;
        URL downloadUrl = null;
        HttpURLConnection connection = null;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            downloadUrl = new URL(params[0]);
            connection = (HttpURLConnection) downloadUrl.openConnection();
            inputStream = connection.getInputStream();
            int read = -1;
            file = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() +
                    "/" + Uri.parse(params[0]).getLastPathSegment()));
            fileOutputStream = new FileOutputStream(file);
            contentLength = connection.getContentLength();
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
                counter = counter + read;
                Log.d("calcualting", "contentLenght " + contentLength + " counter " + counter);
                publishProgress(counter);
            }
            downloadStatus = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return downloadStatus;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (activity != null) {
            calculatedProgress = (int) (((double) values[0] / contentLength) * 100);
            ((SecondActivity) activity).progressUpdate(calculatedProgress);
        }


    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        ((SecondActivity) activity).hideProgressDialog();

    }
}