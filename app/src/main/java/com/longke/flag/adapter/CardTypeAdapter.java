package com.longke.flag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longke.flag.R;

/**
 * Created by longke on 2017/12/29.
 */

public class CardTypeAdapter extends BaseAdapter {
    private Context mContext;
    public CardTypeAdapter(Context mContext){
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
        return 5;
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_type_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }    else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    static class ViewHolder{

    }
}
