package com.invest.Gui.listener.settings;

import com.invest.Gui.frames.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetAccountActionListener implements ActionListener {

    private SettingsFrame settingsFrame;

    public ResetAccountActionListener(SettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton removeAccount = settingsFrame.getRemoveAccount();
        JRadioButton resetAccount = settingsFrame.getResetAccount();
        JButton confirmButton = settingsFrame.getConfirmButton();

        if (resetAccount.isSelected()) {
            removeAccount.setSelected(false);
            confirmButton.setVisible(true);
        } else {
            confirmButton.setVisible(false);
        }
    }

}