package com.victorsima.uber;

import com.victorsima.uber.model.AccessToken;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Uber authentication service interface
 */
public interface UberAuthService {

    /**
     *
     * @param clientId
     * @param clientSecret
     * @param authorizationCode
     * @param grantType
     * @param callback
     */
    @POST("/oauth/token")
    void requestAccessToken(@Query("client_id") String clientId,
                            @Query("client_secret") String clientSecret,
                            @Query("code") String authorizationCode,
                            @Query("grant_type") String grantType,
                            @Query("redirect_uri") String redirectUri,
                            Callback<AccessToken> callback);

    /**
     *
     * @param clientId
     * @param clientSecret
     * @param authorizationCode
     * @param grantType
     * @param redirectUri
     * @return
     */
    @POST("/oauth/token")
    AccessToken requestAccessToken(@Query("client_id") String clientId,
                            @Query("client_secret") String clientSecret,
                            @Query("code") String authorizationCode,
                            @Query("grant_type") String grantType,
                            @Query("redirect_uri") String redirectUri);

}
