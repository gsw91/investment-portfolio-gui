package com.invest.Gui.tables;

class QuotationsData {

    private String index;
    private String price;
    private String serverActualization;

    protected QuotationsData(String index, String price, String serverActualization) {
        this.index = index;
        this.price = price;
        this.serverActualization = serverActualization;
    }

    protected String getIndex() {
        return index;
    }

    protected String getPrice() {
        return price;
    }

    protected String getServerActualization() {
        return serverActualization;
    }

}
