package com.example.quxiaopeng.maptest;

import java.io.Serializable;

/**
 * Created by quxiaopeng on 16/8/17.
 */

public class PoiModel implements Serializable {
    public int mapVendor = -1;      //3家地图的经纬度都是不一样的
    public double latitude = -1;
    public double longitude = -1;
    public String addressTitle;
    public String addressDetail;
    public String cityCode;
    public float accuracy;
    //省县市区
    public String province;
    public String city;
    public String district;
}
