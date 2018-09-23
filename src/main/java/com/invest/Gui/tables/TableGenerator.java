package com.invest.Gui.tables;

import com.invest.Gui.connection.BasicUrlCreator;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public interface TableGenerator extends BasicUrlCreator {

    Logger LOGGER = Logger.getLogger(TableGenerator.class);
    JTable createTable(Long userId);
    List transformResponse(String[] array);
    List setGeneratedDate(List<String> transformedResponseList);

    default HttpURLConnection createConnection(String url,String requestMethod)  {
        try {
            URL createdUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) createdUrl.openConnection();
            connection.setRequestMethod(requestMethod);
            return connection;
        } catch (IOException ioe) {
            LOGGER.warn("Connection problem: " + ioe.getMessage());
            return null;
        }
    }

    default String[] getResponse(HttpURLConnection connection, String tableNameToLogger) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                builder.append(inputLine);
            }
            String response = removeSigns(builder.toString());
            if (response == null) {
                LOGGER.error(tableNameToLogger + " creation failed");
                return new String[]{" ", " "};
            }
            return response.split(",");
        } catch (IOException ioe) {
            LOGGER.warn("Connection problem: " + ioe.getMessage());
            return null;
        }
    }

    default String removeSigns(String response) {
        response = response.replace("[", "");
        response = response.replace("{", "");
        response = response.replace("}", "");
        response = response.replace("]", "");
        response = response.replace("\"", "");
        return response;
    }

    default String[] setParams(String... params) {
        return params;
    }

    default String[] setValues(String... values) {
        return values;
    }

}
