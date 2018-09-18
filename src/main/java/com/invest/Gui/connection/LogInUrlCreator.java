package com.invest.Gui.connection;

import com.invest.Gui.dto.UserDto;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LogInUrlCreator {

    private final static Logger LOGGER = Logger.getLogger(LogInUrlCreator.class);
    private BasicUrlCreator basicUrlCreator = new BasicUrlCreator();

    public boolean sendReminderEmail(String email) {
        String basicPath =  "/v1/user/checkMail?";
        String[] params = {"mail"};
        String[] values = {email};
        String urlPath = basicUrlCreator.generateGetUrl(basicPath, params, values);
        HttpURLConnection connection;
        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString().equals("true");
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public UserDto createLoginRequest(String login, String password) {
        String basicPath =  "/v1/user/login?";
        String[] params = {"name" , "password"};
        String[] values = {login, password};
        String urlPath = basicUrlCreator.generateGetUrl(basicPath, params, values);
        HttpURLConnection connection;
        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode==200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                String allResponse = response.toString();
                allResponse = allResponse.replace("{", "");
                allResponse = allResponse.replace("}", "");
                allResponse = allResponse.replace("\"", "");
                String[] array = allResponse.split(",");

                ArrayList<String> list = new ArrayList<>();

                for(String i: array) {
                    String[] nextArray = i.split(":");
                    if (nextArray.length ==2) {
                        list.add(nextArray[1]);
                    }
                }
                if (!list.get(0).equals("null")) {
                    return new UserDto(Long.valueOf(list.get(0)), list.get(1), list.get(2), list.get(3));
                }

            } else {
                LOGGER.warn("No user in database: " + login + " " + password);
            }
        } catch (IOException e) {
            return new UserDto();
        }
        return new UserDto();
    }

}
