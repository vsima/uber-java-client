package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.request.RequestBody;

import java.util.Date;

/**
 * User history model obj
 */
public class UserHistory {

    @Expose
    @SerializedName("uuid")
    private String uuid;
    @Expose
    @SerializedName("request_time")
    private Date requestTime;
    @Expose
    @SerializedName("product_id")
    private String productId;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("distance")
    private float distance;
    @Expose
    @SerializedName("start_time")
    private Date startTime;
    @Expose
    @SerializedName("end_time")
    private Date endTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
