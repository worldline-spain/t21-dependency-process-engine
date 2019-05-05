package com.worldline.t21dependencysample;

import com.worldline.engine.dependency.command.BaseCommand;

import java.util.Calendar;

public class CheckTimeOfDayTask extends BaseCommand<SampleReceiver, Integer> {

    private static final int FIRST_POINT = 1;

    private static final int LAST_POINT = 52;

    public CheckTimeOfDayTask(String... processSpecificRestrictions) {
        super(Constants.CHECK_TIME_OF_DAY_TASK, processSpecificRestrictions);
        updatingStates.add(Constants.TIME_OF_DAY_CHECKED);
    }

    @Override
    public void execute() {
        super.execute();

        Calendar now = Calendar.getInstance();
        int minute = now.get(Calendar.MINUTE);
        if (minute >= FIRST_POINT && minute <= LAST_POINT) {
            receiver.setTaskId(minute);
            handleSuccess(minute);
        } else {
            handleError();
        }
    }

    @Override
    protected void handleSuccess(Integer response) {
        updatedStates.add(Constants.TIME_OF_DAY_CHECKED);
        listener.commandFinished(this);
    }

    @Override
    protected void handleError() {
        errorStates.add(Constants.TIME_OF_DAY_CHECKED);
        listener.commandFinished(this);
    }
}
