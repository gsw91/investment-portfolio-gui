package com.invest.Gui.tables;

import java.time.LocalDate;

class StatisticsData {

    private String index;
    private Double buyingPrice;
    private LocalDate buyingDate;
    private Long quantity;
    private Double sellingPrice;
    private LocalDate sellingDate;
    private String result;
    private Double returnRate;
    private Long duration;

    protected StatisticsData(String index, Double buyingPrice, LocalDate buyingDate, Long quantity, Double sellingPrice, LocalDate sellingDate, String result, Double returnRate, Long duration) {
        this.index = index;
        this.buyingPrice = buyingPrice;
        this.buyingDate = buyingDate;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.sellingDate = sellingDate;
        this.result = result;
        this.returnRate = returnRate;
        this.duration = duration;
    }

    protected String getIndex() {
        return index;
    }

    protected Double getBuyingPrice() {
        return buyingPrice;
    }

    protected LocalDate getBuyingDate() {
        return buyingDate;
    }

    protected Long getQuantity() {
        return quantity;
    }

    protected Double getSellingPrice() {
        return sellingPrice;
    }

    protected LocalDate getSellingDate() {
        return sellingDate;
    }

    protected String getResult() {
        return result;
    }

    protected Double getReturnRate() {
        return returnRate;
    }

    protected Long getDuration() {
        return duration;
    }

}