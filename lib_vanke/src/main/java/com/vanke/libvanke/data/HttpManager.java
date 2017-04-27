package com.vanke.libvanke.data;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.vanke.libvanke.net.ResponseConvertFactory;
import com.vanke.libvanke.util.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * User: PAPA
 * Date: 2017-03-30
 */

public class HttpManager {
    private static final int DEFAULT_TIMEOUT = 20;

    private OkHttpClient.Builder mBuilder;
    private HashMap<String, Object> mServiceMap;

    // Make this class a thread safe singleton
    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    public static synchronized HttpManager get() {
        return SingletonHolder.INSTANCE;
    }

    private HttpManager() {
        //initOkhttp(null);
        mServiceMap = new HashMap<>();
    }

    public static void init(Context context, boolean isDebug) {
        get().initOkhttp(context, isDebug);
    }

    private void initOkhttp(Context context, boolean isDebug) {
        //custom OkHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //time our
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //cache
        if (context != null) {
            File httpCacheDirectory = new File(context.getCacheDir(), "OkHttpCache");
            httpClient.cache(new Cache(httpCacheDirectory, 50 * 1024 * 1024));
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor
                .Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        if (isDebug)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(logging);
        if(isDebug){
            httpClient.addNetworkInterceptor(new StethoInterceptor());
        }

        //Interceptor
        //httpClient.addNetworkInterceptor(new LogInterceptor());
        //httpClient.addInterceptor(new CacheControlInterceptor());
        //retry when fail
        httpClient.retryOnConnectionFailure(true);

        //todo SSL证书
        httpClient.sslSocketFactory(TrustManager.getUnsafeOkHttpClient());
        httpClient.hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory
                .ALLOW_ALL_HOSTNAME_VERIFIER);

        mBuilder = httpClient;
    }

    public OkHttpClient.Builder getOkHttpClientBuild(){
        return mBuilder;
    }


    private <S> S createApi(Class<S> serviceClass) {
        return createApi(serviceClass, mBuilder.build());
    }

    private <S> S createApi(Class<S> serviceClass, OkHttpClient client) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("baseUrl");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(baseUrl)) {

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createApi(serviceClass);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createApi(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }


//    private class CacheControlInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (!AppUtils.isNetworkConnected(mContext)) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//            }
//
//            Response response = chain.proceed(request);
//
//            if (AppUtils.isNetworkConnected(mContext)) {
//                int maxAge = 60 * 60; // read from cache for 1 minute
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//            return response;
//        }
//    }
}
