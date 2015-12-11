package com.example.quxiaopeng.getphoneapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<AppInfo> mListAppInfo;

    //获取PackageManager对象
    //PackageManager packageManager = this.getPackageManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_listView);
        mListAppInfo = new ArrayList<>();
        //qureyAppByIntent();
        queryByApplicationInfo();
        BrowseApplicationAdapter adapter = new BrowseApplicationAdapter(this, mListAppInfo);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = mListAppInfo.get(position).getIntent();
                startActivity(intent);
            }
        });
    }

    private void qureyAppByIntent() {
        PackageManager packageManager = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //通过查询,获得所有ResolveInfo对象
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
        //调用系统排序,根据name排序
        //该排序很重要,否则只能显示系统应用,而不能列出第三方应用
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));
        if (mListAppInfo!=null){
            mListAppInfo.clear();
            for (ResolveInfo resolveInfo:resolveInfos){
                String activityName=resolveInfo.activityInfo.name; //获得该应用程序的启动Activity的name
                String pkgName=resolveInfo.activityInfo.packageName;//获得应用程序的包名
                String appLabel= (String) resolveInfo.loadLabel(packageManager);//获得应用程序的label
                Drawable icon=resolveInfo.loadIcon(packageManager);//获得应用程序的图标

                //为应用程序的启动Activity 准备Intent
                Intent intent=new Intent();
                intent.setComponent(new ComponentName(pkgName,activityName));

                //创建一个AppInfo对象,并赋值
                AppInfo appInfo=new AppInfo();
                appInfo.setAppIcon(icon);
                appInfo.setAppLabel(appLabel);
                appInfo.setPkgName(pkgName);
                appInfo.setIntent(intent);
                mListAppInfo.add(appInfo);
                Log.i("MainActivity", "appLabel:" + appLabel + " activityName:" + activityName + " pkgName:" + pkgName);
            }
        }

    }
    private void queryByApplicationInfo(){
        PackageManager packageManager = this.getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));
        mListAppInfo.clear();
        for (ApplicationInfo applicationInfo : applicationInfos) {
            //所有应用程序
            AppInfo appInfo1=new AppInfo();
            appInfo1.setAppLabel((String) applicationInfo.loadLabel(packageManager));
            appInfo1.setPkgName(applicationInfo.packageName);
            appInfo1.setAppIcon(applicationInfo.loadIcon(packageManager));
            mListAppInfo.add(appInfo1);

            //系统程序
            if ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0){
                AppInfo appInfo2=new AppInfo();
                appInfo2.setAppLabel((String) applicationInfo.loadLabel(packageManager));
                appInfo2.setPkgName(applicationInfo.packageName);
                appInfo2.setAppIcon(applicationInfo.loadIcon(packageManager));
                mListAppInfo.add(appInfo2);
            }
        }
    }

}
