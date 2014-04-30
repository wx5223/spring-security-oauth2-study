package com.wx.service;

import org.springframework.web.client.RestOperations;

/**
 * Created by Shawn on 2014/4/30.
 */
public class RestService {
    private RestOperations restTemplate;

    public RestOperations getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }
}
