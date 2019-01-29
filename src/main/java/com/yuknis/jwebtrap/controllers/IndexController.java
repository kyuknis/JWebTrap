package com.yuknis.jwebtrap.controllers;

import java.util.Map;

import com.yuknis.jwebtrap.providers.RequestLoggerServiceProvider;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class IndexController {

    @RequestMapping(value="**")
    public String requestMethodName(@RequestHeader HttpHeaders headers, @RequestBody(required=false) String body) {
        String requestRecord = "";

        for(Map.Entry<String, String> h : headers.toSingleValueMap().entrySet()) {
            String header = String.format("%s: %s\n", h.getKey().toUpperCase(), h.getValue());
            requestRecord += header;
        }
        
        if(body != null) {
            requestRecord += String.format("\n%s", body);
        }

        RequestLoggerServiceProvider.getInstance().saveRecord(requestRecord);

        return "+OK";
    }

}