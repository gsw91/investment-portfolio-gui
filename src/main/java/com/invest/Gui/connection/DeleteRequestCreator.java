package com.invest.Gui.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface DeleteRequestCreator extends RequestCreator {

    String[] createParams();
    String[] createValues();

    default Boolean createRequest(String request, String requestMethod) throws IOException {
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = reader.readLine();
        return Boolean.valueOf(message);
    }

}
