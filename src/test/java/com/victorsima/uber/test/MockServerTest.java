package com.victorsima.uber.test;

import com.victorsima.uber.model.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * Created by victorsima on 8/20/14.
 */
//@RunWith(JUnit4.class)
public class MockServerTest extends BaseTest {

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    public boolean runUsingSandboxServer() {
        return false;
    }
}
