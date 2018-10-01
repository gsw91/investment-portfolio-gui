package com.invest.Gui.listener.settings;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.DeleteRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import org.apache.log4j.Logger;

import java.io.IOException;

class ConfirmDecisionRequestCreator implements DeleteRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(ConfirmDecisionRequestCreator.class);
    private final static String ACCOUNT = "Account";
    private final static String INSTRUMENT = "Instruments";
    private final static String STATS = "Statistics";
    private final static String SUCCESS = " successfully removed.";
    private final static String FAILED =  " can not be removed, something has gone wrong";
    private Long userId;

    protected ConfirmDecisionRequestCreator(Long userId) {
        this.userId = userId;
    }

    protected void deleteAccount() throws IOException {
        String request = generateUrlWithParams(ServiceConfig.USER_DELETE, createParams(), createValues());
        boolean isRemoved = createRequest(request, RequestMethod.DELETE);
        if (isRemoved) {
            LOGGER.info(ACCOUNT + SUCCESS);
        } else {
            LOGGER.warn(ACCOUNT + FAILED);
        }
    }

    protected void resetInstruments() throws IOException {
        String request = generateUrlWithParams(ServiceConfig.INSTRUMENT_RESET, createParams(), createValues());
        boolean isRemoved = createRequest(request, RequestMethod.DELETE);
        if (isRemoved) {
            LOGGER.info(INSTRUMENT + SUCCESS);
        } else {
            LOGGER.error(INSTRUMENT + FAILED);
        }
    }

    protected void resetStatistics() throws IOException {
        String request = generateUrlWithParams(ServiceConfig.STATS_RESET, createParams(), createValues());
        boolean isRemoved = createRequest(request, RequestMethod.DELETE);
        if (isRemoved) {
            LOGGER.info(STATS + SUCCESS);
        } else {
            LOGGER.error(STATS + FAILED);
        }
    }

    @Override
    public String[] createParams() {
        return new String[]{"userId"};
    }

    @Override
    public String[] createValues() {
        return new String[]{String.valueOf(userId)};
    }
}
