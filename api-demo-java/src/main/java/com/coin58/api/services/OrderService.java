package com.coin58.api.services;

import com.coin58.api.api.Coin58APIs;
import com.coin58.api.enums.OrderStatusEnum;
import com.coin58.api.enums.SideEnum;
import com.coin58.api.config.Coin58Config;
import com.coin58.api.core.BodyForm;
import com.coin58.api.core.NameObjectPair;
import com.coin58.api.core.QueryString;
import com.coin58.api.util.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.coin58.api.constant.Constants.*;
import static com.coin58.api.enums.Sites.COIN58;

/**
 * @author coin58 - 2018/3/26.
 */
@Service("coin58-orderService")
public class OrderService extends ServiceBase {
    private final Coin58Config coin58Config;

    @Autowired
    public OrderService(Coin58Config coin58Config) {
        super(COIN58);
        this.coin58Config = coin58Config;
    }

    public Request placeLimitOrder(String symbol, SideEnum side, String price, String amount, String
        clientOid) {
        List<NameValuePair> bodyFormList = BodyForm.builder()
            .field(CLIENT_OID, clientOid)
            .field(SYMBOL, symbol)
            .field(TYPE, LIMIT)
            .field(SIDE, side.value)
            .field(PRICE, price)
            .field(AMOUNT, amount)
            .build();
        return addSignHeader(Request
            .Post(url(Coin58APIs.PLACE_ORDER)), bodyFormList)
            .bodyForm(bodyFormList);
    }

    public Request placeMarketOrder(String symbol, SideEnum side, String amount, String clientOid) {
        List<NameValuePair> bodyFormList = BodyForm.builder()
            .field(CLIENT_OID, clientOid)
            .field(SYMBOL, symbol)
            .field(TYPE, MARKET)
            .field(SIDE, side.value)
            .field(AMOUNT, amount)
            .build();
        return addSignHeader(Request
            .Post(url(Coin58APIs.PLACE_ORDER)), bodyFormList)
            .bodyForm(bodyFormList);
    }

    public Request cancelOrder(String symbol, String orderId) {
        List<NameValuePair> bodyFormList = BodyForm.builder()
            .field(SYMBOL, symbol)
            .field(ORDER_ID, orderId)
            .build();
        return addSignHeader(Request
            .Post(url(Coin58APIs.CANCEL_ORDER)), bodyFormList)
            .bodyForm(bodyFormList);
    }

    public Request getOrder(String symbol, String orderId) {
        QueryString queryString = QueryString.builder()
            .query(SYMBOL, symbol)
            .query(ORDER_ID, orderId);
        return addSignHeader(Request
            .Get(url(Coin58APIs.GET_ORDER) + queryString.build()), queryString.getPairList());
    }

    /**
     * 订单
     *
     * @param symbol 币对
     * @param status “active”, “finished”, “cancelled” (default is “active”)
     * @param limit  default 10, max is 100
     * @return request
     */
    public Request orders(String symbol, OrderStatusEnum status, int limit) {
        QueryString queryString = QueryString.builder()
            .query(SYMBOL, symbol)
            .query(STATUS, status.value)
            .query(LIMIT, limit);
        List<NameValuePair> queryStringList = queryString.getPairList();
        return addSignHeader(Request
            .Get(url(Coin58APIs.ORDERS) + queryString.build()), queryStringList);
    }

    /**
     * Get a list of your past trades.
     *
     * @param symbol currency pair
     * @param limit  limit size
     * @return request
     */
    public Request orderTrades(String symbol, String orderId, int limit) {
        QueryString queryString = QueryString.builder()
            .query(ORDER_ID, orderId)
            .query(SYMBOL, symbol)
            .query(LIMIT, limit);
        List<NameValuePair> queryStringList = queryString.getPairList();
        return addSignHeader(Request
            .Get(url(Coin58APIs.ORDER_TRADES) + queryString.build()), queryStringList);
    }

    private Request addSignHeader(Request request, List<NameValuePair> bodyFormList) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        bodyFormList.add(new NameObjectPair(API_KEY, coin58Config.getApiKey()));
        return request.addHeader(X_58COIN_APIKEY, coin58Config.getApiKey())
            .addHeader(X_58COIN_SIGNATURE, StringUtils.base64Sha256Hex(bodyFormList, coin58Config.getSecretKey(), timestamp))
            .addHeader(X_58COIN_TIMESTAMP, timestamp);
    }

}
