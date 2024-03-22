package com.aex.platform.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.java.Log;

@Log
public class FeignExceptionHandler implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        log.severe(String.valueOf(response.status()));
        log.severe(String.valueOf(response.body()));
        log.severe("response.reason: " + response.reason());
        return new Exception(response.reason());
    }
}
