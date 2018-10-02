package com.invest.Gui.frames;

import com.invest.Gui.listener.settings.ChangingFrameActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class ChangingFrame extends JFrame {

    private final static String TITLE = "Changing ";
    private ArrayList<JTextField> list = new ArrayList<>();
    private Long userId;
    private String typeParam;
    private String param;
    private JTextField oldUserParam;
    private JTextField newParamOne;
    private JTextField newParamTwo;
    private JButton closeButton;
    private JPanel textPanel;

    public ChangingFrame(Long userId, String typeParam, String param) {
        super(TITLE + typeParam);
        this.userId = userId;
        this.typeParam = typeParam;
        this.param = param;
        openFrame();
    }

    public String getTypeParam() {
        return typeParam;
    }

    public String getParam() {
        return param;
    }

    public JTextField getOldUserParam() {
        return oldUserParam;
    }

    public JTextField getNewParamOne() {
        return newParamOne;
    }

    public JTextField getNewParamTwo() {
        return newParamTwo;
    }

    private void openFrame() {
        configureComponents();
        configurePanels();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents() {
        JTextField old = new JTextField("Old " + typeParam);
        old.setEditable(false);
        old.setHorizontalAlignment(JTextField.CENTER);
        old.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(old);

        JTextField newOne = new JTextField("New " + typeParam);
        newOne.setEditable(false);
        newOne.setHorizontalAlignment(JTextField.CENTER);
        newOne.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(newOne);

        JTextField repeat = new JTextField("Repeat " + typeParam);
        repeat.setEditable(false);
        repeat.setHorizontalAlignment(JTextField.CENTER);
        repeat.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(repeat);

        closeButton = new JButton("Change it!");

        switch (typeParam) {
            case "password":
                configurePasswordFields();
                break;
            default:
                configureTextFields();
        }
    }

    private void configurePasswordFields() {
        oldUserParam = new JPasswordField();
        oldUserParam.setHorizontalAlignment(JPasswordField.CENTER);
        list.add(1, oldUserParam);

        newParamOne = new JPasswordField();
        newParamOne.setHorizontalAlignment(JPasswordField.CENTER);
        list.add(3, newParamOne);

        newParamTwo = new JPasswordField();
        newParamTwo.setHorizontalAlignment(JPasswordField.CENTER);
        list.add(5, newParamTwo);
    }

    private void configureTextFields() {
        oldUserParam = new JTextField(param);
        oldUserParam.setEditable(false);
        oldUserParam.setHorizontalAlignment(JTextField.CENTER);
        oldUserParam.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(1, oldUserParam);

        newParamOne = new JTextField();
        newParamOne.setHorizontalAlignment(JTextField.CENTER);
        newParamOne.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(3, newParamOne);

        newParamTwo = new JTextField();
        newParamTwo.setHorizontalAlignment(JTextField.CENTER);
        newParamTwo.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        list.add(5, newParamTwo);
    }

    private void configurePanels() {
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(3, 2));
        for (JTextField textField: list) {
            textPanel.add(textField);
        }
    }

    private void installListenersInComponents() {
        closeButton.addActionListener(new ChangingFrameActionListener(userId, this));
    }

    private void configureFrame() {
        this.setSize(380, 150);
        this.setLocation(600, 300);
        this.getContentPane().add(BorderLayout.CENTER, textPanel);
        this.getContentPane().add(BorderLayout.SOUTH, closeButton);
        this.setVisible(true);
    }

}
