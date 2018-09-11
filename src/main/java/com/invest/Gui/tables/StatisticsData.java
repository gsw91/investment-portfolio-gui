package com.invest.Gui.tables;

import java.time.LocalDate;

public class StatisticsData {

    private String index;
    private Double buyingPrice;
    private LocalDate buyingDate;
    private Long quantity;
    private Double sellingPrice;
    private LocalDate sellingDate;
    private String result;
    private Double returnRate;
    private Long duration;

    public StatisticsData(String index, Double buyingPrice, LocalDate buyingDate, Long quantity, Double sellingPrice, LocalDate sellingDate, String result, Double returnRate, Long duration) {
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

    public String getIndex() {
        return index;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public LocalDate getBuyingDate() {
        return buyingDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public LocalDate getSellingDate() {
        return sellingDate;
    }

    public String getResult() {
        return result;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public Long getDuration() {
        return duration;
    }

}