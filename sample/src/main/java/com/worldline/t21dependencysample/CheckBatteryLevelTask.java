package com.worldline.t21dependencysample;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.worldline.engine.dependency.command.BaseCommand;

public class CheckBatteryLevelTask extends BaseCommand<SampleReceiver, Integer> {

    public CheckBatteryLevelTask(String... processSpecificRestrictions) {
        super(Constants.CHECK_BATTERY_LEVEL_TASK, processSpecificRestrictions);
        updatingStates.add(Constants.BATTERY_LEVEL_CHECKED);
    }

    @Override
    public void execute() {
        super.execute();

        int batteryLevel = getBatteryPercentage(receiver.getContext());
        receiver.setBatteryLevel(batteryLevel);

        if (batteryLevel > 5) {
            handleSuccess(batteryLevel);
        } else {
            handleError();
        }
    }

    @Override
    protected void handleSuccess(Integer response) {
        updatedStates.add(Constants.BATTERY_LEVEL_CHECKED);
        if (response > 15) {
            updatedStates.add(Constants.BATTERY_LEVEL_ABOVE_15);
        }
        listener.commandFinished(this);
    }

    @Override
    protected void handleError() {
        errorStates.add(Constants.BATTERY_LEVEL_CHECKED);
        listener.commandFinished(this);
    }

    private static int getBatteryPercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }
}
