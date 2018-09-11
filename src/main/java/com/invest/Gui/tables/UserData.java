package com.invest.Gui.tables;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UserData {

    private String name;
    private Long quantity;
    private BigDecimal buy;
    private BigDecimal now;
    private BigDecimal value;
    private String change;
    private BigDecimal result;

    public UserData(String name, Long quantity, BigDecimal buy, BigDecimal now) {
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

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getNow() {
        return now;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getChange() {
        return change;
    }

    public BigDecimal getResult() {
        return result;
    }
}
