package com.invest.Gui.frames;

import com.invest.Gui.connection.LogInUrlCreator;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.exception.LogInException;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LogInFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(LogInFrame.class);

    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private String serverUrl;
    private JRadioButton remindButton;
    private JButton logIn;
    private JPanel userPanel;
    private JPanel remindingPanel;
    private LogInUrlCreator logInUrlCreator = new LogInUrlCreator();

    public LogInFrame(String serverUrl) throws HeadlessException {
        this.serverUrl = serverUrl;
        createLoginFrame();
    }

    private LogInFrame getFrame() {
        return this;
    }

    private void createLoginFrame() {
        this.setTitle("Investing app log in");
        this.setLocation(500, 300);

        logIn = new JButton("click to log in");
        logIn.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        logIn.addActionListener(new LogInActionListener());

        JButton signUp = new JButton("sign up");
        signUp.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        signUp.addActionListener(new SignUpActionListener());

        remindButton = new JRadioButton("forgot your login / password");
        remindButton.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
        remindButton.addActionListener(new ForgotActionListener());

        JButton exitButton = new JButton(" exit ");
        exitButton.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        exitButton.addActionListener(new ExitButtonActionListener());

        loginField = new JTextField();
        loginField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        loginField.addMouseListener(new TextAreaMouseListener());

        passwordField = new JPasswordField();
        passwordField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        passwordField.addMouseListener(new PasswordAreaMouseListener());

        emailField = new JTextField();
        emailField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        emailField.addMouseListener(new TextAreaMouseListener());

        JLabel loginLabel = new JLabel("Enter the user name", SwingConstants.CENTER);
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        loginLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel passwordLabel = new JLabel("Enter the password", SwingConstants.CENTER);
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel emailLabel = new JLabel("Enter your email", SwingConstants.CENTER);
        emailLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        emailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(2, 2));
        userPanel.add(loginLabel);
        userPanel.add(loginField);
        userPanel.add(passwordLabel);
        userPanel.add(passwordField);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 2));
        optionsPanel.add(signUp);
        optionsPanel.add(exitButton);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        southPanel.add(logIn);
        southPanel.add(remindButton);

        remindingPanel = new JPanel();
        remindingPanel.setLayout(new GridLayout(1, 2));
        remindingPanel.add(emailLabel);
        remindingPanel.add(emailField);

        this.getContentPane().add(BorderLayout.CENTER, userPanel);
        this.getContentPane().add(BorderLayout.SOUTH, southPanel);
        this.getContentPane().add(BorderLayout.NORTH, optionsPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 250);
        this.setVisible(true);
    }

    class ForgotActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (remindButton.isSelected()) {
                logIn.setText("send email");
                getFrame().getContentPane().remove(userPanel);
                getFrame().getContentPane().add(BorderLayout.CENTER, remindingPanel);
                getFrame().setSize(400, 200);
                getFrame().repaint();
            } else {
                logIn.setText("click to log in");
                getFrame().getContentPane().remove(remindingPanel);
                getFrame().getContentPane().add(BorderLayout.CENTER, userPanel);
                getFrame().setSize(400, 250);
                getFrame().repaint();
            }
        }
    }

    class ExitButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class SignUpActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SignUpFrame signUpFrame = new SignUpFrame("Sign up", serverUrl);
            signUpFrame.setVisible(true);
            getFrame().dispose();
        }
    }

    class LogInActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (logIn.getText().equals("click to log in")) {
                String login = loginField.getText();
                char[] chars = passwordField.getPassword();
                StringBuilder builder = new StringBuilder();
                for (char i : chars) {
                    builder.append(i);
                }
                String password = builder.toString();
                boolean condition = sendLogRequest(login, password);
                if (condition) {
                    LOGGER.info("User " + login + " logged in ");
                } else {
                    LOGGER.warn("Wrong login or password");
                }
            } else if (logIn.getText().equals("send email")) {
                String email = emailField.getText();
                boolean emailCondition = sendRemindingEmail(email);
                if (emailCondition) {
                    LOGGER.info("Email with credentials was sent to: " + email);
                    new LogInFrame(serverUrl);
                    getFrame().dispose();
                } else {
                    LOGGER.warn("There is no such email id database: " + email);
                }
            }
        }
    }

    private boolean sendRemindingEmail(String email) {
        boolean emailCondition = email.contains("@") && email.contains(".");
        if (emailCondition) {
            emailCondition = logInUrlCreator.sendReminderEmail(email);
        }
        return emailCondition;
    }

    private boolean sendLogRequest(String login, String password) throws LogInException {
        UserDto userDto = logInUrlCreator.createLoginRequest(login, password);
        if (userDto.getId()!=null) {
            UserFrame userFrame = new UserFrame(userDto, serverUrl);
            userFrame.openUserFrame();
            getFrame().dispose();
            return true;
        } else {
            return false;
        }
    }

    class TextAreaMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (loginField.isCursorSet()) {
                loginField.setText("");
            }
            if (emailField.isCursorSet()) {
                emailField.setText("");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class PasswordAreaMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            passwordField.setText("");
            passwordField.setEchoChar('*');
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

}
