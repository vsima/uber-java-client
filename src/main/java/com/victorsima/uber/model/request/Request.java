package com.victorsima.uber.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.Driver;
import com.victorsima.uber.model.Location;
import com.victorsima.uber.model.Vehicle;

import java.util.List;

/**
 * Created by victorsima on 3/22/15.
 */
public class Request {

    /**
     * The unique ID of the Request.
     */
    @Expose
    @SerializedName("request_id")
    private String requestId;

    /**
     * The status of the Request indicating state.
     */
    @Expose
    @SerializedName("status")
    private RequestBody.Status status;

    /**
     * The object that contains vehicle details.
     */
    @Expose
    @SerializedName("vehicle")
    private Vehicle vehicle;

    /**
     * The object that contains driver details.
     */
    @Expose
    @SerializedName("driver")
    private Driver driver;

    /**
     * The object that contains the location information of the vehicle and driver.
     */
    @Expose
    @SerializedName("location")
    private Location location;

    /**
     * The estimated time of vehicle arrival in minutes.
     */
    @Expose
    @SerializedName("eta")
    private int eta;

    /**
     * The surge pricing multiplier used to calculate the increased price of a Request. A
     * multiplier of 1.0 means surge pricing is not in effect.
     */
    @Expose
    @SerializedName("surge_multiplier")
    private float surgeMultiplier;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RequestBody.Status getStatus() {
        return status;
    }

    public void setStatus(RequestBody.Status status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public float getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(float surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }
}
