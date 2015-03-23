package com.victorsima.uber.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by victorsima on 3/25/15.
 */
public class SurgeConfirmationError {

    @Expose
    @SerializedName("meta")
    private Meta meta;
    @Expose
    @SerializedName("errors")
    private List<Error> errors;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
