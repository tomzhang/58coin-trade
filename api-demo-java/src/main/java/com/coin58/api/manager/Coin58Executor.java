package com.coin58.api.manager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;

/**
 * @author coin58 - 2018/3/24.
 */
@Component
public class Coin58Executor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Executor executor;

    public Coin58Executor() {
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SSLContext sslContext = SSLContexts.createSystemDefault();
        ConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
            NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", socketFactory)
            .build();
        HttpClientConnectionManager mgr = new PoolingHttpClientConnectionManager(r);
        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(mgr)
            .build();
        executor = Executor.newInstance(httpClient);
    }

    public synchronized Response execute(Request request) throws IOException {
        return executor.execute(request);
    }
}
