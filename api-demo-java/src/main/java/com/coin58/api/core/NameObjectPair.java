package com.coin58.api.core;

import lombok.Data;
import org.apache.http.NameValuePair;

import java.math.BigDecimal;

/**
 * @author coin58
 * @date 2017/7/16.
 */
@Data
public class NameObjectPair implements NameValuePair {

    private String name;
    private Object value;

    public NameObjectPair(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).stripTrailingZeros().toPlainString();
        }
        return value.toString();
    }
}
