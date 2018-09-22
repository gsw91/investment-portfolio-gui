package com.invest.Gui.listener.signUp;

import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchToLogInActionListener implements ActionListener {

    private SignUpFrame signUpFrame;

    public SwitchToLogInActionListener(SignUpFrame signUpFrame) {
        this.signUpFrame = signUpFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new LogInFrame();
        signUpFrame.dispose();
    }
}