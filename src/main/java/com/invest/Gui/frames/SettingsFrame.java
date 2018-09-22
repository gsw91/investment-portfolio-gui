package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.common.ShowHideActionListener;
import com.invest.Gui.listener.settings.*;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    private JButton confirmButton;
    private JButton cancelButton;
    private JRadioButton resetAccount;
    private JRadioButton removeAccount;
    private UserFrame userFrame;
    private UserDto userDto;
    private JPanel radioButtonsPanel;
    private JPanel buttonsPanel;
    private JPanel labelPanel;

    public SettingsFrame(UserFrame userFrame, UserDto userDto) {
        this.userFrame = userFrame;
        this.userDto = userDto;
        createSettingFrame();
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JRadioButton getResetAccount() {
        return resetAccount;
    }

    public JRadioButton getRemoveAccount() {
        return removeAccount;
    }

    public UserFrame getUserFrame() {
        return userFrame;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    private void createSettingFrame(){
        configureComponents();
        installListenersInComponents();
        configurePanels();
        configureFrame();
    }

    private void configureComponents() {
        removeAccount = new JRadioButton();
        resetAccount = new JRadioButton();
        confirmButton = new JButton("confirm");
        confirmButton.setVisible(false);
        cancelButton = new JButton("cancel");
    }

    private void installListenersInComponents() {
        removeAccount.addActionListener(new RemoveAccountActionListener(this));
        resetAccount.addActionListener(new ResetAccountActionListener(this));
        confirmButton.addActionListener(new ConfirmDecisionActionListener(this));
        cancelButton.addActionListener(new ShowHideActionListener(this, ShowHideActionListener.INVISIBLE));
    }

    private void configurePanels() {
        radioButtonsPanel = new JPanel();
        radioButtonsPanel.setLayout(new GridLayout(8,1));
        radioButtonsPanel.add(removeAccount);
        radioButtonsPanel.add(resetAccount);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(8, 1));
        labelPanel.add(new JLabel("Delete your account and your identities"));
        labelPanel.add(new JLabel("Reset your account (your instruments and statistics)"));
    }

    private void configureFrame() {
        this.setTitle("Account settings");
        this.setSize(400, 300);
        this.setLocation(500,300);
        this.setVisible(false);
        this.getContentPane().add(BorderLayout.CENTER, labelPanel);
        this.getContentPane().add(BorderLayout.WEST, radioButtonsPanel);
        this.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
    }

}
