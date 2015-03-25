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

    public static final String SCOPE_PROFILE = "profile";
    public static final String SCOPE_HISTORY_LITE = "history_lite";
    public static final String SCOPE_REQUEST = "request";

    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    /**
     * Exchange this authorization code for an access_token, which will allow you to make requests on
     * behalf of a user. The access_token expires in 30 days. The grant_type may be authorization_code or refresh_token.
     *
     * @param clientId A 32 character string (public)
     * @param clientSecret A 40 character string. DO NOT SHARE. This should not be available on any public facing
     *                     server or web site. If you have been compromised or shared this token accidentally please
     *                     reset your client_secret via our web interface. This will require updating your application
     *                     to use the newly issued client_secret.
     * @param redirectUri
     * @param grantType
     * @param authorizationCode
     * @param refreshToken
     * @return AccessToken
     */
    @POST("/oauth/token")
    AccessToken requestAccessToken(@Query("client_id") String clientId,
                                   @Query("client_secret") String clientSecret,
                                   @Query("redirect_uri") String redirectUri,
                                   @Query("grant_type") String grantType,
                                   @Query("code") String authorizationCode,
                                   @Query("refresh_token") String refreshToken);

    /**
     * @see #requestAccessToken(String, String, String, String, String, String)
     */
    @POST("/oauth/token")
    void requestAccessToken(@Query("client_id") String clientId,
                            @Query("client_secret") String clientSecret,
                            @Query("redirect_uri") String redirectUri,
                            @Query("grant_type") String grantType,
                            @Query("code") String authorizationCode,
                            @Query("refresh_token") String refreshToken,
                            Callback<AccessToken> callback);



}
