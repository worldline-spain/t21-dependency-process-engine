package com.worldline.t21dependencysample;

import com.alorma.timeline.TimelineView;

public class Item {

    private final int type;

    private final String id;

    private String description;

    private boolean success;

    private boolean error;

    private boolean processFinishedSuccessfully;

    public Item(String id) {
        this.type = TimelineView.TYPE_DEFAULT;
        this.id = id;
        this.description = "";
    }

    public Item(int type, String id) {
        this.type = type;
        this.id = id;
        this.description = "";
    }

    public Item(String id, String description, boolean success, boolean error) {
        this.type = TimelineView.TYPE_DEFAULT;
        this.id = id;
        this.description = description;
        this.success = success;
        this.error = error;
    }

    public Item(int type, String id, String description, boolean success, boolean error) {
        this.type = type;
        this.id = id;
        this.description = description;
        this.success = success;
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isProcessFinishedSuccessfully() {
        return processFinishedSuccessfully;
    }

    public void setProcessFinishedSuccessfully(boolean processFinishedSuccessfully) {
        this.processFinishedSuccessfully = processFinishedSuccessfully;
    }
}
