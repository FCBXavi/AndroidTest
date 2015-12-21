package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quxiaopeng.swiperefreshlayoutandrecyclerview.R;

import java.util.List;

/**
 * Created by quxiaopeng on 15/12/21.
 */
public class MyAdapter extends BaseAdapter {
    List<String>list;
    Context mContext;
    public MyAdapter(Context context, List<String>list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String item = list.get(position);
        viewHolder.textView.setText(item);
        return convertView;
    }

    private class ViewHolder {
        public TextView textView;
    }
}
