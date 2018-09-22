package com.invest.Gui.listener.login;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchToSignUpActionListener implements ActionListener {

    private LogInFrame logInFrame;

    public SwitchToSignUpActionListener(LogInFrame logInFrame) {
        this.logInFrame = logInFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SignUpFrame();
        logInFrame.dispose();
    }
}