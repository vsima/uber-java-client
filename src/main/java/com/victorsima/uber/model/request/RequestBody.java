package com.victorsima.uber.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victorsima on 3/23/15.
 */
public class RequestBody {

    public RequestBody(String productId, double startLatitude, double startLongitude,
                       double endLatitude, double endLongitude, String surgeConfirmationId) {
        this.productId = productId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.surgeConfirmationId = surgeConfirmationId;
    }

    public enum Status {
        /**
         * The Request is matching to the most efficient available driver.
         */
        @SerializedName("processing")
        PROCESSING("processing"),
        /**
         * The Request has been accepted by a driver and is "en route" to the start_location.
         */
        @SerializedName("accepted")
        ACCEPTED("accepted"),
        /**
         * The driver has arrived or will be shortly.
         */
        @SerializedName("arriving")
        ARRIVING("arriving"),
        /**
         * The Request is "en route" from the start location to the end location.
         */
        @SerializedName("in_progress")
        IN_PROGRESS("in_progress"),
        /**
         * The Request has been canceled by the driver.
         */
        @SerializedName("driver_canceled")
        DRIVER_CANCELLED("driver_canceled"),
        /**
         * Request has been completed by the driver.
         */
        @SerializedName("completed")
        COMPLETED("completed"),
        /**
         * The Request was unfulfilled because no drivers were available. Use the Product Types sandbox endpoint to
         * create a Request with this status.
         */
        @SerializedName("no_drivers_available")
        NO_DRIVERS_AVAILABLE("no_drivers_available"),
        /**
         * The Request canceled by rider. Issue a DELETE command to give a Request this status
         */
        @SerializedName("rider_canceled")
        RIDER_CANCELED("rider_canceled");

        private final String status;
        Status(String status) {
            this.status = status;
        }
        public String value() {
            return status;
        }
    }

    @Expose
    @SerializedName("product_id")
    private String productId;

    @Expose
    @SerializedName("start_latitude")
    private double startLatitude;

    @Expose
    @SerializedName("start_longitude")
    private double startLongitude;

    @Expose
    @SerializedName("end_latitude")
    private double endLatitude;

    @Expose
    @SerializedName("end_longitude")
    private double endLongitude;

    @Expose
    @SerializedName("surge_confirmation_id")
    private String surgeConfirmationId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getSurgeConfirmationId() {
        return surgeConfirmationId;
    }

    public void setSurgeConfirmationId(String surgeConfirmationId) {
        this.surgeConfirmationId = surgeConfirmationId;
    }
}
