package com.example.quxiaopeng.ottotest;

import com.squareup.otto.Bus;

/**
 * Created by quxiaopeng on 15/9/28.
 */
public class AppConfig {
    private static final Bus BUS=new Bus();
     public static Bus getBusInstance(){
         return BUS;
     }
}
