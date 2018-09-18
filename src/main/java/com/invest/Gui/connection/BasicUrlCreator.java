package com.invest.Gui.connection;

import com.invest.Gui.config.ServiceConfig;

public class BasicUrlCreator {

    public String generateGetUrl(String basicPath, String[] params, String[] values) {

        ServiceConfig serviceConfig = new ServiceConfig();
        String serverUrl = serviceConfig.getServiceUrl();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serverUrl);
        stringBuilder.append(basicPath);
        if (params.length != values.length) {
            return "";
        } else {
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(params[i]);
                stringBuilder.append("=");
                stringBuilder.append(values[i]);
            }
        }
        return stringBuilder.toString();
    }
}
