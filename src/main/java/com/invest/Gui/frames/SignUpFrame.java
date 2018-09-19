package com.invest.Gui.frames;

import com.invest.Gui.listener.common.*;
import com.invest.Gui.listener.signUp.*;

import javax.swing.*;
import java.awt.*;

public class SignUpFrame extends JFrame {

    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private String serverUrl;
    private JButton exitButton;
    private JButton logIn;
    private JButton signUp;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JPanel panel;
    private JPanel optionsPanel;


    public SignUpFrame(String serverUrl) throws HeadlessException {
        this.serverUrl = serverUrl;
        this.createFrame();
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

        this.setTitle("Sign up");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(500,300);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.getContentPane().add(BorderLayout.SOUTH, signUp);
        this.getContentPane().add(BorderLayout.NORTH, optionsPanel);
        this.setSize(400,250);
        this.setVisible(true);
    }

    private void configureComponents() {
        loginField = new JTextField();
        loginField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        exitButton = new JButton(" exit ");
        exitButton.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        emailField = new JTextField();
        emailField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        logIn = new JButton("log in");
        logIn.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        signUp = new JButton(" click to sign up");
        signUp.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        loginLabel = new JLabel("Enter the user name", SwingConstants.CENTER);
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        loginLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        passwordLabel = new JLabel("Enter the password", SwingConstants.CENTER);
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        emailLabel = new JLabel("Enter the email", SwingConstants.CENTER);
        emailLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        emailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void installListenersInComponents() {
        signUp.addActionListener(new SignUpActionListener(serverUrl, this));
        logIn.addActionListener(new SwitchToLogInActionListener(serverUrl, this));
        emailField.addMouseListener(new TextFieldMouseListener(emailField));
        exitButton.addActionListener(new ExitButtonActionListener());
        passwordField.addMouseListener(new PasswordFieldMouseListener(passwordField));
        loginField.addMouseListener(new TextFieldMouseListener(loginField));
    }

    private void configurePanels() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 2));
        optionsPanel.add(logIn);
        optionsPanel.add(exitButton);
    }

}
