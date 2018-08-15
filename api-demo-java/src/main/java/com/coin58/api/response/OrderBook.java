package com.coin58.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * {
 * "bids": [
 * // price, amount
 * [ "0.018", "0.5" ],
 * [ "0.0187", "0.176" ],
 * ...
 * ],
 * "asks": [
 * // price, amount
 * [ "0.019726", "0.017" ],
 * [ "0.019727", "0.349" ],
 * ...
 * ]
 * }
 *
 * @author coin58 - 2018/3/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBook {
    private List<List> bids;
    private List<List> asks;
}
