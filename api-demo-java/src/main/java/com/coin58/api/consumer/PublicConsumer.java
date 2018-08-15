package com.coin58.api.consumer;

import com.coin58.api.response.Coin58Resp;
import com.coin58.api.response.OrderBook;
import com.coin58.api.response.SpotTicker;
import com.coin58.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author coin58 - 2018/3/24.
 */
@Component("coin58-publicConsumer")
@Slf4j
public class PublicConsumer {

    public void handleSpotTicker(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<List<SpotTicker>> result = JsonUtils.parseJson(json,
            new TypeReference<Coin58Resp<List<SpotTicker>>>() {
            });
        log.info("Spot ticker result [{}]", result);
    }

    public void handleOrderBook(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<OrderBook> result = JsonUtils.parseJson(json,
            new TypeReference<Coin58Resp<OrderBook>>() {
            });
        log.info("Order book result [{}]", result);
    }

    public void handleTrades(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<JsonNode> result = JsonUtils.parseJson(json,
            new TypeReference<Coin58Resp<JsonNode>>() {
            });
        log.info("Trades result [{}]", result);
    }
}
