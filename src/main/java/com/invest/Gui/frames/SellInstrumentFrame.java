package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SellInstrumentFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(SellInstrumentFrame.class);
    private JFrame userFrame;
    private UserDto userDto;
    private Boolean visibility;
    private JTextField instrumentName;
    private JTextField quantity;
    private JTextField price;
    private String serverUrl;

    protected SellInstrumentFrame(JFrame userFrame, UserDto userDto, Boolean visibility, String serverUrl) throws HeadlessException {
        this.userFrame = userFrame;
        this.userDto = userDto;
        this.visibility = visibility;
        this.serverUrl = serverUrl;
        openSellingWindow();
    }

    private SellInstrumentFrame getFrame() {
        return this;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private void openSellingWindow() {
        this.setTitle("Sell instrument");
        this.setLocation(500,300);
        this.setSize(300, 180);

        JButton confirmButton = new JButton("Sell");
        confirmButton.addActionListener(new ConfirmButtonActionListener());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonActionListener());

        instrumentName = new JTextField();
        quantity = new JTextField();
        price = new JTextField();

        this.setLayout(new GridLayout(4, 2));
        this.add(new JLabel("Instrument"));
        this.add(instrumentName);
        this.add(new JLabel("Quantity"));
        this.add(quantity);
        this.add(new JLabel("Price"));
        this.add(price);
        this.add(confirmButton);
        this.add(cancelButton);
        this.setVisible(visibility);
    }

    class ConfirmButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long userId = userDto.getId();
                String name = instrumentName.getText().toUpperCase();
                Long qtyToSell = convertToLong(quantity.getText());
                Double sellingPrice = convertToDouble(price.getText());
                boolean isSold = sellInstrument(userId, name, qtyToSell, sellingPrice);
                if (isSold) {
                    setVisible(false);
                    UserFrame newUserFrame = new UserFrame(userDto, serverUrl);
                    newUserFrame.openUserFrame();
                    userFrame.dispose();
                }
            } catch (NumberFormatException exc) {
                LOGGER.warn("Incorrect values inserted");
            } catch (IOException exce) {
                LOGGER.warn(exce.getMessage());
            }
        }

        private Long convertToLong(String quantity) throws NumberFormatException {
            return Long.valueOf(quantity);
        }

        private Double convertToDouble(String price) throws NumberFormatException {
            price = price.replace(",", ".");
            return Double.valueOf(price);
        }

        private boolean sellInstrument(Long userId, String name, Long quantity, Double sellingPrice) throws IOException {

            String request = serverUrl + "/v1/instrument/sell?userId=" + userId + "&name=" + name + "&quantity=" + quantity + "&price=" + sellingPrice;
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String message = reader.readLine();
            boolean isSold = Boolean.valueOf(message);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200 && isSold) {
                LOGGER.info("Instrument was sold, user " + userDto.getLogin() + ", instrument " + name + ", quantity/price " + quantity + "/" + sellingPrice);
                return true;
            } else {
                LOGGER.warn("Selling instrument failed, response code " + responseCode);
                return false;
            }
        }
    }

    class CancelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().setVisible(false);
        }
    }

}
