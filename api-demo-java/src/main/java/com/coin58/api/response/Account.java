package com.coin58.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 * "currency": "BTC",
 * "balance": "0.00000000",
 * "available": "0.00000000",
 * }
 *
 * @author coin58 - 2018/3/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String currency;
    private String balance;
    private String available;
}
