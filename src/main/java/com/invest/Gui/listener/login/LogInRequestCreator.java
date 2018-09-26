package com.invest.Gui.listener.login;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.GetRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.dto.UserDto;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class LogInRequestCreator implements GetRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(LogInRequestCreator.class);

    protected boolean sendReminderEmail(String email) {
        String[] params = {"mail"};
        String[] values = {email};
        String urlPath = generateUrlWithParams(ServiceConfig.CHECK_MAIL, params, values);
        try {
            return getResponse(urlPath, RequestMethod.GET).equals("true");
        } catch (IOException e) {
            return false;
        }
    }

    protected UserDto createLoginRequest(String login, String password) {
        String[] params = {"name", "password"};
        String[] values = {login, password};
        String url = generateUrlWithParams(ServiceConfig.USER_LOGIN, params, values);
        String response;
        try {
            response = getResponse(url, RequestMethod.GET);
            response = removeSigns(response);
            return prepareUserToLog(response);
        } catch (IOException ioe) {
            LOGGER.warn("No user in database: " + login + " " + password);
        }
        return new UserDto();
    }

    private UserDto prepareUserToLog(String response) {
        String[] array = response.split(",");
        List<String> list = new ArrayList<>();
        for (String i : array) {
            String[] nextArray = i.split(":");
            if (nextArray.length == 2) {
                list.add(nextArray[1]);
            }
        }
        if (!list.get(0).equals("null")) {
            return new UserDto(Long.valueOf(list.get(0)), list.get(1), list.get(2), list.get(3));
        }
        return new UserDto();
    }

}
