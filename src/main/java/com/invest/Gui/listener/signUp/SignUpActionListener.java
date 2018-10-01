package com.invest.Gui.listener.signUp;

import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpActionListener extends SignUpRequestCreator implements ActionListener {

    private SignUpFrame signUpFrame;

    public SignUpActionListener(SignUpFrame signUpFrame) {
        super(signUpFrame);
        this.signUpFrame = signUpFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sendPostRequest();
        if (isCreated) {
            new LogInFrame();
            signUpFrame.dispose();
            isCreated = false;
        }
    }

}