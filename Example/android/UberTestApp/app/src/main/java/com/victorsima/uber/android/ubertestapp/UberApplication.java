package com.victorsima.uber.android.ubertestapp;

import android.app.Application;

import com.victorsima.uber.UberClient;

import retrofit.RestAdapter;

/**
 * Created by victorsima on 8/23/14.
 */
public class UberApplication extends Application{

    private UberClient uberClient = new UberClient("v1", BuildConfig.UBER_CLIENT_ID, BuildConfig.UBER_CLIENT_SECRET, RestAdapter.LogLevel.BASIC);

    @Override
    public void onCreate() {
        super.onCreate();
        uberClient.setServerToken(BuildConfig.UBER_CLIENT_SERVER_TOKEN);
    }

    public UberClient getUberClient() {
        return uberClient;
    }
}
