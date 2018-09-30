package com.invest.Gui.listener.addInstrument;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.PostRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.frames.AddInstrumentFrame;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

class BuyInstrumentRequestCreator implements PostRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(BuyInstrumentRequestCreator.class);
    protected static boolean IS_INSTRUMENT_ADDED = false;
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
    public void sendPostRequest() {
        if(setValues()) {
            String[] keys = createKeys();
            Object[] values = createValues();
            try {
                HttpURLConnection connection = createPostConnection();
                JSONObject jsonObject = createJson(keys, values);
                sendJsonObject(connection, jsonObject);

                int responseCode = connection.getResponseCode();
                Set<String> val = checkAnswer(connection);

                if (responseCode == 200 && val.size() != 1) {
                    LOGGER.info("Instrument added for user " + userId);
                    IS_INSTRUMENT_ADDED = true;
                } else {
                    LOGGER.warn("Adding instrument failed");
                }
            } catch (IOException ioe) {
                LOGGER.warn("Adding instrument failed " + ioe.getMessage());
            }
        }
    }

    private Set<String> checkAnswer(HttpURLConnection connection) throws IOException {
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String response = bufferedReader.readLine();
        response = removeSigns(response);
        String[] array = response.split(",");
        Set<String> val = new HashSet<>();
        for(String i: array) {
            String[] arr = i.split(":");
            val.add(arr[1]);
        }
        return val;
    }

    private boolean setValues() {
        userId = frame.getUserDto().getId();
        index = frame.getInstrumentName().getText().toUpperCase();
        try {
            quantity = convertToLong(frame.getQuantity().getText());
        } catch (NumberFormatException nfe) {
            LOGGER.error("Incorrect quantity value");
            return false;
        }
        try {
            buyingPrice = convertToDouble(frame.getPrice().getText());
        } catch (NumberFormatException nfe) {
            LOGGER.error("Incorrect buying price value");
            return false;
        }
        try {
            buyingDate = convertToLocalDate(frame.getBought().getText());
        } catch (DateTimeParseException dtpe) {
            LOGGER.error("Incorrect date");
            return false;
        }
        return true;
    }

    private Long convertToLong(String quantity) throws NumberFormatException {
        return Long.valueOf(quantity);
    }

    private Double convertToDouble(String price) throws NumberFormatException {
        price = price.replace(",", ".");
        return Double.valueOf(price);
    }

    private LocalDate convertToLocalDate(String date) throws DateTimeParseException {
        if (date.length()==0) {
            return LocalDate.now();
        }
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
