/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.kernel;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by khunzohn on 10/26/15.
 */
public class MarketDayKernelTest {
    MarketDayKernel kernel;
    @Before
    public void setUp() throws Exception {
        kernel = new MarketDayKernel();
    }

    @After
    public void tearDown() throws Exception {
        kernel = null;
    }
}