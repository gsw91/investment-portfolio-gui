package com.invest.Gui.listener.signUp;

import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchToLogInActionListener implements ActionListener {

    private String serverUrl;
    private SignUpFrame signUpFrame;

    public SwitchToLogInActionListener(String serverUrl, SignUpFrame signUpFrame) {
        this.serverUrl = serverUrl;
        this.signUpFrame = signUpFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new LogInFrame(serverUrl);
        signUpFrame.dispose();
    }
}