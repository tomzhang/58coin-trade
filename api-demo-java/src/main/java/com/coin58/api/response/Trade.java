package com.coin58.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 * "trade_id": 231234,
 * "order_id": "4303100732",
 * "symbol": "LTC_BTC",
 * "price": "4.00000000",
 * "amount": "1.00000000",
 * "fee": "0.10000000",
 * "fee_currency": "BTC",
 * "side": "buy",
 * "liquidity": "maker",
 * "time": 1499865549590
 * }
 *
 * @author coin58 - 2018/3/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @JsonProperty("trade_id")
    private long tradeId;
    @JsonProperty("order_id")
    private String orderId;
    private String symbol;
    private String price;
    private String amount;
    private String fee;
    @JsonProperty("fee_currency")
    private String feeCurrency;
    private String side;
    private String liquidity;
    private long time;
}
