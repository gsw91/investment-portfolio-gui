package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.common.ShowHideActionListener;
import com.invest.Gui.listener.settings.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SettingsFrame extends JFrame {

    private JButton confirmButton;
    private JButton cancelButton;
    private Set<JRadioButton> setButtons;
    private JRadioButton resetAccountButton;
    private JRadioButton removeAccountButton;
    private JRadioButton changeLoginButton;
    private JRadioButton changePasswordButton;
    private JRadioButton changeEmailButton;
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

    public JRadioButton getChangeLoginButton() {
        return changeLoginButton;
    }

    public JRadioButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JRadioButton getChangeEmailButton() {
        return changeEmailButton;
    }

    public JRadioButton getResetAccountButton() {
        return resetAccountButton;
    }

    public JRadioButton getRemoveAccountButton() {
        return removeAccountButton;
    }

    public UserFrame getUserFrame() {
        return userFrame;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    private void createSettingFrame(){
        configureComponents();
        createSetOfButtons();
        installListenersInComponents();
        configurePanels();
        configureFrame();
    }

    private void configureComponents() {
        removeAccountButton = new JRadioButton();
        resetAccountButton = new JRadioButton();
        changeLoginButton = new JRadioButton();
        changePasswordButton = new JRadioButton();
        changeEmailButton = new JRadioButton();
        confirmButton = new JButton("confirm");
        confirmButton.setVisible(false);
        cancelButton = new JButton("cancel");
    }

    private void installListenersInComponents() {
        removeAccountButton.addActionListener(new SettingsFrameActionListener(this, removeAccountButton));
        resetAccountButton.addActionListener(new SettingsFrameActionListener(this, resetAccountButton));
        changeLoginButton.addActionListener(new SettingsFrameActionListener(this, changeLoginButton));
        changePasswordButton.addActionListener(new SettingsFrameActionListener(this, changePasswordButton));
        changeEmailButton.addActionListener(new SettingsFrameActionListener(this, changeEmailButton));
        confirmButton.addActionListener(new ConfirmDecisionActionListener(this));
        cancelButton.addActionListener(new ShowHideActionListener(this, ShowHideActionListener.INVISIBLE));
    }

    private void configurePanels() {
        radioButtonsPanel = new JPanel();
        radioButtonsPanel.setLayout(new GridLayout(8,1));
        radioButtonsPanel.add(removeAccountButton);
        radioButtonsPanel.add(resetAccountButton);
        radioButtonsPanel.add(changeLoginButton);
        radioButtonsPanel.add(changePasswordButton);
        radioButtonsPanel.add(changeEmailButton);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(8, 1));
        labelPanel.add(new JLabel("Delete your account and your identities"));
        labelPanel.add(new JLabel("Reset your account (your instruments and statistics)"));
        labelPanel.add(new JLabel("Login change"));
        labelPanel.add(new JLabel("Password change"));
        labelPanel.add(new JLabel("Email change"));
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

    private void createSetOfButtons() {
        setButtons = new HashSet<>();
        setButtons.add(removeAccountButton);
        setButtons.add(resetAccountButton);
        setButtons.add(changeLoginButton);
        setButtons.add(changePasswordButton);
        setButtons.add(changeEmailButton);
    }

    public void chooseButton(JRadioButton button) {
        if (button.isSelected()) {
            setButtons.remove(button);
            for (JRadioButton radioButton: setButtons){
                radioButton.setSelected(false);
            }
            confirmButton.setVisible(true);
            setButtons.add(button);
        } else {
            confirmButton.setVisible(false);
        }
    }

}
