package com.invest.Gui.listener.sellInstrument;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.SellInstrumentFrame;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SellInstrumentActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(SellInstrumentActionListener.class);
    private SellInstrumentFrame sellInstrumentFrame;

    public SellInstrumentActionListener(SellInstrumentFrame sellInstrumentFrame) {
        this.sellInstrumentFrame = sellInstrumentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDto userDto = sellInstrumentFrame.getUserDto();
        String name = sellInstrumentFrame.getInstrumentName().getText().toUpperCase();
        String serverUrl = ServiceConfig.SERVER_URL;
        UserFrame userFrame = sellInstrumentFrame.getUserFrame();
        try {
            Long qtyToSell = convertToLong(sellInstrumentFrame.getQuantity().getText());
            Double sellingPrice = convertToDouble(sellInstrumentFrame.getPrice().getText());
            boolean isSold = sellInstrument(userDto, serverUrl, name, qtyToSell, sellingPrice);
            if (isSold) {
                sellInstrumentFrame.setVisible(false);
                UserFrame newUserFrame = new UserFrame(userDto);
                newUserFrame.openUserFrame();
                userFrame.closeAllFrames();
            }
        } catch (NumberFormatException exc) {
            LOGGER.warn("Incorrect setValues inserted");
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

    private boolean sellInstrument(UserDto userDto, String serverUrl, String name, Long quantity, Double sellingPrice) throws IOException {

        String request = serverUrl + ServiceConfig.INSTRUMENT_SELL + "userId=" + userDto.getId() + "&name=" + name + "&quantity=" + quantity + "&price=" + sellingPrice;
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