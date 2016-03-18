package com.worldline.engine.dependency.command;

/**
 * Interface definition for a callback to be invoked when the command (task) is executed.
 */
public interface Command {

    /**
     * Callback method to be invoked when the command starts executing.
     */
    void execute();
}
