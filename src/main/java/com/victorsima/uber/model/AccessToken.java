package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Uber Oauth2 AccessToken model obj
 */
public class AccessToken {

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("token_type")
    private String tokenType;

    public AccessToken() {
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setAccessToken(String paramString) {
        this.accessToken = paramString;
    }

    public void setTokenType(String paramString) {
        this.tokenType = paramString;
    }
}