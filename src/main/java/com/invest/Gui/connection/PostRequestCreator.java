package com.invest.Gui.connection;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public interface PostRequestCreator extends RequestCreator {

    void addInstrument(Long userId);
    String[] createKeys();
    Object[] createValues();

    default HttpURLConnection createPostConnection(String url, String requestMethod) throws IOException {
        URL urlPath = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    default void sendJsonObject(HttpURLConnection connection, JSONObject jsonObject) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(jsonObject.toString());
        wr.flush();
    }

    default JSONObject createJson(String[] keys, Object[] values) {
        JSONObject json = new JSONObject();
        for(int key=0, value=0; key<keys.length; key++, value++) {
            json.put(keys[key], values[value]);
        }
        return json;
    }

}
