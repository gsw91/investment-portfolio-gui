package com.invest.Gui.listener.login;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.exception.LogInException;
import com.invest.Gui.frames.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInActionListener extends LogInRequestCreator implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(LogInActionListener.class);
    private LogInFrame logInFrame;
    private JButton logIn;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField emailField;

    public LogInActionListener(LogInFrame logInFrame) {
        this.logInFrame = logInFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        logIn = logInFrame.getLogIn();
        loginField = logInFrame.getLoginField();
        passwordField = logInFrame.getPasswordField();
        emailField = logInFrame.getEmailField();

        if (checkText("click to log in")) {
            tryLogIn();
        } else if (checkText("send email")) {
            trySendEmail();
        }
    }

    private boolean checkText(String text) {
        return logIn.getText().equals(text);
    }

    private void tryLogIn() {
        String login = getUserLogin();
        boolean logInCondition = isSentLogRequest(login);
        if (logInCondition) {
            LOGGER.info("User " + login + " logged in ");
        } else {
            LOGGER.warn("Wrong login or password");
            WarningFrame.openWarningFrame("Wrong login or password");
        }
    }

    private void trySendEmail() {
        String email = getUserEmail();
        boolean emailCondition = sendRemindingEmail(email);
        if (emailCondition) {
            LOGGER.info("Email with credentials was sent to: " + email);
            switchToLogInFrame();
        } else {
            LOGGER.warn("There is no such email id database: " + email);
            WarningFrame.openWarningFrame("There is no such email id database: " + email);
        }
    }

    private String getUserLogin() {
        return loginField.getText();
    }

    private String getUserEmail() {
        return emailField.getText();
    }

    private boolean isSentLogRequest(String login) {
        char[] chars = passwordField.getPassword();
        StringBuilder builder = new StringBuilder();
        for (char i : chars) {
            builder.append(i);
        }
        String password = builder.toString();
        return sendLogRequest(login, password);
    }

    private void switchToLogInFrame() {
        new LogInFrame();
        logInFrame.dispose();
    }

    private boolean sendRemindingEmail(String email) {
        boolean emailCondition = email.contains("@") && email.contains(".");
        if (emailCondition) {
            emailCondition = sendReminderEmail(email);
        }
        return emailCondition;
    }

    private boolean sendLogRequest(String login, String password) throws LogInException {
        UserDto userDto = createLoginRequest(login, password);
        if (userDto.getId() != null) {
            UserFrame userFrame = new UserFrame(userDto);
            userFrame.openUserFrame();
            logInFrame.dispose();
            return true;
        } else {
            return false;
        }
    }

}