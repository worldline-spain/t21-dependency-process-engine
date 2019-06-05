package com.worldline.engine.dependency.listener;

import com.worldline.engine.dependency.command.BaseCommand;

/**
 * Interface definition for a callback to be invoked when the command (task) has finished.
 */
public interface CommandFinishedListener {

    /**
     * Callback method to be invoked when the command has finished executing.
     *
     * @param command The command that finished
     */
    void commandFinished(BaseCommand command);
}
