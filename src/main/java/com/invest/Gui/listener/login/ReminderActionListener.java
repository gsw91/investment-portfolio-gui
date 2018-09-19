package com.invest.Gui.listener.login;

import com.invest.Gui.frames.LogInFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReminderActionListener implements ActionListener {

    private LogInFrame logInFrame;

    public ReminderActionListener(LogInFrame logInFrame) {
        this.logInFrame = logInFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JRadioButton remindButton = logInFrame.getRemindButton();
        JButton logIn = logInFrame.getLogIn();
        JPanel userPanel = logInFrame.getUserPanel();
        JPanel remindingPanel = logInFrame.getRemindingPanel();

        if (remindButton.isSelected()) {
            logIn.setText("send email");
            logInFrame.getContentPane().remove(userPanel);
            logInFrame.getContentPane().add(BorderLayout.CENTER, remindingPanel);
            logInFrame.setSize(400, 200);
            logInFrame.repaint();
        } else {
            logIn.setText("click to log in");
            logInFrame.getContentPane().remove(remindingPanel);
            logInFrame.getContentPane().add(BorderLayout.CENTER, userPanel);
            logInFrame.setSize(400, 250);
            logInFrame.repaint();
        }
    }

}
