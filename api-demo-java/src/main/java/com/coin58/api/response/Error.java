package com.coin58.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author coin58 - 2018/3/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Error {
    private int code;
    private String message;
}
