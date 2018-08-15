package com.coin58.api.consumer;

import com.coin58.api.response.Coin58Resp;
import com.coin58.api.response.Order;
import com.coin58.api.response.Trade;
import com.coin58.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author coin58 - 2018/3/26.
 */
@Component("coin58-orderConsumer")
@Slf4j
public class OrderConsumer {

    public void handlePlaceOrder(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<Order> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<Order>>() {
        });
        log.info("Place order result [{}]", result);
    }

    public void handleCancelOrder(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<Order> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<Order>>() {
        });
        log.info("Cancel order result [{}]", result);
    }

    public void handleGetOrder(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<Order> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<Order>>() {
        });
        log.info("Get order result [{}]", result);
    }

    public void handleOrders(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<List<Order>> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<List<Order>>>() {
        });
        log.info("Orders result [{}]", result);
    }

    public void handleOrderTrades(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<List<Trade>> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<List<Trade>>>() {
        });
        log.info("Order trades result [{}]", result);
    }
}
