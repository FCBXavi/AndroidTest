package com.example.quxiaopeng.getphoneapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 15/10/9.
 */
public class BrowseApplicationAdapter extends BaseAdapter {

    private List<AppInfo> mListAppInfo;
    LayoutInflater inflater;

    public BrowseApplicationAdapter (Context context,List<AppInfo> appInfos){
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListAppInfo=appInfos;
    }

    @Override
    public int getCount() {
        return mListAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.browse_app_item,null);
            viewHolder.appIcon= (ImageView) convertView.findViewById(R.id.iv_appimage);
            viewHolder.tvAppLabel= (TextView) convertView.findViewById(R.id.tv_appLabel);
            viewHolder.tvAppName= (TextView) convertView.findViewById(R.id.tv_appName);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        AppInfo appInfo=mListAppInfo.get(position);
        viewHolder.appIcon.setImageDrawable(appInfo.getAppIcon());
        viewHolder.tvAppLabel.setText(appInfo.getAppLabel());
        viewHolder.tvAppName.setText(appInfo.getPkgName());
        return convertView;
    }

    class ViewHolder{
        public ImageView appIcon;
        public TextView tvAppLabel;
        public TextView tvAppName;
    }
}
