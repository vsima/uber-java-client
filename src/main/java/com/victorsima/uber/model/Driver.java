package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/22/15.
 */
public class Driver {
    @Expose
    @SerializedName("phone_number")
    private String phoneNumber;
    @Expose
    @SerializedName("rating")
    private float rating;
    @Expose
    @SerializedName("picture_url")
    private String pictureUrl;
    @Expose
    @SerializedName("name")
    private String name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}