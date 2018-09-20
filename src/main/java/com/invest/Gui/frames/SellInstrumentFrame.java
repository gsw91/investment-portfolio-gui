package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.common.CloseButtonActionListener;
import com.invest.Gui.listener.sellInstrument.SellInstrumentActionListener;

import javax.swing.*;
import java.awt.*;

public class SellInstrumentFrame extends JFrame {

    private UserFrame userFrame;
    private String serverUrl;
    private UserDto userDto;
    private JTextField instrumentName;
    private JTextField quantity;
    private JTextField price;
    private JButton confirmButton;
    private JButton cancelButton;

    protected SellInstrumentFrame(UserFrame userFrame, UserDto userDto, String serverUrl) throws HeadlessException {
        this.userFrame = userFrame;
        this.userDto = userDto;
        this.serverUrl = serverUrl;
        createSellWindow();
    }

    public UserFrame getUserFrame() {
        return userFrame;
    }

    public UserDto getUserDto() {
        return userDto;
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

    public String getServerUrl() {
        return serverUrl;
    }

    private void createSellWindow() {
        configureComponents();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents(){
        confirmButton = new JButton("Sell");
        cancelButton = new JButton("Cancel");
        instrumentName = new JTextField();
        quantity = new JTextField();
        price = new JTextField();
    }

    private void installListenersInComponents() {
        confirmButton.addActionListener(new SellInstrumentActionListener(this));
        cancelButton.addActionListener(new CloseButtonActionListener(this));
    }

    private void configureFrame() {
        this.setTitle("Sell instrument");
        this.setLocation(500,300);
        this.setSize(300, 180);
        this.setLayout(new GridLayout(4, 2));
        this.add(new JLabel("Instrument"));
        this.add(instrumentName);
        this.add(new JLabel("Quantity"));
        this.add(quantity);
        this.add(new JLabel("Price"));
        this.add(price);
        this.add(confirmButton);
        this.add(cancelButton);
        this.setVisible(false);
    }

}
