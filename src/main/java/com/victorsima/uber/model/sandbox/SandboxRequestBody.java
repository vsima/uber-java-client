package com.victorsima.uber.model.sandbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.request.RequestBody;

/**
 * Created by victorsima on 3/23/15.
 */
public class SandboxRequestBody {

    @Expose
    @SerializedName("status")
    private RequestBody.Status status;

    public SandboxRequestBody(RequestBody.Status status) {
        this.status = status;
    }

    public RequestBody.Status getStatus() {
        return status;
    }

    public void setStatus(RequestBody.Status status) {
        this.status = status;
    }
}
