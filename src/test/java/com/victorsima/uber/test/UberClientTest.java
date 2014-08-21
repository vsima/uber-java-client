package com.victorsima.uber.test;

import com.victorsima.uber.UberClient;
import com.victorsima.uber.model.Products;
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

    @Before
    public void setup() throws Exception {

        String serverToken = null;

        InputStream is = getClass().getResourceAsStream("/test.properties");
        if (is != null) {
            Properties props = new Properties();
            props.load(is);

            serverToken = props.getProperty("uber_server_token");
            assertNotNull("uber_server_token property is null. Make sure you have a test.properties file in " +
                    "/src/test/resources/ directory.", serverToken);

            latitude = props.getProperty("uber_product_latitude");
            assertNotNull("uber_product_latitude property is null. Make sure you have a test.properties file in " +
                    "/src/test/resources/ directory.", latitude);

            longitude = props.getProperty("uber_product_longitude");
            assertNotNull("uber_product_longitude property is null. Make sure you have a test.properties file in " +
                    "/src/test/resources/ directory.", longitude);
        } else {
            serverToken = System.getProperty("uber_server_token");
            latitude = System.getProperty("uber_product_latitude");
            longitude = System.getProperty("uber_product_longitude");
        }

        client = new UberClient("v1", "", "", null, RestAdapter.LogLevel.FULL);
        client.setServerToken(serverToken);
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
}
