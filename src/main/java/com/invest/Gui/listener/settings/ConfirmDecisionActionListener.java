package com.invest.Gui.listener.settings;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SettingsFrame;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfirmDecisionActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(ConfirmDecisionActionListener.class);

    private SettingsFrame settingsFrame;
    private String serverUrl;
    private UserDto userDto;
    private UserFrame userFrame;

    public ConfirmDecisionActionListener(SettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
        this.serverUrl = settingsFrame.getServerUrl();
        this.userDto = settingsFrame.getUserDto();
        this.userFrame = settingsFrame.getUserFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton removeAccount = settingsFrame.getRemoveAccount();
        JRadioButton resetAccount = settingsFrame.getResetAccount();

        if (removeAccount.isSelected()) {
            try {
                deleteAccount();
                new LogInFrame(serverUrl);
                userFrame.closeAllFrames();
            } catch (IOException excep) {
                LOGGER.error(excep.getMessage());
            }
        } else if (resetAccount.isSelected()) {
            try {
                resetInstruments();
                resetStatistics();
                new UserFrame(userDto, serverUrl).openUserFrame();
                userFrame.closeAllFrames();
            } catch (IOException excep1) {
                LOGGER.error(excep1.getMessage());
            }
        }
    }

    private void deleteAccount() throws IOException {
        String request = serverUrl + "/v1/user/delete?userId=" + userDto.getId();
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = reader.readLine();
        boolean isRemoved = Boolean.valueOf(message);

        int responseCode = connection.getResponseCode();

        if (responseCode == 200 && isRemoved) {
            LOGGER.info("Account successfully removed");
        } else {
            LOGGER.warn("Something has gone wrong, try later" + responseCode);
        }
    }

    private void resetInstruments() throws IOException {
        String request = serverUrl + "/v1/instrument/reset?userId=" + userDto.getId();
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = reader.readLine();
        boolean isRemoved = Boolean.valueOf(message);

        int responseCode = connection.getResponseCode();

        if (responseCode == 200 && isRemoved) {
            LOGGER.info("Instruments successfully removed");
        } else {
            LOGGER.warn("Instruments can not be remove, try later" + responseCode);
        }
    }

    private void resetStatistics() throws IOException {
        String request = serverUrl + "/v1/stats/reset?userId=" + userDto.getId();
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = reader.readLine();
        boolean isRemoved = Boolean.valueOf(message);

        int responseCode = connection.getResponseCode();

        if (responseCode == 200 && isRemoved) {
            LOGGER.info("Statistics successfully removed");
        } else {
            LOGGER.warn("Statistics can not be remove, try later" + responseCode);
        }
    }

}