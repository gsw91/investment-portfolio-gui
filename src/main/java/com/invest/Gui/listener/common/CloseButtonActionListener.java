package com.invest.Gui.listener.common;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonActionListener implements ActionListener {

    private JFrame frame;

    public CloseButtonActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
    }
}