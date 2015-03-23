package com.victorsima.uber.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.request.SurgeConfirmation;

/**
 * Request Meta model obj
 */
public class Meta {

    @Expose
    @SerializedName("surge_confirmation")
    private SurgeConfirmation surgeConfirmation;

    public SurgeConfirmation getSurgeConfirmation() {
        return surgeConfirmation;
    }

    public void setSurgeConfirmation(SurgeConfirmation surgeConfirmation) {
        this.surgeConfirmation = surgeConfirmation;
    }
}
