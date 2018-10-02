package com.invest.Gui.listener.sellInstrument;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.PutRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.SellInstrumentFrame;
import com.invest.Gui.frames.WarningFrame;
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
    public boolean createPutRequest() {
        if (setValues()) {
            String[] params = createParams();
            String[] values = createValues();
            String request = generateUrlWithParams(ServiceConfig.INSTRUMENT_SELL, params, values);
            try {
                boolean isSold = getResponse(request, RequestMethod.PUT);
                return checkIfIsSold(isSold, frame.getUserDto());
            } catch (NumberFormatException exc) {
                LOGGER.warn("Incorrect setValues inserted");
                WarningFrame.openWarningFrame("Incorrect setValues inserted");
                return false;
            } catch (IOException ioe) {
                LOGGER.warn(ioe.getMessage());
                return false;
            }
        }
        return false;
    }

    private boolean setValues() {
        this.userId = String.valueOf(frame.getUserDto().getId());
        this.name = frame.getInstrumentName().getText().toUpperCase();
        try {
            this.quantity = String.valueOf(convertToLong(frame.getQuantity().getText()));
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Incorrect quantity inserted");
            WarningFrame.openWarningFrame("Incorrect quantity inserted");
            return false;
        }
        try {
            this.sellingPrice = String.valueOf(convertToDouble(frame.getPrice().getText()));
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Incorrect selling price inserted");
            WarningFrame.openWarningFrame("Incorrect selling price inserted");
            return false;
        }
        return true;
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
            WarningFrame.openWarningFrame("Incorrect instrument to sell");
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
