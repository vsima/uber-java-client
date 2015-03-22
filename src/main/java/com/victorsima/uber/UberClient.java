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
 * The Uber api client object
 */
public class UberClient {

    private static final String oAuthUri = "https://login.uber.com";
    private static final String apiUri = "https://api.uber.com";
    private static final String sandboxApiUri = "https://sandbox-api.uber.com";

    private String version;
    private String accessToken;
    private String refreshToken;
    private String serverToken;
    private String clientId;
    private String clientSecret;
    private String clientRedirectUri;
    private UberService apiService;
    private UberAuthService authService;
    private Gson gson;

//    /**
//     * Constructor
//     *
//     * @param version
//     * @param clientId
//     * @param clientSecret
//     * @param logLevel
//     */
//    public UberClient(String version, String clientId, String clientSecret, RestAdapter.LogLevel logLevel) {
//        this(version, clientId, clientSecret, null, null, false, logLevel);
//    }
//
//    /**
//     * Constructor
//     * @param version
//     * @param clientId
//     * @param clientSecret
//     * @param client
//     * @param logLevel
//     */
//    public UberClient(String version, String clientId, String clientSecret, Client client, RestAdapter.LogLevel logLevel) {
//        this(version, clientId, clientSecret, null, client, false, logLevel);
//    }

    /**
     * Constructor
     * @param version
     * @param clientId
     * @param clientSecret
     * @param clientRedirectUri
     * @param client
     * @param logLevel
     */
    public UberClient(String version, String clientId, String clientSecret, String clientRedirectUri, Client client, boolean useSandbox, RestAdapter.LogLevel logLevel) {
        this.version = version;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientRedirectUri = clientRedirectUri;

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        OkHttpClient okHttpClient = new OkHttpClient();
        gson = gsonBuilder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint((useSandbox ? sandboxApiUri : apiUri) + "/" + version)
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
                .setEndpoint(oAuthUri)
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UberService getApiService() {
        return apiService;
    }
    public UberAuthService getAuthService() {
        return authService;
    }

    public String getClientRedirectUri() {
        return clientRedirectUri;
    }

    public void setClientRedirectUri(String clientRedirectUri) {
        this.clientRedirectUri = clientRedirectUri;
    }


    //*************
    // oAuth helpers
    //*************

    /**
     * Creates the url used in the initial oAuth flow. Put this url in to a webview.
     * @return oauth authorize url
     */
    public String getAuthorizeUrl(){
        return oAuthUri + "/oauth/authorize?response_type=code&client_id=" + this.clientId;
    }

    /**
     * Method to parse the authorization code from a redirect url
     * @param redirectUri
     * @return authorization code
     */
    public String parseAuthorizationCode(String redirectUri) {
        return redirectUri.replace(clientRedirectUri + "?code=", "");
    }

    public void requestAccessToken(String authorizationCode, final Callback<AccessToken> callback) {
        authService.requestAccessToken(clientId, clientSecret, authorizationCode, "authorization_code",
                clientRedirectUri, new Callback<AccessToken>() {
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
