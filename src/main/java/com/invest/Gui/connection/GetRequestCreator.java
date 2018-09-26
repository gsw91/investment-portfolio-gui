package com.invest.Gui.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface GetRequestCreator extends RequestCreator {

    default String getResponse(String url, String requestMethod) throws IOException {
        URL urlPath = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
        connection.setRequestMethod(requestMethod);
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
        return "";
    }

    default String removeSigns(String response) {
        response = response.replace("[", "");
        response = response.replace("{", "");
        response = response.replace("}", "");
        response = response.replace("]", "");
        response = response.replace("\"", "");
        return response;
    }

}
