package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Product model obj
 */
public class Product {

    @Expose
    @SerializedName("product_id")
    private String productId;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("display_name")
    private String displayName;

    @Expose
    @SerializedName("capacity")
    private int capacity;

    @Expose
    @SerializedName("image")
    private String image;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
