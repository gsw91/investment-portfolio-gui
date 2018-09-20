package com.invest.Gui.listener.common;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonActionListener implements ActionListener {

    public static int CLOSE = 0;

    private JFrame frame;
    private int operation;

    public CloseButtonActionListener(JFrame frame) {
        this.frame = frame;
    }

    public CloseButtonActionListener(JFrame frame, int operation) {
        this.frame = frame;
        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (operation) {
            case 0:
                frame.dispose();
                break;
            default:
                frame.setVisible(false);
        }
    }

}