package com.invest.Gui.listener.settings;

import com.invest.Gui.frames.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrameActionListener implements ActionListener {

    private SettingsFrame settingsFrame;
    private JRadioButton radioButton;

    public SettingsFrameActionListener(SettingsFrame settingsFrame, JRadioButton radioButton) {
        this.settingsFrame = settingsFrame;
        this.radioButton = radioButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        settingsFrame.chooseButton(radioButton);
    }

}
