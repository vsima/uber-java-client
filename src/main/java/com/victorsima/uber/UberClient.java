package com.victorsima.uber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.victorsima.uber.exception.ForbiddenException;
import com.victorsima.uber.exception.UnauthorizedException;
import com.victorsima.uber.model.AccessToken;
import retrofit.*;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by victorsima on 8/20/14.
 */
public class UberClient {

    private String version;
    private String accessToken;
    private String serverToken;
    private String clientId;
    private String clientSecret;
    private UberService apiService;
    private UberAuthService authService;
    private Gson gson;

    public UberClient(String version, String clientId, String clientSecret, RestAdapter.LogLevel logLevel) {
        this(version, clientId, clientSecret, null, logLevel);
    }

    public UberClient(String version, String clientId, String clientSecret, Client client, RestAdapter.LogLevel logLevel) {
        this.version = version;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        OkHttpClient okHttpClient = new OkHttpClient();
        gson = gsonBuilder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.uber.com/" + version)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade requestFacade) {

                        if (hasAccessToken()) {
                            requestFacade.addHeader("Authorization", "Bearer " + accessToken);
                            return;
                        } else if (hasServerToken()) {
                            requestFacade.addHeader("Authorization", "Token " + serverToken);
                        }
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError retrofitError) {
                        switch (retrofitError.getResponse().getStatus()) {
                            case 401: //Unauthorized
                                return new UnauthorizedException();
                            case 403: //Forbidden
                                return new ForbiddenException();
                        }
                        return null;
                    }
                })
                .setLogLevel(logLevel)
                .setClient(client == null ? new OkClient(okHttpClient) : client)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(UberService.class);

        RestAdapter authRestAdapter = new RestAdapter.Builder()
                .setEndpoint("https://login.uber.com")
                .setLogLevel(logLevel)
                .setClient(client == null ? new OkClient(okHttpClient) : client)
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .build();
        authService = authRestAdapter.create(UberAuthService.class);
    }

    public boolean hasAccessToken() {
        return accessToken != null && accessToken.length() > 0;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public boolean hasServerToken() {
        return serverToken != null && serverToken.length() > 0;
    }

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public UberService getApiService() {
        return apiService;
    }

    public void requestAccessToken(String code, final Callback<AccessToken> callback) {
        authService.requestAccessToken(clientId, clientSecret, code, "authorization_code", new Callback<AccessToken>() {
            @Override
            public void success(AccessToken accessToken, Response response) {
                UberClient.this.accessToken = accessToken.getAccessToken();
                callback.success(accessToken, response);
            }

            @Override
            public void failure(RetrofitError error) {
                failure(error);
            }
        });
    }
}
