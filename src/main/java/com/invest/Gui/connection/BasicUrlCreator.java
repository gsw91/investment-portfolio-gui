package com.invest.Gui.connection;

import com.invest.Gui.config.ServiceConfig;

interface BasicUrlCreator {

    default String generateUrlWithParams(String endpoint, String[] params, String[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceConfig.SERVER_URL);
        stringBuilder.append(endpoint);
        stringBuilder.append("?");

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
