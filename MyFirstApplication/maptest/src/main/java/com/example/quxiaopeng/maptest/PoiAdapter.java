package com.example.quxiaopeng.maptest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by quxiaopeng on 16/8/17.
 */

public class PoiAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PoiModel> mList;

    public PoiAdapter(Context context, List<PoiModel> list) {
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_poi, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PoiModel model = mList.get(position);

        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.mTxtTitle.setText(model.addressTitle);
        myHolder.mTxtAddress.setText(model.addressDetail);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtTitle;
        TextView mTxtAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTxtTitle = (TextView) itemView.findViewById(R.id.text_location_title);
            mTxtAddress = (TextView) itemView.findViewById(R.id.text_location_address);
        }
    }
}
