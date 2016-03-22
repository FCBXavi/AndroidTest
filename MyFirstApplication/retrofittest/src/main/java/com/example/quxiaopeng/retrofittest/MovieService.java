package com.example.quxiaopeng.retrofittest;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by quxiaopeng on 16/3/22.
 */
public interface MovieService {
//    @GET("top250")
//    Call<MovieModel> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResultModel<List<MovieModel>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
