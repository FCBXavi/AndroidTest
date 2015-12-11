package com.example.quxiaopeng.getphoneapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by quxiaopeng on 15/10/9.
 */
public class AppInfo {
    private String appLabel;   //应用程序标签
    private Drawable appIcon;  //应用程序图像
    private Intent intent;     //启动应用程序的Intent,一般是Action和Category炜Lancher的Activity
    private String pkgName;    //应用程序对应的包名

    public AppInfo(){}

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public Intent getIntent() {
        return intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
