package com.example.quxiaopeng.ottotest;

/**
 * Created by quxiaopeng on 15/9/28.
 */
public class Download{

    public Download(){
      //  AppConfig.getBusInstance().register(this);
        AppConfig.getBusInstance().post(new PostEvent());
    }


}
