package com.lunger.bidirtabledemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lunger.bidirtabledemo.R;


/**
 * Created by Allen on 2015/02/05 15:45
 */
public class LvNameAdapter extends BaseAdapter {
    private Context mContext;

    public LvNameAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_good_name, null);
            holder.tv_good_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_good_name.setText(String.format(mContext.getString(R.string.phone_name), position + 4));
        return convertView;
    }
    class ViewHolder{
        TextView tv_good_name;
    }
}