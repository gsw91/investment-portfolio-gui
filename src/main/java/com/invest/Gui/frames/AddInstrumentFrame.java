package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.addInstrument.BuyInstrumentActionListener;
import com.invest.Gui.listener.common.CloseButtonActionListener;
import javax.swing.*;
import java.awt.*;

public class AddInstrumentFrame extends JFrame {

    private UserDto userDto;
    private UserFrame userFrame;
    private JTextField instrumentName;
    private JTextField quantity;
    private JTextField price;
    private JTextField bought;
    private String serverUrl;
    private JButton confirmButton;
    private JButton cancelButton;

    public AddInstrumentFrame(UserFrame userFrame, UserDto userDto, String serverUrl) {
        this.userFrame = userFrame;
        this.userDto = userDto;
        this.serverUrl = serverUrl;
        createAddingInstrumentFrame();
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public UserFrame getUserFrame() {
        return userFrame;
    }

    public JTextField getInstrumentName() {
        return instrumentName;
    }

    public JTextField getQuantity() {
        return quantity;
    }

    public JTextField getPrice() {
        return price;
    }

    public JTextField getBought() {
        return bought;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    private void createAddingInstrumentFrame() {
        configureComponents();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents() {
        confirmButton = new JButton("Buy");
        cancelButton = new JButton("Cancel");
        instrumentName = new JTextField();
        quantity = new JTextField();
        price = new JTextField();
        bought = new JTextField();
    }

    private void installListenersInComponents() {
        confirmButton.addActionListener(new BuyInstrumentActionListener(this));
        cancelButton.addActionListener(new CloseButtonActionListener(this));
    }

    private void configureFrame() {
        this.setTitle("Add instrument");
        this.setLocation(500,300);
        this.setSize(300,180);
        this.setLayout(new GridLayout(5,2));
        this.getContentPane().add(new JLabel("Instrument name"));
        this.getContentPane().add(instrumentName);
        this.getContentPane().add(new JLabel("Quantity"));
        this.getContentPane().add(quantity);
        this.getContentPane().add(new JLabel("Price"));
        this.getContentPane().add(price);
        this.getContentPane().add(new JLabel("Bought YYYY-MM-DD"));
        this.getContentPane().add(bought);
        this.getContentPane().add(confirmButton);
        this.getContentPane().add(cancelButton);
        this.setVisible(false);
    }

}
