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
        //String result = "https://ancient-gorge-42887.herokuapp.com";
        String result = "http://localhost:8080";
        ServiceConfig serviceConfig = new ServiceConfig();
        //when
        String property = serviceConfig.getServiceUrl();
        //then
        Assert.assertEquals(result, property);
    }

}
