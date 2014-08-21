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

        InputStream is = getClass().getResourceAsStream("/unittest.properties");
        Properties props = new Properties();
        props.load(is);

        client = new UberClient("v1", "", "", null, RestAdapter.LogLevel.FULL);
        String serverToken = props.getProperty("server_token");
        assertNotNull("server_token property is null. Make sure you have a unittest.properties file in " +
                "/src/test/resources/ directory.", serverToken);

        latitude = props.getProperty("product_latitude");
        assertNotNull("product_latitude property is null. Make sure you have a unittest.properties file in " +
                "/src/test/resources/ directory.", serverToken);
        longitude = props.getProperty("product_longitude");
        assertNotNull("product_longitude property is null. Make sure you have a unittest.properties file in " +
                "/src/test/resources/ directory.", serverToken);
        client.setServerToken(serverToken);
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void testGetProducts() {
        Products products = client.getApiService().getProducts(Double.valueOf(latitude).doubleValue(),
                Double.valueOf(longitude).doubleValue());
        assertNotNull("get products response is null", products);
        assertNotNull("products list is null", products.getProducts());
    }
}
