package com.coin58.api.consumer;

import com.coin58.api.response.Account;
import com.coin58.api.response.Coin58Resp;
import com.coin58.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author coin58 - 2018/3/26.
 */
@Component("coin58-accountConsumer")
@Slf4j
public class AccountConsumer {

    public void handleAccounts(Response response) throws IOException {
        String json = response.returnContent().asString();
        Coin58Resp<List<Account>> result = JsonUtils.parseJson(json, new TypeReference<Coin58Resp<List<Account>>>() {
        });
        log.info("Account result [{}]", result);
    }
}
