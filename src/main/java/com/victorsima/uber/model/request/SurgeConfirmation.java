package com.victorsima.uber.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/22/15.
 */
public class SurgeConfirmation {

    /**
     * The URL a user must visit to accept surge pricing.
     */
    @Expose
    @SerializedName("href")
    private String href;

    /**
     * The surge confirmation identifier used to make a Request after a user has accepted surge pricing.
     */
    @Expose
    @SerializedName("surge_confirmation_id")
    private String surgeConfirmationId;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSurgeConfirmationId() {
        return surgeConfirmationId;
    }

    public void setSurgeConfirmationId(String surgeConfirmationId) {
        this.surgeConfirmationId = surgeConfirmationId;
    }
}
