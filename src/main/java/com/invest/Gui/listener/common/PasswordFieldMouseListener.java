package com.invest.Gui.listener.common;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PasswordFieldMouseListener implements MouseListener {

    private JPasswordField passwordField;

    public PasswordFieldMouseListener(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

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
