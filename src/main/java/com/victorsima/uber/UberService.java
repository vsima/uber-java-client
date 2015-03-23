package com.victorsima.uber;

import com.squareup.okhttp.Response;
import com.victorsima.uber.model.*;
import com.victorsima.uber.model.request.Request;
import com.victorsima.uber.model.request.RequestBody;
import com.victorsima.uber.model.request.RequestMap;
import retrofit.Callback;
import retrofit.http.*;

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
     * @return Times
     */
    @GET("/estimates/time")
    Times getTimeEstimates(@Query("start_latitude") double startLatitude,
                           @Query("start_longitude") double startLongitude,
                           @Query("customer_uuid") String customerUUID,
                           @Query("product_id") String productId);

    /**
     * @see #getTimeEstimates(double, double, String, String)
     */
    @GET("/estimates/time")
    void getTimeEstimates(@Query("start_latitude") double startLatitude,
                          @Query("start_longitude") double startLongitude,
                          @Query("customer_uuid") String customerUUID,
                          @Query("product_id") String productId,
                          Callback<Times> timesCallback);

    /**
     * The Promotions endpoint returns information about the promotion that will be available to a
     * new user based on their activity's location. These promotions do not apply for existing users.
     * @param startLatitude Latitude component of start location.
     * @param startLongitude Longitude component of start location.
     * @param endLatitude Latitude component of end location.
     * @param endLongitude Longitude component of end location.
     * @return Promotion
     */
    @GET("/promotions")
    Promotion getPromotions(@Query("start_latitude") double startLatitude,
                            @Query("start_longitude") double startLongitude,
                            @Query("end_latitude") double endLatitude,
                            @Query("end_longitude") double endLongitude);

    /**
     * @see #getPromotions(double, double, double, double)
     */
    @GET("/promotions")
    void getPromotions(@Query("start_latitude") double startLatitude,
                            @Query("start_longitude") double startLongitude,
                            @Query("end_latitude") double endLatitude,
                            @Query("end_longitude") double endLongitude,
                            Callback<Promotion> promotionCallback);

    /**
     * The User Profile endpoint returns information about the Uber user that has authorized with the application.
     *
     * @return UserProfile
     */
    @GET("/me")
    UserProfile getMe();
    /**
     * @see #getMe()
     */
    @GET("/me")
    void getMe(Callback<UserProfile> userProfileCallback);

    /**
     * <p>The Request endpoint allows a ride to be requested on behalf of an Uber user given their desired product,
     * start, and end locations.</p>
     *
     * <p>Please review the Sandbox documentation on how to develop and test against these endpoints without making
     * real-world Requests and being charged.</p>
     *
     * @param requestBody
     * @return
     */
    @POST("/requests")
    Request postRequest(@Body RequestBody requestBody);

    /**
     * @see #postRequest(com.victorsima.uber.model.request.RequestBody request)
     */
    @POST("/requests")
    void postRequest(@Body RequestBody requestBody, Callback<Request> callback);


    /**
     * Get the real time status of an ongoing trip that was created using the Ride Request endpoint.
     * @param requestId Unique identifier representing a Request.
     * @return
     */
    @GET("/requests/{request_id}")
    Request getRequestDetails(@Path("request_id") String requestId);

    /**
     *@see #getRequestDetails(String)
     */
    @GET("/requests/{request_id}")
    void getRequestDetails(@Path("request_id") String requestId, Callback<Request> callback);

    /**
     * Cancel an ongoing Request on behalf of a rider
     * @param requestId Unique identifier representing a Request.
     * @return
     */
    @DELETE("/requests/{request_id}")
    Response deleteRequest(@Path("request_id") String requestId);

    /**
     * @see #deleteRequest(String)
     */
    @DELETE("/requests/{request_id}")
    void deleteRequest(@Path("request_id") String requestId, Callback<Response> callback);

    @GET("/requests/{request_id}/map")
    RequestMap getRequestMap(@Path("request_id") String requestId);
}
