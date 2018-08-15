package com.coin58.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author coin58 - 2018/3/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin58Resp<T> {
    private T result;
    private Error error;
}
