package com.worldline.t21dependencysample;

import android.content.Context;

public class SampleReceiver {

    private Context context;

    private int taskId;

    private String pointTitle;

    private int batteryLevel;

    public SampleReceiver(Context context) {
        this.context = context;
    }

    public void reset() {
        taskId = 0;
        pointTitle = null;
        batteryLevel = 0;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getPointTitle() {
        return pointTitle;
    }

    public void setPointTitle(String pointTitle) {
        this.pointTitle = pointTitle;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
