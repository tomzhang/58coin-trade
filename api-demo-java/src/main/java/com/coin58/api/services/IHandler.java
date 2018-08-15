package com.coin58.api.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

/**
 * @author coin58
 * @date 2017/7/17.
 */
public interface IHandler<T> {
    /**
     * 要发送的request
     * @return request
     */
    Request getRequest();

    /**
     * 处理返回的结果
     * @param response response
     * @return T
     */
    T handleResponse(HttpResponse response);
}
