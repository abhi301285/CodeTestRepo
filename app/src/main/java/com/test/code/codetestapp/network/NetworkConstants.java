package com.test.code.codetestapp.network;

import com.test.code.codetestapp.BuildConfig;

public interface NetworkConstants {

    String WEB_URL = BuildConfig.WEB_URL;

    String WS_RESPONSE_STATUS_SUCCESS = "200 OK";
    String WS_RESPONSE_STATUS_UNAUTHORIZED = "401";

    // Methods
    String WS_GET_ALBUMS = "/albums";

}
