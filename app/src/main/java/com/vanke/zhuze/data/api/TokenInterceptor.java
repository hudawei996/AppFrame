package com.vanke.zhuze.data.api;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求TOKEN拦截器，为所有请求植入TOKEN
 * User: PAPA
 * Date: 2017-04-01
 */

public class TokenInterceptor implements Interceptor {

    private IGetTokenAction mTokenAction;

    public TokenInterceptor(IGetTokenAction action){
        mTokenAction = action;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = null;
        if(mTokenAction!=null)
            token = mTokenAction.getToken();
        if ( TextUtils.isEmpty(token)) {//表示第一次登陆还没拉取过token
            return chain.proceed(originalRequest);//执行登陆的操作
        }
        //此处表示已有token 这时只需要判断401即可
        Request authorised = originalRequest.newBuilder()
                .header("Authorization",  token)//此处的token 是你保存在本地的
                .build();
        Response response = chain.proceed(authorised);//执行此次请求
//        if (response.code() == 401) {//返回为token失效
//            refreshToken();//重新获取token，此处的刷新token需要同步执行以防止异步到来的问题
//            Request newRequest = originalRequest.newBuilder()
//                    .header("Authorization", token)
//                    .build();//
//            return chain.proceed(newRequest);
//        }
        return response;
    }


   public interface IGetTokenAction {
        String getToken();
    }

}
