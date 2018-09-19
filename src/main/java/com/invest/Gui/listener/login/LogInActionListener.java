package com.invest.Gui.listener.login;

import com.invest.Gui.connection.LogInUrlCreator;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.exception.LogInException;
import com.invest.Gui.frames.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInActionListener implements ActionListener {

    private LogInUrlCreator logInUrlCreator = new LogInUrlCreator();
    private final static Logger LOGGER = Logger.getLogger(LogInActionListener.class);
    private LogInFrame logInFrame;
    private String serverUrl;

    public LogInActionListener(LogInFrame logInFrame, String serverUrl) {
        this.logInFrame = logInFrame;
        this.serverUrl = serverUrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton logIn = logInFrame.getLogIn();
        JTextField loginField = logInFrame.getLoginField();
        JPasswordField passwordField = logInFrame.getPasswordField();
        JTextField emailField = logInFrame.getEmailField();

        if (logIn.getText().equals("click to log in")) {
            String login = loginField.getText();
            char[] chars = passwordField.getPassword();
            StringBuilder builder = new StringBuilder();
            for (char i : chars) {
                builder.append(i);
            }
            String password = builder.toString();
            boolean condition = sendLogRequest(login, password);
            if (condition) {
                LOGGER.info("User " + login + " logged in ");
            } else {
                LOGGER.warn("Wrong login or password");
            }
        } else if (logIn.getText().equals("send email")) {
            String email = emailField.getText();
            boolean emailCondition = sendRemindingEmail(email);
            if (emailCondition) {
                LOGGER.info("Email with credentials was sent to: " + email);
                new LogInFrame(serverUrl);
                logInFrame.dispose();
            } else {
                LOGGER.warn("There is no such email id database: " + email);
            }
        }
    }

    private boolean sendRemindingEmail(String email) {
        boolean emailCondition = email.contains("@") && email.contains(".");
        if (emailCondition) {
            emailCondition = logInUrlCreator.sendReminderEmail(email);
        }
        return emailCondition;
    }

    private boolean sendLogRequest(String login, String password) throws LogInException {
        UserDto userDto = logInUrlCreator.createLoginRequest(login, password);
        if (userDto.getId() != null) {
            UserFrame userFrame = new UserFrame(userDto, serverUrl);
            userFrame.openUserFrame();
            logInFrame.dispose();
            return true;
        } else {
            return false;
        }
    }

}