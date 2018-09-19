package com.invest.Gui.listener.login;

import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchToSignUpActionListener implements ActionListener {

    private LogInFrame logInFrame;
    private String serverUrl;

    public SwitchToSignUpActionListener(LogInFrame logInFrame, String serverUrl) {
        this.logInFrame = logInFrame;
        this.serverUrl = serverUrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SignUpFrame(serverUrl);
        logInFrame.dispose();
    }
}