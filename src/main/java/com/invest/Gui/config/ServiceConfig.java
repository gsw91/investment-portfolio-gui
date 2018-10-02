package com.invest.Gui.config;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class ServiceConfig {

    private final static Logger LOGGER = Logger.getLogger(ServiceConfig.class);
    private static File applicationProperties = new File("application.properties");

    public final static String SERVER_URL = getPropertyValue(applicationProperties, "server.address.url");
    public final static String INSTRUMENT_ADD = getPropertyValue(applicationProperties, "endpoint.instrument.add");
    public final static String INSTRUMENT_SHOW_ALL = getPropertyValue(applicationProperties, "endpoint.instrument.show");
    public final static String INSTRUMENT_SELL = getPropertyValue(applicationProperties, "endpoint.instrument.sell");
    public final static String INSTRUMENT_RESET = getPropertyValue(applicationProperties, "endpoint.instrument.reset");
    public final static String SHARES_GET_ONE = getPropertyValue(applicationProperties, "endpoint.share.name");
    public final static String SHARES_ALL = getPropertyValue(applicationProperties, "endpoint.share.all");
    public final static String USER_CHECK_MAIL = getPropertyValue(applicationProperties, "endpoint.user.check.mail");
    public final static String USER_CREATE = getPropertyValue(applicationProperties, "endpoint.user.create");
    public final static String USER_DELETE = getPropertyValue(applicationProperties, "endpoint.user.delete");
    public final static String USER_LOGIN = getPropertyValue(applicationProperties, "endpoint.user.login");
    public final static String USER_UPDATE_LOGIN = getPropertyValue(applicationProperties, "endpoint.user.update.login");
    public final static String USER_UPDATE_PASSWORD = getPropertyValue(applicationProperties, "endpoint.user.update.password");
    public final static String USER_UPDATE_MAIL = getPropertyValue(applicationProperties, "endpoint.user.update.email");
    public final static String STATS_USER = getPropertyValue(applicationProperties, "endpoint.stats.user");
    public final static String STATS_RESET = getPropertyValue(applicationProperties, "endpoint.stats.reset");

    private static String getPropertyValue(File file, String property) {

        Properties properties = new Properties();
        if (!file.canRead()) {
                file = new File("src\\main\\resources\\application.properties");
        }
        try {
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
            return properties.getProperty(property);
        } catch (IOException ioe) {
            LOGGER.warn("File not found");
            return "";
        }
    }

}
