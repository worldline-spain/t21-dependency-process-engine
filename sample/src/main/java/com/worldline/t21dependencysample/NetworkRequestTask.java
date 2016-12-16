package com.worldline.t21dependencysample;

import com.worldline.engine.dependency.command.BaseCommand;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkRequestTask<R, T> extends BaseCommand<R, T> {

    private static final String ENDPOINT = "http://t21services.herokuapp.com";

    public NetworkRequestTask(String id, String... processSpecificRestrictions) {
        super(id, processSpecificRestrictions);
    }

    protected Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
