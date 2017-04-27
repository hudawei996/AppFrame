package com.vanke.zhuze.data.api;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * User: PAPA
 * Date: 2017-04-01
 */

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
//        //取出本地的refreshToken
//        String refreshToken = "sssgr122222222";
//
//        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
//        ApiService service = ServiceManager.getService(ApiService.class);
//        Call<String> call = service.refreshToken(refreshToken);
//
//        //要用retrofit的同步方式
//        String newToken = call.execute().body();
//
//        return response.request().newBuilder()
//                .header("Authorization", newToken)
//                .build();

        return null;
    }
}
