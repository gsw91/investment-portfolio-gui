package com.invest.Gui.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceConfig {

    private final static Logger LOGGER = Logger.getLogger(ServiceConfig.class);

    private String serviceUrl = getUrl();

    public String getServiceUrl() {
        return serviceUrl;
    }

    private String getUrl() {
        try {
            return getPropValue();
        } catch (IOException e) {
            LOGGER.warn("File not found");
        }
        return "";
    }

    private String getPropValue() throws IOException {
        Properties properties = new Properties();
        String propFile = "application.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile);

        if(inputStream!=null) {
            properties.load(inputStream);
        } else {
            LOGGER.warn("File not found");
        }
        return properties.getProperty("service.address.url");
    }

}
