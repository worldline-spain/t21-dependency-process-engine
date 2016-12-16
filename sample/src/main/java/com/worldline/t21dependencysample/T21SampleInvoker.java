package com.worldline.t21dependencysample;

import android.util.Log;

import com.worldline.engine.dependency.invoker.Invoker;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

public class T21SampleInvoker extends Invoker<SampleReceiver> {

    private static final String TAG = "T21";

    public T21SampleInvoker(SampleReceiver receiver, ProcessFinishedInterface<SampleReceiver> listener) {
        super(Constants.SAMPLE_INVOKER, receiver, listener,
                Constants.INTERNET_CONNECTION_CHECKED,
                Constants.POINT_LOADED);
        initialize();
    }

    private void initialize() {
        CheckInternetConnectionTask checkInternetConnectionTask = new CheckInternetConnectionTask();
        takeCommand(checkInternetConnectionTask);

        GetPointDetailsTask getPointDetailsTask = new GetPointDetailsTask();
        takeCommand(getPointDetailsTask);
    }

    @Override
    public void log(String message) {
        Log.d(TAG, message);
    }
}
