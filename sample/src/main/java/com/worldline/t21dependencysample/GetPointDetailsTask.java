package com.worldline.t21dependencysample;

import com.worldline.engine.dependency.command.BaseCommand;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPointDetailsTask extends BaseCommand<SampleReceiver, Point> {

    private static final String ENDPOINT = "http://t21services.herokuapp.com";

    private final PointApiService pointApiService;

    public GetPointDetailsTask(String... processSpecificRestrictions) {
        super(Constants.GET_POINT_TASK, processSpecificRestrictions);
        updatingStates.add(Constants.POINT_LOADED);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pointApiService = retrofit.create(PointApiService.class);
    }

    @Override
    public void execute() {
        super.execute();
        String pointId = Integer.toString(receiver.getTaskId());
        Call<Point> call = pointApiService.getPointById(pointId);
        call.enqueue(new Callback<Point>() {
            @Override
            public void onResponse(Call<Point> call, Response<Point> response) {
                if (response.isSuccessful()) {
                    handleSuccess(response.body());
                } else {
                    handleError();
                }
            }

            @Override
            public void onFailure(Call<Point> call, Throwable t) {
                handleError();
            }
        });
    }

    @Override
    protected void handleSuccess(Point response) {
        updatedStates.add(Constants.POINT_LOADED);
        receiver.setPointTitle(response.getTitle());
        listener.commandFinished(this);
    }

    @Override
    protected void handleError() {
        errorStates.add(Constants.POINT_LOADED);
        listener.commandFinished(this);
    }
}
