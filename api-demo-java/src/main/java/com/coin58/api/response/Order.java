package com.coin58.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {
 * "order_id": "4303100732",
 * "client_oid": "...",
 * "symbol": "LTC_BTC",
 * "type": "limit",
 * "side": "buy",
 * "price": "0.019708",
 * "amount": "0.322",
 * "base_filled": "0.0",
 * "quote_filled": "0.0",
 * "status": "received",
 * "created_time": 1521142156000
 * }
 *
 * @author coin58 - 2018/3/26.
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("client_oid")
    private String clientOid;
    private String symbol;
    private String type;
    private String side;
    private String price;
    private String amount;
    @JsonProperty("base_filled")
    private String baseFilled;
    @JsonProperty("quote_filled")
    private String quoteFilled;
    private String status;
    @JsonProperty("created_time")
    private long createdTime;
}
