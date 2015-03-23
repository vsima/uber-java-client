package com.victorsima.uber.model.sandbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.Product;

/**
 * Created by victorsima on 3/23/15.
 */
public class SandboxProductBody {

    public SandboxProductBody(Float surgeMultiplier, Boolean driversAvailable) {
        this.surgeMultiplier = surgeMultiplier;
        this.driversAvailable = driversAvailable;
    }

    @Expose
    @SerializedName("surge_multiplier")
    private Float surgeMultiplier;
    @Expose
    @SerializedName("drivers_available")
    private Boolean driversAvailable;

    public float getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(float surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    public boolean isDriversAvailable() {
        return driversAvailable;
    }

    public void setDriversAvailable(boolean driversAvailable) {
        this.driversAvailable = driversAvailable;
    }
}
