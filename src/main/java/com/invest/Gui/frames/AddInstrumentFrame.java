package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class AddInstrumentFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(AddInstrumentFrame.class);
    private UserDto userDto;
    private Boolean visibility;
    private JFrame userFrame;
    private JTextField instrumentName;
    private JTextField quantity;
    private JTextField price;
    private JTextField bought;
    private String serverUrl;

    protected AddInstrumentFrame(JFrame userFrame, UserDto userDto, Boolean visibility, String serverUrl) {
        this.userFrame = userFrame;
        this.userDto = userDto;
        this.visibility = visibility;
        this.serverUrl = serverUrl;
        createAddingInstrumentFrame();
    }

    private AddInstrumentFrame getFrame() {
        return this;
    }

    private void createAddingInstrumentFrame() {
        this.setTitle("Add instrument");
        this.setLocation(500,300);
        this.setSize(300,180);
        this.setLayout(new GridLayout(5,2));

        JButton confirmButton = new JButton("Buy");
        confirmButton.addActionListener(new ConfirmButtonActionListener());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonActionListener());

        instrumentName = new JTextField();
        quantity = new JTextField();
        price = new JTextField();
        bought = new JTextField();

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

        this.setVisible(visibility);
    }

    private class ConfirmButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String index = instrumentName.getText().toUpperCase();
                Long qty = convertToLong(quantity.getText());
                Double buingPrice = convertToDouble(price.getText());
                LocalDate date = convertToLocalDate(bought.getText());

                addingInstrument(index, qty, buingPrice, date);

                setVisible(false);
                UserFrame newUserFrame = new UserFrame(userDto, serverUrl);
                newUserFrame.openUserFrame();
                userFrame.dispose();

            } catch (DateTimeParseException dte) {
                LOGGER.warn("Incorrect data time inserted");
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Incorrect values inserted");
            } catch (IOException ioe) {
                LOGGER.error(ioe.getMessage());
            }
        }

        private Long convertToLong(String quantity) throws NumberFormatException {
            return Long.valueOf(quantity);
        }

        private Double convertToDouble(String price) throws NumberFormatException {
            price = price.replace(",", ".");
            return Double.valueOf(price);
        }

        private LocalDate convertToLocalDate(String date) throws DateTimeParseException {
            return LocalDate.parse(date);
        }

        private void addingInstrument(String index, Long quantity, Double buyingPrice, LocalDate buyingDate) throws IOException {

            String request = serverUrl + "/v1/instrument/add";
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            JSONObject cred = new JSONObject();
            cred.put("userId", userDto.getId());
            cred.put("quantity", quantity);
            cred.put("sharesIndex", index);
            cred.put("buyingPrice", buyingPrice);
            cred.put("buyingDate", buyingDate);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(cred.toString());
            wr.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("Instrument added for user " + userDto.getLogin());
            } else {
                LOGGER.warn("Adding instrument failed");
            }
        }

    }

    private class CancelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().setVisible(false);
        }
    }

}
