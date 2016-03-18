package com.worldline.engine.dependency.listener;

import com.worldline.engine.dependency.invoker.Invoker;

/**
 * Interface definition for a callback to be invoked when the process has finished.
 */
public interface ProcessFinishedInterface <R> {

    public static int SUCCESS = 200;
    public static int ERROR = 400;

    /**
     * Callback method to be invoked when the process has finished executing.
     *
     * @param statusCode The result of the process. One of
     *                   {@link #SUCCESS} or {@link #ERROR}
     * @param process    The process that finished
     */
    void processFinished(int statusCode, Invoker<R> process);
}
