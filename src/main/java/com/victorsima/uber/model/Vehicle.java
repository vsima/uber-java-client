package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/22/15.
 */
public class Vehicle {

    @Expose
    @SerializedName("make")
    private String make;
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("license_plate")
    private String licensePlate;
    @Expose
    @SerializedName("picture_url")
    private String pictureUrl;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
