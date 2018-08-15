package com.coin58.api.core;


import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author coin58 - 2018/3/26.
 */
public class BodyForm {
    List<NameValuePair> formList;

    public BodyForm() {
        this.formList = new ArrayList<>();
    }

    public static BodyForm builder() {
        return new BodyForm();
    }

    public BodyForm field(String name, Object value) {
        formList.add(new NameObjectPair(name, value));
        return this;
    }

    public List<NameValuePair> build() {
        return formList;
    }
}
