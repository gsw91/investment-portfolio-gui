package com.invest.Gui.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ServiceConfigTestSuite {

    @Test
    public void testGetServiceUrl() {
        //given
        String result = "https://investment-portfolio-app.herokuapp.com";
//        String result = "http://localhost:8080";
        new ServiceConfig();
        //when
        String property = ServiceConfig.SERVER_URL;
        //then
        Assert.assertEquals(result, property);
    }

}
