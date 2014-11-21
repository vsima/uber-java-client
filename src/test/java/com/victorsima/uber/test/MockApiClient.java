package com.victorsima.uber.test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by victorsima on 11/20/14.
 */
public class MockApiClient implements Client {

    @Override
    public Response execute(Request request) throws IOException {
        URI uri = URI.create(request.getUrl());
        String method = request.getMethod();
        String responseString = "{}";

        if (uri.getPath().equals("/v1/estimates/time") && "GET".equals(method)) {
            /**
             * {@link com.victorsima.uber.UberService#getTimeEstimates(double, double, String, String)}
             **/
            responseString = "{\"times\":[{\"localized_display_name\":\"uberX\",\"estimate\":140,\"display_name\":\"uberX\",\"product_id\":\"b8e5c464-5de2-4539-a35a-986d6e58f186\"},{\"localized_display_name\":\"uberXL\",\"estimate\":571,\"display_name\":\"uberXL\",\"product_id\":\"1e0ce2df-4a1e-4333-86dd-dc0c67aaabe1\"},{\"localized_display_name\":\"UberBLACK\",\"estimate\":224,\"display_name\":\"UberBLACK\",\"product_id\":\"0e9d8dd3-ffec-4c2b-9714-537e6174bb88\"},{\"localized_display_name\":\"UberSUV\",\"estimate\":224,\"display_name\":\"UberSUV\",\"product_id\":\"56487469-0d3d-4f19-b662-234b7576a562\"},{\"localized_display_name\":\"uberT\",\"estimate\":40,\"display_name\":\"uberT\",\"product_id\":\"ebe413ab-cf49-465f-8564-a71119bfa449\"}]}";
        } else if (uri.getPath().equals("/v1/estimates/price") && "GET".equals(method)) {
            /**
             * {@link com.victorsima.uber.UberService#getPriceEstimates(double, double, double, double)}
             */
            responseString = "{\"prices\":[{\"localized_display_name\":\"uberX\",\"duration\":1730,\"low_estimate\":\"31\",\"display_name\":\"uberX\",\"product_id\":\"b8e5c464-5de2-4539-a35a-986d6e58f186\",\"distance\":9.5,\"surge_multiplier\":1.0,\"estimate\":\"$31-42\",\"high_estimate\":\"42\",\"currency_code\":\"USD\"},{\"localized_display_name\":\"uberXL\",\"duration\":1730,\"low_estimate\":\"47\",\"display_name\":\"uberXL\",\"product_id\":\"1e0ce2df-4a1e-4333-86dd-dc0c67aaabe1\",\"distance\":9.5,\"surge_multiplier\":1.0,\"estimate\":\"$47-63\",\"high_estimate\":\"63\",\"currency_code\":\"USD\"},{\"localized_display_name\":\"UberBLACK\",\"duration\":1730,\"low_estimate\":\"55\",\"display_name\":\"UberBLACK\",\"product_id\":\"0e9d8dd3-ffec-4c2b-9714-537e6174bb88\",\"distance\":9.5,\"surge_multiplier\":1.0,\"estimate\":\"$55-72\",\"high_estimate\":\"72\",\"currency_code\":\"USD\"},{\"localized_display_name\":\"UberSUV\",\"duration\":1730,\"low_estimate\":\"73\",\"display_name\":\"UberSUV\",\"product_id\":\"56487469-0d3d-4f19-b662-234b7576a562\",\"distance\":9.5,\"surge_multiplier\":1.0,\"estimate\":\"$73-92\",\"high_estimate\":\"92\",\"currency_code\":\"USD\"},{\"localized_display_name\":\"uberT\",\"duration\":1730,\"low_estimate\":null,\"display_name\":\"uberT\",\"product_id\":\"ebe413ab-cf49-465f-8564-a71119bfa449\",\"distance\":9.5,\"surge_multiplier\":1.0,\"estimate\":\"Metered\",\"high_estimate\":null,\"currency_code\":null}]}";
        } else if (uri.getPath().equals("/v1/products") && "GET".equals(method)) {
            /**
             * {@link com.victorsima.uber.UberService#getProducts(double, double)}
             */
            responseString = "{\"products\":[{\"capacity\":4,\"image\":\"http:\\/\\/d1a3f4spazzrp4.cloudfront.net\\/car-types\\/mono\\/mono-uberx.png\",\"display_name\":\"uberX\",\"product_id\":\"b8e5c464-5de2-4539-a35a-986d6e58f186\",\"description\":\"The Low Cost Uber\"},{\"capacity\":6,\"image\":\"http:\\/\\/d1a3f4spazzrp4.cloudfront.net\\/car-types\\/mono\\/mono-uberxl2.png\",\"display_name\":\"uberXL\",\"product_id\":\"1e0ce2df-4a1e-4333-86dd-dc0c67aaabe1\",\"description\":\"low-cost rides for large groups\"},{\"capacity\":4,\"image\":\"http:\\/\\/d1a3f4spazzrp4.cloudfront.net\\/car-types\\/mono\\/mono-black.png\",\"display_name\":\"UberBLACK\",\"product_id\":\"0e9d8dd3-ffec-4c2b-9714-537e6174bb88\",\"description\":\"The original Uber\"},{\"capacity\":6,\"image\":\"http:\\/\\/d1a3f4spazzrp4.cloudfront.net\\/car-types\\/mono\\/mono-suv.png\",\"display_name\":\"UberSUV\",\"product_id\":\"56487469-0d3d-4f19-b662-234b7576a562\",\"description\":\"Room for everyone\"},{\"capacity\":1,\"image\":\"http:\\/\\/d1a3f4spazzrp4.cloudfront.net\\/car-types\\/mono\\/mono-nytaxi4.png\",\"display_name\":\"uberT\",\"product_id\":\"ebe413ab-cf49-465f-8564-a71119bfa449\",\"description\":\"Taxi and Boro-Taxi without the hassle\"}]}";
        }

            return new Response(uri.toString(), 200, "OK", new ArrayList<Header>(), new TypedByteArray("application/json", responseString.getBytes()));
    }

}
