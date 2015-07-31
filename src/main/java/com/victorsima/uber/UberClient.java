package com.victorsima.uber;

import com.google.gson.*;
import com.squareup.okhttp.OkHttpClient;
import com.victorsima.uber.exception.ForbiddenException;
import com.victorsima.uber.exception.UnauthorizedException;
import com.victorsima.uber.model.AccessToken;
import com.victorsima.uber.model.sandbox.SandboxService;
import retrofit.*;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import java.lang.reflect.Type;
import java.util.Date;

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
    private SandboxService sandboxService;
    private Gson gson;

    /**
     * Constructor
     *
     * @param clientId
     * @param clientSecret
     * @param clientRedirectUri
     * @param logLevel
     */
    public UberClient(String clientId, String clientSecret, String clientRedirectUri, RestAdapter.LogLevel logLevel) {
        this(clientId, clientSecret, clientRedirectUri, null, null, false, logLevel);
    }

    /**
     * Constructor
     *
     * @param serverToken
     * @param logLevel
     */
    public UberClient(String serverToken, RestAdapter.LogLevel logLevel) {
        this(null, null, null, null, null, false, logLevel);
    }

    /**
     * Constructor
     * @param clientId
     * @param clientSecret
     * @param clientRedirectUri
     * @param client
     * @param logLevel
     */
    public UberClient(String clientId, String clientSecret, String clientRedirectUri, String serverToken, Client client, boolean useSandbox, RestAdapter.LogLevel logLevel) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientRedirectUri = clientRedirectUri;
        this.serverToken = serverToken;

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
        OkHttpClient okHttpClient = new OkHttpClient();
        gson = gsonBuilder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(useSandbox ? sandboxApiUri : apiUri)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade requestFacade) {

                        if (hasAccessToken()) {
                            requestFacade.addHeader("Authorization", "Bearer " + getAccessToken());
                            return;
                        } else if (hasServerToken()) {
                            requestFacade.addHeader("Authorization", "Token " + getServerToken());
                        }
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError retrofitError) {
                        if (retrofitError != null && retrofitError.getResponse() != null) {
                            switch (retrofitError.getResponse().getStatus()) {
                                case 401: //Unauthorized
                                    return new UnauthorizedException();
                                case 403: //Forbidden
                                    return new ForbiddenException();
                            }
                        }
                        return retrofitError;
                    }
                })
                .setLogLevel(logLevel)
                .setClient(client == null ? new OkClient(okHttpClient) : client)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(UberService.class);
        if (useSandbox) {
            sandboxService = restAdapter.create(SandboxService.class);
        }

        RestAdapter authRestAdapter = new RestAdapter.Builder()
                .setEndpoint(oAuthUri)
                .setLogLevel(logLevel)
                .setClient(client == null ? new OkClient(okHttpClient) : client)
                .setConverter(new GsonConverter(gson))
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

    public SandboxService getSandboxService() {
        return sandboxService;
    }

    public String getOAuthUri() {
        return oAuthUri;
    }

    public Gson getGson() {
        return gson;
    }
}
