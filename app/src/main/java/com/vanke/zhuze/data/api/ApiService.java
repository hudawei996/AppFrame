package com.vanke.zhuze.data.api;

import com.vanke.libvanke.net.HttpResult;
import com.vanke.zhuze.model.response.CommunityPostsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * User: PAPA
 * Date: 2017-04-01
 */

public interface ApiService {

    String baseUrl = "http://test.4009515151.com/";

    @GET("api/zhuzher/users/me/project/posts/recommend")
    Observable<HttpResult<CommunityPostsResponse>> getPost(@Query("start") int page, @Query("count") int per_page);


}
