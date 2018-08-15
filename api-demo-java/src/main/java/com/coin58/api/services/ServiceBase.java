package com.coin58.api.services;

import com.coin58.api.enums.Site;

/**
 * @author coin58 - 2018/3/26.
 */
public class ServiceBase {
    protected Site site;

    protected ServiceBase(Site site) {
        this.site = site;
    }

    protected String url(String path) {
        return this.site.getUrl() + path;
    }
}
