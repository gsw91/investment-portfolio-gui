package com.invest.Gui.listener.addInstrument;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConfirmButtonActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(ConfirmButtonActionListener.class);
    private AddInstrumentFrame frame;

    public ConfirmButtonActionListener(AddInstrumentFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            String serverUrl = frame.getServerUrl();
            UserDto userDto = frame.getUserDto();
            String index = frame.getInstrumentName().getText().toUpperCase();
            Long qty = convertToLong(frame.getQuantity().getText());
            Double buyingPrice = convertToDouble(frame.getPrice().getText());
            LocalDate date = convertToLocalDate(frame.getBought().getText());

        try {
            addingInstrument(serverUrl, userDto, index, qty, buyingPrice, date);
            reloadFrame(userDto, serverUrl);
        } catch (DateTimeParseException dte) {
            LOGGER.warn("Incorrect data time inserted");
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Incorrect values inserted");
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }
    }

    private void reloadFrame(UserDto userDto, String serverUrl) {
        frame.setVisible(false);
        UserFrame newUserFrame = new UserFrame(userDto, serverUrl);
        newUserFrame.openUserFrame();
        frame.getUserFrame().closeAllFrames();
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

    private void addingInstrument(String serverUrl, UserDto userDto, String index, Long quantity, Double buyingPrice, LocalDate buyingDate) throws IOException {

        HttpURLConnection connection = createPostConnection(serverUrl);
        JSONObject jsonObject = createJson(userDto, index, quantity, buyingPrice, buyingDate);
        sendJsonObject(connection, jsonObject);

        if (connection.getResponseCode() == 200) {
            LOGGER.info("Instrument added for user " + userDto.getLogin());
        } else {
            LOGGER.warn("Adding instrument failed");
        }
    }

    private HttpURLConnection createPostConnection(String serverUrl) throws IOException {
        String request = serverUrl + "/v1/instrument/add";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        return connection;
    }

    private JSONObject createJson(UserDto userDto, String index, Long quantity, Double buyingPrice, LocalDate buyingDate) {
        JSONObject json = new JSONObject();
        json.put("userId", userDto.getId());
        json.put("quantity", quantity);
        json.put("sharesIndex", index);
        json.put("buyingPrice", buyingPrice);
        json.put("buyingDate", buyingDate);
        return json;
    }

    private void sendJsonObject(HttpURLConnection connection, JSONObject jsonObject) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(jsonObject.toString());
        wr.flush();
    }

}