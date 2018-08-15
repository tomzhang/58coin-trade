package com.coin58.api.services;

import com.coin58.api.config.Coin58Config;
import com.coin58.api.core.NameObjectPair;
import com.coin58.api.enums.Site;
import com.coin58.api.enums.Sites;
import com.coin58.api.util.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.coin58.api.api.Coin58APIs.ACCOUNT;
import static com.coin58.api.constant.Constants.*;

/**
 * @author coin58 - 2018/3/26.
 */
@Component("coin58-accountService")
public class AccountService {
    private static final Site COIN58 = Sites.COIN58;
    private final Coin58Config coin58Config;

    @Autowired
    public AccountService(Coin58Config coin58Config) {
        this.coin58Config = coin58Config;
    }

    public Request account() {
        return addSignHeader(Request.Get(url(ACCOUNT)), new ArrayList<>());
    }

    private String url(String path) {
        return COIN58.getUrl() + path;
    }


    private Request addSignHeader(Request request, List<NameValuePair> bodyFormList) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        bodyFormList.add(new NameObjectPair(API_KEY, coin58Config.getApiKey()));
        return request
            .addHeader(X_58COIN_APIKEY, coin58Config.getApiKey())
            .addHeader(X_58COIN_SIGNATURE, StringUtils.base64Sha256Hex(bodyFormList, coin58Config.getSecretKey(), timestamp))
            .addHeader(X_58COIN_TIMESTAMP, timestamp);
    }
}
