package com.worldline.t21dependencysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.worldline.engine.dependency.invoker.Invoker;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

public class MainActivity extends AppCompatActivity implements ProcessFinishedInterface<SampleReceiver> {

    private static final String TAG = "T21";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startProcess();
    }

    private void startProcess() {
        SampleReceiver receiver = new SampleReceiver(this);
        receiver.setTaskId(13);
        T21SampleInvoker sampleInvoker = new T21SampleInvoker(receiver, this);
        sampleInvoker.startProcess();
    }

    @Override
    public void processFinished(int statusCode, Invoker<SampleReceiver> invoker) {
        if (statusCode == ProcessFinishedInterface.SUCCESS ) {
            Log.d(TAG, "Success! " + invoker.getReceiver().getPointTitle());
        } else {
            Log.d(TAG, "Error! " + invoker.getErrorStates());
        }
    }
}
