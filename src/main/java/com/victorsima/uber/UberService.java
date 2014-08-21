package com.victorsima.uber;

import com.victorsima.uber.model.Prices;
import com.victorsima.uber.model.Products;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by victorsima on 8/20/14.
 */
public interface UberService {

    /**
     * The Products endpoint returns information about the Uber products offered at a given location. The response
     * includes the display name and other details about each product, and lists the products in the proper
     * display order.
     *
     * @param latitude
     * @param longitude
     * @param productsCallback
     */
    @GET("/products")
    void getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude,
                     Callback<Products> productsCallback);

    /**
     * The Products endpoint returns information about the Uber products offered at a given location. The response
     * includes the display name and other details about each product, and lists the products in the proper
     * display order.
     *
     * @param latitude Latitude component of location.
     * @param longitude Longitude component of location.
     * @return
     */
    @GET("/products")
    Products getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude);


    /**
     * The Price Estimates endpoint returns an estimated price range for each product offered at a given location.
     * The price estimate is provided as a formatted string with the full price range and the localized currency
     * symbol.
     *
     * @param startLatitude Latitude component of start location.
     * @param startLongitude Longitude component of start location.
     * @param endLatitude Latitude component of end location.
     * @param endLongitude Longitude component of end location.
     * @param productsCallback
     */
    @GET("/estimates/price")
    void getPriceEstimates(@Query("start_latitude") double startLatitude,
                           @Query("start_longitude") double startLongitude,
                           @Query("end_latitude") double endLatitude,
                           @Query("end_longitude") double endLongitude,
                           Callback<Prices> productsCallback);

    /**
     * The Price Estimates endpoint returns an estimated price range for each product offered at a given location.
     * The price estimate is provided as a formatted string with the full price range and the localized currency
     * symbol.
     *
     * @param startLatitude Latitude component of start location.
     * @param startLongitude Longitude component of start location.
     * @param endLatitude Latitude component of end location.
     * @param endLongitude Longitude component of end location.
     * @return
     */
    @GET("/estimates/price")
    Prices getPriceEstimates(@Query("start_latitude") double startLatitude,
                             @Query("start_longitude") double startLongitude,
                             @Query("end_latitude") double endLatitude,
                             @Query("end_longitude") double endLongitude);
}
