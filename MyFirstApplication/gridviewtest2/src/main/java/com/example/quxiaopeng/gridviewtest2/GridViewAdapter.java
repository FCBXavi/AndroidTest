package com.example.quxiaopeng.gridviewtest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 15/10/17.
 */
public class GridViewAdapter extends BaseAdapter {

    List<FruitModel> fruitModels;
    LayoutInflater inflater;

    public GridViewAdapter(Context context, List<FruitModel> fruitModels) {
        this.fruitModels = fruitModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fruitModels.size();
    }

    @Override
    public Object getItem(int position) {
        return fruitModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_item, null);
            viewHolder.fruitImage = (ImageView) convertView.findViewById(R.id.iv_fruit);
            viewHolder.tint = (FrameLayout) convertView.findViewById(R.id.iv_tint);
            viewHolder.fruitName = (TextView) convertView.findViewById(R.id.tv_fruitname);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_selectFruit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FruitModel fruitModel = fruitModels.get(position);
        viewHolder.fruitName.setText(fruitModel.name);
        viewHolder.fruitImage.setImageResource(fruitModel.picId);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.tint.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tint.setVisibility(View.GONE);
                }
            }
        });


        return convertView;
    }

    class ViewHolder {
        public TextView fruitName;
        public ImageView fruitImage;
        public FrameLayout tint;
        public CheckBox checkBox;
    }
}
