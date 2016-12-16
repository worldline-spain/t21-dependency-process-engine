package com.worldline.t21dependencysample;

import android.content.Context;

public class SampleReceiver {

    private Context context;

    private int taskId;

    private String pointTitle;

    public SampleReceiver(Context context) {
        this.context = context;
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
}
