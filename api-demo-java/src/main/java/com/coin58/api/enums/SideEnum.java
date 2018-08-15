package com.coin58.api.enums;

/**
 * @author coin58 - 2018/3/26.
 */
public enum SideEnum {
    BUY("buy"),

    SELL("sell");

    public String value;

    SideEnum(String value) {
        this.value = value;
    }
}
