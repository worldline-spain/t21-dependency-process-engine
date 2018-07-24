package com.worldline.t21dependencysample;

import android.util.Log;

import com.alorma.timeline.TimelineView;
import com.worldline.engine.dependency.command.BaseCommand;
import com.worldline.engine.dependency.invoker.Invoker;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

import java.util.ArrayList;
import java.util.List;

public class T21SampleInvoker extends Invoker<SampleReceiver> {

    interface AdapterListener {
        void notifyAdapterChanged();
    }

    private AdapterListener adapterListener;

    private static final String TAG = "T21";

    private List<Item> items;

    public T21SampleInvoker(SampleReceiver receiver, ProcessFinishedInterface<SampleReceiver> listener, AdapterListener adapterListener) {
        super(Constants.SAMPLE_INVOKER, receiver, listener,
                Constants.INTERNET_CONNECTION_CHECKED,
                Constants.POINT_LOADED);
        items = new ArrayList<>();
        this.adapterListener = adapterListener;
        initialize();
    }

    private void initialize() {
        items.add(new Item(TimelineView.TYPE_START, "Start process"));

        CheckInternetConnectionTask checkInternetConnectionTask = new CheckInternetConnectionTask();
        takeCommand(checkInternetConnectionTask);
        items.add(new Item(checkInternetConnectionTask.getId()));

        GetPointDetailsTask getPointDetailsTask = new GetPointDetailsTask();
        takeCommand(getPointDetailsTask);
        items.add(new Item(getPointDetailsTask.getId()));

        items.add(new Item(TimelineView.TYPE_END, "End process"));
    }

    @Override
    public void log(String message) {
        Log.d(TAG, message);
    }

    @Override
    public void commandFinished(BaseCommand command) {
        super.commandFinished(command);
        for (Item item : items) {
            if (command.getId().equals(item.getId())) {
                if (command.getErrorStates() != null && !command.getErrorStates().isEmpty()) {
                    item.setError(true);
                } else {
                    item.setSuccess(true);
                }
                if (adapterListener != null) {
                    adapterListener.notifyAdapterChanged();
                }
                break;
            }
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
