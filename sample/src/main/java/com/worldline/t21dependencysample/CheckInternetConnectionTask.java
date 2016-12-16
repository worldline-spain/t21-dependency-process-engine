package com.worldline.t21dependencysample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.worldline.engine.dependency.command.BaseCommand;

public class CheckInternetConnectionTask extends BaseCommand<SampleReceiver, Boolean> {

    public CheckInternetConnectionTask(String... processSpecificRestrictions) {
        super(Constants.CHECK_INTERNET_CONNECTION_TASK, processSpecificRestrictions);
        updatingStates.add(Constants.INTERNET_CONNECTION_CHECKED);
    }

    @Override
    public void execute() {
        super.execute();
        ConnectivityManager cm = (ConnectivityManager) receiver.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            handleSuccess(netInfo.isConnected());
        } else {
            handleError();
        }
    }

    @Override
    protected void handleSuccess(Boolean response) {
        updatedStates.add(Constants.INTERNET_CONNECTION_CHECKED);
        listener.commandFinished(this);
    }

    @Override
    protected void handleError() {
        errorStates.add(Constants.INTERNET_CONNECTION_CHECKED);
        listener.commandFinished(this);
    }
}
