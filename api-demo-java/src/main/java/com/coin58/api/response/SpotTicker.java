package com.coin58.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 * "symbol": "LTC_BTC",
 * // The timestamp at which this information was valid
 * "time": 1512744759000,
 * // Current best bid price
 * "bid": "0.019983",
 * // Current best ask price
 * "ask": "0.019984",
 * // The price at which the last trade executed
 * "last": "0.019984",
 * "change": "4.02",
 * // Open price of the last 24 hours
 * "open": "0.019223",
 * // Highest price of the last 24 hours
 * "high": "0.020268",
 * // Lowest price of the last 24 hours
 * "low": "0.019107",
 * // of the last 24 hours
 * "volume": "1425.391",
 * // of the last 24 hours
 * "quote_volume": "28.046991116"
 * }
 *
 * @author coin58 - 2018/3/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotTicker {
    private String symbol;
    private long time;
    private String bid;
    private String ask;
    private String last;
    private String change;
    private String open;
    private String high;
    private String low;
    private String volume;
    @JsonProperty("quote_volume")
    private String quoteVolume;
}
