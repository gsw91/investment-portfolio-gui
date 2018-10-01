package com.invest.Gui.frames;

import com.invest.Gui.listener.common.ShowHideActionListener;

import javax.swing.*;
import java.awt.*;

public final class WarningFrame extends JFrame {

    public static void openWarningFrame(String text) {
        WarningFrame warningFrame = new WarningFrame(text);
        warningFrame.openFrame();
    }

    private final static String TITLE = "Warning frame";
    private String text;
    private JTextField textField;
    private JButton closeButton;

    private WarningFrame(String text) {
        super(TITLE);
        this.text = text;
    }

    private void openFrame() {
       configureComponents();
       installListenersInComponents();
       configureFrame();
    }

    private void configureComponents() {
        textField = new JTextField();
        textField.setText(text);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        closeButton = new JButton("Close");
    }

    private void installListenersInComponents() {
        closeButton.addActionListener(new ShowHideActionListener(this, ShowHideActionListener.CLOSE));
    }

    private void configureFrame() {
        this.setSize(380,150);
        this.setLocation(600,300);
        this.getContentPane().add(BorderLayout.CENTER, textField);
        this.getContentPane().add(BorderLayout.SOUTH, closeButton);
        this.setVisible(true);
    }

}
