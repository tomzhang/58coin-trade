package com.coin58.api.services;

import com.coin58.api.api.Coin58APIs;
import com.coin58.api.enums.Site;
import com.coin58.api.enums.Sites;
import com.coin58.api.core.QueryString;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import static com.coin58.api.constant.Constants.LIMIT;
import static com.coin58.api.constant.Constants.SYMBOL;


/**
 * @author coin58 - 2018/3/26.
 */
@Component("coin58-publicService")
public class PublicService {

    private static final Site COIN58 = Sites.COIN58;

    public Request spotAllTickers() {
        return Request.Get(url(Coin58APIs.SPOT_TICKER));
    }

    public Request spotTicker(String symbol) {
        return Request.Get(url(Coin58APIs.SPOT_TICKER +
            QueryString.builder().query(SYMBOL, symbol).build()
        ));
    }

    /**
     * 深度
     *
     * @param symbol 币对
     * @param limit  default 60,  max is 200
     * @return request
     */
    public Request spotOrderBook(String symbol, int limit) {
        return Request.Get(url(Coin58APIs.ORDER_BOOK +
            QueryString.builder()
                .query(SYMBOL, symbol)
                .build()
        ));
    }

    public Request spotOrderBook(String symbol) {
        return spotOrderBook(symbol, 60);
    }

    /**
     * 查询成交历史
     *
     * @param symbol 币对
     * @param limit  default 50, max is 500
     * @return request
     */
    public Request spotTrades(String symbol, int limit) {
        return Request.Get(url(Coin58APIs.TRADES +
            QueryString.builder()
                .query(SYMBOL, symbol)
                .query(LIMIT, limit)
                .build()
        ));
    }

    public Request spotTrades(String symbol) {
        return spotTrades(symbol, 50);
    }

    private String url(String path) {
        return COIN58.getUrl() + path;
    }
}
