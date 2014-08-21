package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by victorsima on 8/21/14.
 */
public class Times {

    @Expose
    @SerializedName("times")
    private List<Time> times;

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
