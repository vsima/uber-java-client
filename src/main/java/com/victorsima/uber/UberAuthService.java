package com.victorsima.uber;

import com.victorsima.uber.model.AccessToken;
import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by victorsima on 8/20/14.
 */
public interface UberAuthService {

    @POST("/oauth/token")
    void requestAccessToken(@Query("client_id") String clientId,
                            @Query("client_secret") String clientSecret,
                            @Query("code") String code,
                            @Query("grant_type") String grantType,
                            Callback<AccessToken> callback);

}
