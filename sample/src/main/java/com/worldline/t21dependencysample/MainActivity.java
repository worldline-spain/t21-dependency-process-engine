package com.worldline.t21dependencysample;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.worldline.engine.dependency.invoker.Invoker;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

public class MainActivity extends AppCompatActivity implements ProcessFinishedInterface<SampleReceiver>, T21SampleInvoker.AdapterListener {

    private static final String TAG = "T21";

    public static final int PROCESS_RESTART_DELAY = 3000;

    private T21SampleInvoker sampleInvoker;

    private RecyclerView recyclerView;

    private TimelineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startProcess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh && adapter != null && !adapter.isProcessRunning()) {
            adapter.reset();
            sampleInvoker.getReceiver().reset();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startProcess();
                }
            }, PROCESS_RESTART_DELAY);

            Toast.makeText(this, getString(R.string.process_restart), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler);
        adapter = new TimelineAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SampleReceiver receiver = new SampleReceiver(this);
        sampleInvoker = new T21SampleInvoker(receiver, this, this);
        adapter.addItems(sampleInvoker.getItems());
    }

    private void startProcess() {
        adapter.start();
        sampleInvoker.startProcess();
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
