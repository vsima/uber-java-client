package com.victorsima.uber.test;

import com.victorsima.uber.UberClient;
import com.victorsima.uber.model.Prices;
import com.victorsima.uber.model.Products;
import com.victorsima.uber.model.Promotion;
import com.victorsima.uber.model.Times;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import retrofit.RestAdapter;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 * Created by victorsima on 8/20/14.
 */
@RunWith(JUnit4.class)
public class UberClientTest {

    private UberClient client;
    private String latitude, longitude;
    private String startLatitude, startLongitude;
    private String endLatitude, endLongitude;

    @Before
    public void setup() throws Exception {

        InputStream is = getClass().getResourceAsStream("/locations.properties");
        if (is != null) {
            Properties props = new Properties();
            props.load(is);

            latitude = props.getProperty("uber_product_latitude");
            assertNotNull("uber_product_latitude property is null. Make sure you have a location.properties file in " +
                    "/src/test/resources/ directory.", latitude);

            longitude = props.getProperty("uber_product_longitude");
            assertNotNull("uber_product_longitude property is null. Make sure you have a location.properties file in " +
                    "/src/test/resources/ directory.", longitude);

            startLatitude = props.getProperty("uber_price_start_latitude");
            startLongitude = props.getProperty("uber_price_start_longitude");
            endLatitude = props.getProperty("uber_price_end_latitude");
            endLongitude = props.getProperty("uber_price_end_longitude");
        }

        InputStream is2 = getClass().getResourceAsStream("/uber.properties");
        String serverToken;
        boolean useMockServer = true;

        if (is2 != null) {
            Properties props = new Properties();
            props.load(is2);

            serverToken = props.getProperty("uber_server_token");
            assertNotNull("uber_server_token property is null. Make sure you have an uber.properties file in " +
                    "/src/test/resources/ directory.", serverToken);
            useMockServer = Boolean.parseBoolean(props.getProperty("user_mock_server", "true"));
        } else {
            //get token from CI ENV
            serverToken = System.getenv("uber_server_token");
        }

        if (useMockServer) {
            client = new UberClient("v1", "", "", new MockApiClient(), RestAdapter.LogLevel.FULL);
        } else  {
            client = new UberClient("v1", "", "", null, RestAdapter.LogLevel.FULL);
        }

        if (serverToken != null) {
            client.setServerToken(serverToken);
        }
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void testGetProducts() {
        Products products = client.getApiService().getProducts(Double.parseDouble(latitude),
                Double.parseDouble(longitude));
        assertNotNull("get products response is null", products);
        assertNotNull("products list is null", products.getProducts());
    }

    @Test
    public void testGetPriceEstimates() {
        Prices prices = client.getApiService().getPriceEstimates(
                Double.parseDouble(startLatitude),
                Double.parseDouble(startLongitude),
                Double.parseDouble(endLatitude),
                Double.parseDouble(endLongitude));

        assertNotNull("get price estimates response is null", prices);
        assertNotNull("price estimates list is null", prices.getPrices());
    }


    @Test
    public void testGetTimeEstimates() {
        Times times = client.getApiService().getTimeEstimates(
                Double.parseDouble(startLatitude),
                Double.parseDouble(startLongitude),
                null,
                null);

        assertNotNull("get time estimates response is null", times);
        assertNotNull("time estimates list is null", times.getTimes());
    }

    @Test
    public void testPromotions() {
        Promotion promotion = client.getApiService().getPromotions(
                Double.parseDouble(startLatitude),
                Double.parseDouble(startLongitude),
                Double.parseDouble(endLatitude),
                Double.parseDouble(endLongitude));
        assertNotNull("promotion display text is null", promotion.getDisplayText());
        assertNotNull("promotion localized value is null", promotion.getLocalizedValue());
        assertNotNull("promotion type is null", promotion.getType());
    }
}
