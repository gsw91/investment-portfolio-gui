package com.invest.Gui.listener.common;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowHideActionListener implements ActionListener {

    public static int CLOSE = 0;
    public static int VISIBLE = 1;
    public static int INVISIBLE = 2;

    private JFrame frame;
    private int operation;

    public ShowHideActionListener(JFrame frame, int operation) {
        this.frame = frame;
        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (operation) {
            case 0:
                frame.dispose();
                break;
            case 1:
                frame.setVisible(true);
                break;
            case 2:
                frame.setVisible(false);
                break;
        }
    }

}