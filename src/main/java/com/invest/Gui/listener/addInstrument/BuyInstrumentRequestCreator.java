package com.invest.Gui.listener.addInstrument;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.PostRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.frames.AddInstrumentFrame;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class BuyInstrumentRequestCreator implements PostRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(BuyInstrumentRequestCreator.class);
    private AddInstrumentFrame frame;
    private Long userId;
    private String index;
    private Long quantity;
    private Double buyingPrice;
    private LocalDate buyingDate;

    protected BuyInstrumentRequestCreator(AddInstrumentFrame frame) {
        this.frame = frame;
    }

    @Override
    public void addInstrument(Long userId) {
        setValues();
        String[] keys = createKeys();
        Object[] values = createValues();
        try {
            HttpURLConnection connection = createPostConnection();
            JSONObject jsonObject = createJson(keys, values);
            sendJsonObject(connection, jsonObject);

            if (connection.getResponseCode() == 200) {
                LOGGER.info("Instrument added for user " + userId);
            } else {
                LOGGER.warn("Adding instrument failed");
            }
        } catch (DateTimeParseException dte) {
            LOGGER.warn("Incorrect data time inserted");
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Incorrect setValues inserted");
        } catch (IOException ioe) {
            LOGGER.warn("Adding instrument failed " + ioe.getMessage());
        }
    }

    private void setValues() {
        userId = frame.getUserDto().getId();
        index = frame.getInstrumentName().getText().toUpperCase();
        quantity = convertToLong(frame.getQuantity().getText());
        buyingPrice = convertToDouble(frame.getPrice().getText());
        buyingDate = convertToLocalDate(frame.getBought().getText());
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

    private HttpURLConnection createPostConnection() throws IOException {
        String request = generateUrl(ServiceConfig.ADD_INSTRUMENT);
        return createPostConnection(request, RequestMethod.POST);
    }

    @Override
    public String[] createKeys() {
        return new String[]{"userId", "quantity", "sharesIndex", "buyingPrice", "buyingDate"};
    }

    @Override
    public Object[] createValues() {
        return new Object[]{userId, quantity, index, buyingPrice, buyingDate};
    }

}
