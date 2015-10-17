package com.androidsources.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Gowtham Chandrasekar on 15-10-2015.
 */
public class NonUITaskFragment extends Fragment {
    Activity activity;
    MyTask myTask;



    public void beginTask(String... args){
        myTask=new MyTask(activity);
        myTask.execute(args);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity=(Activity) context;
        }
        if (myTask!=null){
            myTask.onAttach(activity);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (myTask!=null){
            myTask.onDetach();
        }
    }
}
