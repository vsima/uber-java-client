package com.victorsima.uber;

import com.victorsima.uber.model.Products;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by victorsima on 8/20/14.
 */
public interface UberService {

    @GET("/products")
    void getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude,
                     Callback<Products> productsCallback);
    @GET("/products")
    Products getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude);
}
