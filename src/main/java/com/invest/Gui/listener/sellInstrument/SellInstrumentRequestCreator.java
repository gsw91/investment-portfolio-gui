package com.invest.Gui.listener.sellInstrument;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.PutRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.SellInstrumentFrame;
import org.apache.log4j.Logger;

import java.io.IOException;

class SellInstrumentRequestCreator implements PutRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(SellInstrumentRequestCreator.class);
    private SellInstrumentFrame frame;
    private String userId;
    private String name;
    private String quantity;
    private String sellingPrice;

    protected SellInstrumentRequestCreator(SellInstrumentFrame frame) {
        this.frame = frame;
    }

    @Override
    public boolean sellInstrument() {
        setValues();
        String[] params = createParams();
        String[] values = createValues();
        String request = generateUrlWithParams(ServiceConfig.INSTRUMENT_SELL, params, values);
        try {
            boolean isSold = getResponse(request, RequestMethod.PUT);
            return checkIfIsSold(isSold, frame.getUserDto());
        } catch (NumberFormatException exc) {
            LOGGER.warn("Incorrect setValues inserted");
            return false;
        } catch (IOException ioe) {
            LOGGER.warn(ioe.getMessage());
            return false;
        }
    }

    private void setValues() {
        this.userId = String.valueOf(frame.getUserDto().getId());
        this.name = frame.getInstrumentName().getText().toUpperCase();
        this.quantity = String.valueOf(convertToLong(frame.getQuantity().getText()));
        this.sellingPrice = String.valueOf(convertToDouble(frame.getPrice().getText()));
    }

    @Override
    public String[] createParams() {
        return new String[]{"userId", "name", "quantity", "price"};
    }

    @Override
    public String[] createValues() {
        return new String[]{userId, name, quantity, sellingPrice};
    }

    private boolean checkIfIsSold(boolean isSold, UserDto userDto) {
        if (isSold) {
            LOGGER.info("Instrument was sold, user " + userDto.getLogin() + ", instrument " + name + ", quantity/price " + quantity + "/" + sellingPrice);
            return true;
        } else {
            LOGGER.warn("Selling instrument failed");
            return false;
        }
    }

    private Long convertToLong(String quantity) throws NumberFormatException {
        return Long.valueOf(quantity);
    }

    private Double convertToDouble(String price) throws NumberFormatException {
        price = price.replace(",", ".");
        return Double.valueOf(price);
    }

}
