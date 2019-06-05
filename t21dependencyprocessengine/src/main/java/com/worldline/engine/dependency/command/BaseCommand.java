package com.worldline.engine.dependency.command;

import com.worldline.engine.dependency.listener.CommandFinishedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand <R, T> implements Command {

    protected String id;

    protected CommandFinishedListener listener;

    protected R receiver;

    protected List<String> restrictingStates;

    protected List<String> updatingStates;

    protected List<String> updatedStates;

    protected List<String> errorStates;

    public BaseCommand(String id, String... processSpecificRestrictions) {
        this.id = id;
        restrictingStates = new ArrayList<String>();
        updatingStates = new ArrayList<String>();
        updatedStates = new ArrayList<String>();
        errorStates = new ArrayList<String>();
        Collections.addAll(restrictingStates, processSpecificRestrictions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setListener(CommandFinishedListener listener) {
        this.listener = listener;
    }

    public R getReceiver() {
        return receiver;
    }

    public void setReceiver(R receiver) {
        this.receiver = receiver;
    }

    public List<String> getRestrictingStates() {
        return restrictingStates;
    }

    public List<String> getUpdatingStates() {
        return updatingStates;
    }

    public List<String> getUpdatedStates() {
        return updatedStates;
    }

    public List<String> getErrorStates() {
        return errorStates;
    }

    protected void handleError() {
        listener.commandFinished(this);
    }

    protected void handleSuccess(T response) {
        listener.commandFinished(this);
    }

    @Override
    public void execute() {
        updatedStates.clear();
        errorStates.clear();
    }
}
