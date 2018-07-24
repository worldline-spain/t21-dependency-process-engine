package com.worldline.t21dependencysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.worldline.engine.dependency.invoker.Invoker;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

public class MainActivity extends AppCompatActivity implements ProcessFinishedInterface<SampleReceiver>, T21SampleInvoker.AdapterListener {

    private static final String TAG = "T21";

    private T21SampleInvoker sampleInvoker;

    private RecyclerView recyclerView;

    private TimelineAdapter adapter;

    private SampleReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startProcess();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler);
        adapter = new TimelineAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        receiver = new SampleReceiver(this);
        receiver.setTaskId(13);
        sampleInvoker = new T21SampleInvoker(receiver, this, this);
        adapter.addItems(sampleInvoker.getItems());
    }

    private void startProcess() {
        sampleInvoker.startProcess();
        adapter.start();
    }

    @Override
    public void processFinished(int statusCode, Invoker<SampleReceiver> invoker) {
        if (statusCode == ProcessFinishedInterface.SUCCESS) {
            adapter.finishWithSuccess(invoker.getReceiver().getPointTitle());
            Log.d(TAG, "Success! " + invoker.getReceiver().getPointTitle());
        } else {
            adapter.finishWithError(invoker.getErrorStates().toString());
            Log.d(TAG, "Error! " + invoker.getErrorStates());
        }
    }

    @Override
    public void notifyAdapterChanged() {
        adapter.notifyDataSetChanged();
    }
}
