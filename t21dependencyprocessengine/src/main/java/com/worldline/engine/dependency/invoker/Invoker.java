package com.worldline.engine.dependency.invoker;

import com.worldline.engine.dependency.command.BaseCommand;
import com.worldline.engine.dependency.listener.CommandFinishedListener;
import com.worldline.engine.dependency.listener.ProcessFinishedInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used for creating and executing an array of tasks with dependencies
 */
public abstract class Invoker <R> implements CommandFinishedListener {

    private String id;

    private List<BaseCommand> commandList;

    private List<String> currentStates;

    private List<String> requiredStates;

    private List<String> errorStates;

    private boolean processFinished;

    private ProcessFinishedInterface<R> listener;

    private R receiver;

    /**
     * Constructor for the Invoker class
     *
     * @param id             the id of the process which is used to identify the process when it's finished
     * @param receiver       the receiver object that will be passed to all the commands and where the results will be stored
     * @param listener       the callback which needs to be called when the process finishes
     * @param requiredStates an optional array of required states which are used to determine if the process finished successfully
     *                       or no
     */
    public Invoker(String id, R receiver, ProcessFinishedInterface<R> listener, String... requiredStates) {
        this.id = id;
        this.receiver = receiver;
        this.listener = listener;
        this.requiredStates = new ArrayList<String>();
        commandList = new ArrayList<BaseCommand>();
        currentStates = new ArrayList<String>();
        errorStates = new ArrayList<String>();
        processFinished = false;
        Collections.addAll(this.requiredStates, requiredStates);
    }

    /**
     * Constructor for the Invoker class
     *
     * @param id             the id of the process which is used to identify the process when it's finished
     * @param listener       the callback which needs to be called when the process finishes
     * @param requiredStates an optional array of required states which are used to determine if the process finished successfully
     *                       or no
     */
    public Invoker(String id, ProcessFinishedInterface<R> listener, String... requiredStates) {
        this(id, null, listener, requiredStates);
    }

    public void startProcess(String... initialStates) {
        log("Starting process " + id);
        currentStates.clear();
        errorStates.clear();
        Collections.addAll(currentStates, initialStates);
        process();
    }

    public abstract void log(String message);

    public String getId() {
        return id;
    }

    public R getReceiver() {
        return receiver;
    }

    public void setReceiver(R receiver) {
        this.receiver = receiver;
        for (int i = 0; i < commandList.size(); i++) {
            commandList.get(i).setReceiver(receiver);
        }
    }

    public List<String> getErrorStates() {
        return errorStates;
    }

    /**
     * This method gets the command from the command list;
     *
     * @param commandId the ID of the command to be returned;
     *
     * @return the command or null if no command was found
     */
    public BaseCommand getCommandById(String commandId) {
        BaseCommand result = null;
        for (int i = 0; i < commandList.size(); i++) {
            if (commandList.get(i).getId().equals(commandId)) {
                result = commandList.get(i);
                break;
            }
        }
        return result;
    }

    /**
     * This method is used to add a task (command) to the process.
     *
     * @param command the task (command) to be added
     */
    public void takeCommand(BaseCommand command) {
        command.setListener(this);
        command.setReceiver(receiver);
        commandList.add(command);
    }

    /**
     * This method is used to find and execute the first available task.
     * It also determines (based on the current states) if the process has already finished.
     */
    protected void process() {
        BaseCommand current;
        for (int i = 0; i < commandList.size(); i++) {
            current = commandList.get(i);
            if (currentStates.containsAll(current.getUpdatingStates())) {
                log("Command " + current.getId() + " has already been executed.");
                processFinished = true;
            } else {
                if (currentStates.containsAll(current.getRestrictingStates())) {
                    log("Execute command " + current.getId());
                    processFinished = false;
                    commandList.get(i).execute();
                    break;
                } else {
                    log("Command " + current.getId() + " can not be executed yet!");
                }
            }
        }
        if (processFinished) {
            if (currentStates.containsAll(requiredStates)) {
                finishWithSuccess();
            } else {
                finishWithError();
            }
            processFinished = false;
        }
    }

    @Override
    public void commandFinished(BaseCommand command) {
        if (command.getErrorStates().size() > 0) {
            log("Command " + command.getId() + " finished with Error " + command.getErrorStates());
            errorStates.addAll(command.getErrorStates());
            if (currentStates.containsAll(requiredStates)) {
                finishWithSuccess();
            } else {
                errorStates.add("");
                finishWithError();
            }
        } else {
            log("Command " + command.getId() + " finished with Success " + command.getUpdatedStates());
            currentStates.addAll(command.getUpdatedStates());
            process();
        }
    }

    protected void finishWithError() {
        listener.processFinished(ProcessFinishedInterface.ERROR, this);
    }

    protected void finishWithSuccess() {
        listener.processFinished(ProcessFinishedInterface.SUCCESS, this);
    }
}
