package com.example.quxiaopeng.mylistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 15/8/11.
 */
public class MyAdapter extends BaseAdapter {

    Context context;
    List<String>list;
    LayoutInflater inflater;

    public MyAdapter() {
        super();
    }

    public MyAdapter(Context context,List<String>list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item,null);
            holder=new ViewHolder();
            holder.textView=(TextView)convertView.findViewById(R.id.tv_text);
            holder.imageButton=(ImageButton)convertView.findViewById(R.id.right);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.textView.setText(list.get(position));

        Drawable notice=context.getResources().getDrawable(R.drawable.notice);
        notice.setBounds(0,0,50,50);
        holder.textView.setCompoundDrawables(notice,null,null,null);
        holder.imageButton.setImageResource(R.drawable.right);

        return convertView;
    }

    class ViewHolder{
        public TextView textView;
        public ImageButton imageButton;
    }


}
