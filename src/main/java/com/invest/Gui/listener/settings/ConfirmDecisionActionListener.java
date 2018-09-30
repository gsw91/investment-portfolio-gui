package com.invest.Gui.listener.settings;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SettingsFrame;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConfirmDecisionActionListener extends ConfirmDecisionRequestCreator implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(ConfirmDecisionActionListener.class);

    private SettingsFrame frame;
    private UserDto userDto;
    private UserFrame userFrame;

    public ConfirmDecisionActionListener(SettingsFrame frame) {
        super(frame.getUserDto().getId());
        this.frame = frame;
        this.userDto = frame.getUserDto();
        this.userFrame = frame.getUserFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton removeAccount = frame.getRemoveAccount();
        JRadioButton resetAccount = frame.getResetAccount();

        if (removeAccount.isSelected()) {
            tryDeleteAccount();
        } else if (resetAccount.isSelected()) {
            tryResetAccount();
        }
    }

    private void tryDeleteAccount() {
        try {
            deleteAccount();
            new LogInFrame();
            userFrame.closeAllFrames();
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }
    }

    private void tryResetAccount() {
        try {
            resetInstruments();
            resetStatistics();
            reloadFrame(userDto);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }
    }

    private void reloadFrame(UserDto userDto) {
        frame.setVisible(false);
        UserFrame newUserFrame = new UserFrame(userDto);
        newUserFrame.openUserFrame();
        frame.getUserFrame().closeAllFrames();
    }

}