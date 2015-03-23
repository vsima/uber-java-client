package com.victorsima.uber.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/23/15.
 */
public class RequestMap {
    @Expose
    @SerializedName("request_id")
    private String requestId;

    @Expose
    @SerializedName("href")
    private String href;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
