package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(SettingsFrame.class);
    private JButton confirmButton;
    private JButton cancelButton;
    private JRadioButton resetAccount;
    private JRadioButton removeAccount;
    private UserFrame userFrame;
    private UserDto userDto;
    private String serverUrl;

    public SettingsFrame(UserFrame userFrame, UserDto userDto, String serverUrl) {
        this.userFrame = userFrame;
        this.userDto = userDto;
        this.serverUrl = serverUrl;
        createSettingFrame();
    }

    private SettingsFrame getFrame() {
        return this;
    }

    private void createSettingFrame(){
        this.setTitle("Account settings");
        this.setSize(400, 300);
        this.setLocation(500,300);
        this.setVisible(false);

        configureRadioButtons();
        configureButtons();

        JPanel radioButtonsPanel = new JPanel();
        radioButtonsPanel.setLayout(new GridLayout(8,1));
        radioButtonsPanel.add(removeAccount);
        radioButtonsPanel.add(resetAccount);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(8, 1));
        labelPanel.add(new JLabel("Delete your account and your identities"));
        //labelPanel.add(new JLabel("Reset your account (your instruments and statistics)"));

        this.getContentPane().add(BorderLayout.CENTER, labelPanel);
        this.getContentPane().add(BorderLayout.WEST, radioButtonsPanel);
        this.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
    }

    private void configureRadioButtons() {
        resetAccount = new JRadioButton();
        resetAccount.addActionListener(new ResetAccountActionListener());
        resetAccount.setVisible(false);

        removeAccount = new JRadioButton();
        removeAccount.addActionListener(new RemoveAccountActionListener());
    }

    private void configureButtons() {
        confirmButton = new JButton("confirm");
        confirmButton.setVisible(false);
        confirmButton.addActionListener(new ConfirmButtonActionListener());

        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(new CancelButtonActionListener());
    }

    class RemoveAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (removeAccount.isSelected()) {
                resetAccount.setSelected(false);
                confirmButton.setVisible(true);
            } else {
                confirmButton.setVisible(false);
            }
        }
    }

    class ResetAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (resetAccount.isSelected()) {
                removeAccount.setSelected(false);
                confirmButton.setVisible(true);
            } else {
                confirmButton.setVisible(false);
            }
        }
    }

    class ConfirmButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (removeAccount.isSelected()) {
                try {
                    deleteAccount();
                    new LogInFrame(serverUrl);
                    userFrame.closeAllFrames();
                } catch (IOException excep) {
                    LOGGER.error(excep.getMessage());
                }
            } else if (resetAccount.isSelected()) {
                resetAccount();
            }
        }

        private void deleteAccount() throws IOException {
            String request = serverUrl + "/v1/user/delete?userId=" + userDto.getId();
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String message = reader.readLine();
            boolean isRemoved = Boolean.valueOf(message);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200 && isRemoved) {
                LOGGER.info("Account successfully removed");
            } else {
                LOGGER.warn("Something has gone wrong, try later" + responseCode);
            }
        }

        private void resetAccount() {}

    }

    class CancelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().setVisible(false);
        }
    }


}
