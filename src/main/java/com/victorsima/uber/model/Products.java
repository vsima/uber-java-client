package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.victorsima.uber.model.Product;

import java.util.List;

/**
 * Products model obj
 */
public class Products {

    @Expose
    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
