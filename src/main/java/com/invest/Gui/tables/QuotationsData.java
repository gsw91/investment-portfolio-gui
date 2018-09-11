package com.invest.Gui.tables;

public class QuotationsData {

    private String index;
    private String price;
    private String serverActualization;

    public QuotationsData(String index, String price, String serverActualization) {
        this.index = index;
        this.price = price;
        this.serverActualization = serverActualization;
    }

    public String getIndex() {
        return index;
    }

    public String getPrice() {
        return price;
    }

    public String getServerActualization() {
        return serverActualization;
    }

}
