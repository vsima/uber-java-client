package com.victorsima.uber;

import com.victorsima.uber.model.Prices;
import com.victorsima.uber.model.Products;
import com.victorsima.uber.model.Times;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Uber Service interface
 */
public interface UberService {

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
     * @see #getProducts(double, double)
     */
    @GET("/products")
    void getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude,
                     Callback<Products> productsCallback);




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

    /**
     * @see #getPriceEstimates(double, double, double, double)
     */
    @GET("/estimates/price")
    void getPriceEstimates(@Query("start_latitude") double startLatitude,
                           @Query("start_longitude") double startLongitude,
                           @Query("end_latitude") double endLatitude,
                           @Query("end_longitude") double endLongitude,
                           Callback<Prices> productsCallback);

    /**
     * The Time Estimates endpoint returns ETAs for all products offered at a given location, with the responses
     * expressed as integers in seconds. We recommend that this endpoint be called every minute to provide the
     * most accurate, up-to-date ETAs.
     *
     * @param startLatitude Latitude component.
     * @param startLongitude Longitude component.
     * @param customerUUID (optional) Unique customer identifier to be used for experience customization.
     * @param productId (optional) Unique identifier representing a specific product for a given latitude & longitude.
     * @return
     */
    @GET("/estimates/time")
    Times getTimeEstimates(@Query("start_latitude") double startLatitude,
                           @Query("start_longitude") double startLongitude,
                           @Query("customer_uuid") String customerUUID,
                           @Query("product_id") String productId);

    /**
     * @see #getTimeEstimates(double, double, String, String)
     */
    void getTimeEstimates(@Query("start_latitude") double startLatitude,
                          @Query("start_longitude") double startLongitude,
                          @Query("customer_uuid") String customerUUID,
                          @Query("product_id") String productId,
                          Callback<Times> timesCallback);
}
