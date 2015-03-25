package com.victorsima.uber.test;

import com.victorsima.uber.UberClient;
import com.victorsima.uber.model.*;
import com.victorsima.uber.model.request.Request;
import com.victorsima.uber.model.request.RequestBody;
import com.victorsima.uber.test.mock.MockApiClient;
import org.junit.After;
import org.junit.Test;
import retrofit.RestAdapter;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 * An abstract test class which allows running tests against a mocked server or the uber's sandbox server
 *
 * Created by victorsima on 3/19/15.
 */
public abstract class BaseTest {

    protected UberClient client;
    protected String clientId, clientSecret, redirectUrl, serverToken;
    protected String latitude, longitude;
    protected String startLatitude, startLongitude;
    protected String endLatitude, endLongitude;
    protected String username, password;

    /**
     * Return true to use the sandbox server, false to use the mock server
     * @return
     */
    public abstract boolean runUsingSandboxServer();

    public void setup() throws Exception {
        loadLocationProperties();
        loadUberProperties();
        initClient(runUsingSandboxServer());
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

    @Test
    public void testUserProfile() {
        UserProfile userProfile = client.getApiService().getMe();
        assertNotNull("user first name is null", userProfile.getFirstName());
        assertNotNull("user last name is null", userProfile.getLastName());
        assertNotNull("user uuid is null", userProfile.getUuid());
        assertNotNull("user promo code is null", userProfile.getPromoCode());
    }


    protected void initClient(boolean useSandboxServer) {
        if (useSandboxServer) {
            client = new UberClient(clientId, clientSecret, redirectUrl, null, null, useSandboxServer, RestAdapter.LogLevel.FULL);
        } else  {
            client = new UberClient("", "", "", null, new MockApiClient(), useSandboxServer, RestAdapter.LogLevel.FULL);
        }
    }

    protected void loadLocationProperties() throws Exception {

        InputStream is = getClass().getResourceAsStream("/locations.properties");
        assertNotNull("locations.properties file is missing in " +
                "/src/test/resources/ directory.", is);

        Properties props = new Properties();
        props.load(is);

        latitude = props.getProperty("uber_product_latitude");
        assertNotNull("uber_product_latitude property is missing in /src/test/resources/locations.properties", latitude);

        longitude = props.getProperty("uber_product_longitude");
        assertNotNull("uber_product_longitude property is missing in /src/test/resources/locations.properties", longitude);

        startLatitude = props.getProperty("uber_price_start_latitude");
        assertNotNull("uber_price_start_latitude property is missing in /src/test/resources/locations.properties", startLatitude);

        startLongitude = props.getProperty("uber_price_start_longitude");
        assertNotNull("uber_price_start_longitude property is missing in /src/test/resources/locations.properties", startLongitude);

        endLatitude = props.getProperty("uber_price_end_latitude");
        assertNotNull("uber_price_end_latitude property is missing in /src/test/resources/locations.properties", endLatitude);

        endLongitude = props.getProperty("uber_price_end_longitude");
        assertNotNull("uber_price_end_longitude property is missing in /src/test/resources/locations.properties", endLongitude);

    }

    protected void loadUberProperties() throws Exception {
        //CI
        if ("true".equals(System.getenv("CI"))) {
            serverToken = System.getenv("uber_server_token");
            clientId = System.getenv("uber_client_id");
            clientSecret = System.getenv("uber_secret");
            redirectUrl = System.getenv("uber_redirect_url");
            username = System.getenv("uber_username");
            password = System.getenv("uber_password");

        } else {

            InputStream is = getClass().getResourceAsStream("/uber.properties");
            assertNotNull("uber.properties file is missing in /src/test/resources/ directory.", is);

            Properties props = new Properties();
            props.load(is);

            serverToken = props.getProperty("uber_server_token");
            assertNotNull("uber_server_token property is missing in /src/test/resources/uber.properties", serverToken);

            clientId = props.getProperty("uber_client_id");
            assertNotNull("uber_client_id property is missing in /src/test/resources/uber.properties", clientId);

            clientSecret = props.getProperty("uber_secret");
            assertNotNull("uber_secret property is missing in /src/test/resources/uber.properties", clientSecret);

            redirectUrl = props.getProperty("uber_redirect_url");
            assertNotNull("uber_redirect_url property is missing in /src/test/resources/uber.properties", redirectUrl);

            username = props.getProperty("uber_username");
            assertNotNull("uber_username property is missing in /src/test/resources/uber.properties", username);

            password = props.getProperty("uber_password");
            assertNotNull("uber_password property is missing in /src/test/resources/uber.properties", password);

        }

    }
}
