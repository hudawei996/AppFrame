package com.vanke.zhuze;

import android.app.Application;

import com.vanke.libvanke.LibApplication;
import com.vanke.libvanke.data.HttpManager;
import com.vanke.zhuze.data.api.TokenInterceptor;

/**
 * User: PAPA
 * Date: 2017-03-31
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LibApplication.init(this, BuildConfig.DEBUG);
        initTokenInterceptor();
    }

    private void initTokenInterceptor(){
        TokenInterceptor interceptor = new TokenInterceptor(new TokenInterceptor.IGetTokenAction() {
            @Override
            public String getToken() {
                //临时测试
                return "Bearer ctzQjx7uRcPfrIb8a9Iu3wujVjtlOs";
            }
        });

        HttpManager.get().getOkHttpClientBuild().addInterceptor(interceptor);
    }

}
