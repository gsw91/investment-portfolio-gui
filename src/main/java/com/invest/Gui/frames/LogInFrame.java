package com.invest.Gui.frames;

import com.invest.Gui.listener.common.*;
import com.invest.Gui.listener.login.*;

import javax.swing.*;
import java.awt.*;

public class LogInFrame extends JFrame {

    private JTextField loginField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton logIn;
    private JButton signUp;
    private JButton exitButton;
    private JRadioButton remindButton;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JPanel userPanel;
    private JPanel remindingPanel;
    private JPanel optionsPanel;
    private JPanel southPanel;

    public LogInFrame() throws HeadlessException {
        createFrame();
    }

    public JRadioButton getRemindButton() {
        return remindButton;
    }

    public JButton getLogIn() {
        return logIn;
    }

    public JPanel getUserPanel() {
        return userPanel;
    }

    public JPanel getRemindingPanel() {
        return remindingPanel;
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    private void createFrame() {
        configureComponents();
        installListenersInComponents();
        configurePanels();
        configureFrame();
    }

    private void configureComponents() {
        remindButton = new JRadioButton("forgot your login / password");
        remindButton.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
        logIn = new JButton("click to log in");
        logIn.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        signUp = new JButton("sign up");
        signUp.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        exitButton = new JButton(" exit ");
        exitButton.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        loginField = new JTextField();
        loginField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        emailField = new JTextField();
        emailField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        loginLabel = new JLabel("Enter the user name", SwingConstants.CENTER);
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        loginLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel = new JLabel("Enter the password", SwingConstants.CENTER);
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        emailLabel = new JLabel("Enter your email", SwingConstants.CENTER);
        emailLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        emailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void installListenersInComponents() {
        logIn.addActionListener(new LogInActionListener(this));
        signUp.addActionListener(new SwitchToSignUpActionListener(this));
        exitButton.addActionListener(new ExitButtonActionListener());
        remindButton.addActionListener(new ReminderActionListener(this));
        emailField.addMouseListener(new TextFieldMouseListener(emailField));
        passwordField.addMouseListener(new PasswordFieldMouseListener(passwordField));
        loginField.addMouseListener(new TextFieldMouseListener(loginField));
    }

    private void configurePanels() {
        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(2, 2));
        userPanel.add(loginLabel);
        userPanel.add(loginField);
        userPanel.add(passwordLabel);
        userPanel.add(passwordField);
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 2));
        optionsPanel.add(signUp);
        optionsPanel.add(exitButton);
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        southPanel.add(logIn);
        southPanel.add(remindButton);
        remindingPanel = new JPanel();
        remindingPanel.setLayout(new GridLayout(1, 2));
        remindingPanel.add(emailLabel);
        remindingPanel.add(emailField);
    }

    private void configureFrame() {
        this.setTitle("Investing app log in");
        this.setLocation(500, 300);
        this.getContentPane().add(BorderLayout.CENTER, userPanel);
        this.getContentPane().add(BorderLayout.SOUTH, southPanel);
        this.getContentPane().add(BorderLayout.NORTH, optionsPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 250);
        this.setVisible(true);
    }

}
