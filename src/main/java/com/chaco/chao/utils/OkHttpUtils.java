package com.chaco.chao.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * author:zhaopeiyan001
 * Date:2019-09-02 14:01
 */

public @Slf4j class OkHttpUtils {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();



    public static Response execute(String url, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (body == null) {
            builder.get();
        } else {
            builder.post(body);
        }
        Request request = builder.build();
//        log.debug("url:"+url);
        return OK_HTTP_CLIENT.newCall(request).execute();
    }
}
