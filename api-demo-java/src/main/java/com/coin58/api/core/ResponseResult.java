package com.coin58.api.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author coin58
 * @param <T>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 6081751343710030533L;
    /**
     * 0表示成功，>0表示失败,<0系统保留
     */
    private int code = 0;

    /**
     * 提示信息
     */
    private String msg = "";

    /**
     * 详细提示信息
     */
    private String detailMsg = "";

    /**
     * 返回数据
     */
    private T data;

    public static <T> ResponseResult success(final T data) {
        return build(0, "", "", data);
    }

    public static ResponseResult failure(final int code, final String msg) {
        return failure(code, msg, "");
    }

    public static ResponseResult failure(final String msg) {
        return failure(-1, msg, "");
    }

    public static ResponseResult failure(final String msg, final String detailMsg) {
        return failure(-1, msg, detailMsg);
    }

    public static ResponseResult failure(final int code, final String msg, final String detailMsg) {
        return build(code, msg, detailMsg, null);
    }

    public static <T> ResponseResult build(final int code, final String msg, final String detailMsg, final T data) {
        return new ResponseResult<>(code, msg, detailMsg, data);
    }
}
