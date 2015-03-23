package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/22/15.
 */
public class Location {

    @Expose
    @SerializedName("latitude")
    private float latitude;

    @Expose
    @SerializedName("longitude")
    private float longitude;
}
