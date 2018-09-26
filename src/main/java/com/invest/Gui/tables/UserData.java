package com.invest.Gui.tables;

import java.math.BigDecimal;
import java.math.RoundingMode;

class UserData {

    private String name;
    private Long quantity;
    private BigDecimal buy;
    private BigDecimal now;
    private BigDecimal value;
    private String change;
    private BigDecimal result;

    protected UserData(String name, Long quantity, BigDecimal buy, BigDecimal now) {
        this.name = name;
        this.quantity = quantity;
        this.buy = buy;
        this.now = now;
        value = now.multiply(BigDecimal.valueOf(quantity));
        change = String.valueOf((now.subtract(buy)).multiply(BigDecimal.valueOf(100L)).divide(buy, 2, RoundingMode.CEILING));
        result = value.subtract( BigDecimal.valueOf(quantity).multiply(buy));
    }

    public String getName() {
        return name;
    }

    protected Long getQuantity() {
        return quantity;
    }

    protected BigDecimal getBuy() {
        return buy;
    }

    protected BigDecimal getNow() {
        return now;
    }

    protected BigDecimal getValue() {
        return value;
    }

    protected String getChange() {
        return change;
    }

    protected BigDecimal getResult() {
        return result;
    }
}
