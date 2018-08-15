package com.coin58.api.enums;

/**
 * @author coin58 - 2018/3/26.
 */
public enum OrderStatusEnum {
    RECEIVED("received"),
    ACTIVE("active"),
    FINISHED("finished"),
    CANCELING("canceling"),
    CANCELLED("cancelled"),;
    public String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }
}
