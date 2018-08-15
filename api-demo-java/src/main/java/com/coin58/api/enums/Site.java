package com.coin58.api.enums;

import lombok.Data;

/**
 * @author coin58 - 2018/3/24.
 */
@Data
public class Site {
    private String name;
    private String host;
    private String protocol;
    private String url;

    public Site(String name, String host, String protocol) {
        this.name = name;
        this.host = host;
        this.protocol = protocol;
        this.url = protocol.concat("://").concat(host).concat("/");
    }
}
