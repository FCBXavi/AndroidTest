package com.example.quxiaopeng.retrofittest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by quxiaopeng on 16/3/3.
 */
public interface StockService {
    @Headers("apix-key:54acc370057645d4722f78542800c0fe")
    @GET("apixmoney/stockdata/stock?stockid=sz002230")
    Call<String> setRequest();
}
