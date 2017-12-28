package com.longke.flag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longke.flag.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by longke on 2017/12/16.
 */

public class MyFlagAdapter extends RecyclerView.Adapter<MyFlagAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flag_second, parent, false);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFlagAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(position==0)
            holder.topView.setVisibility(View.VISIBLE);
        else
            holder.topView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View topView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
            topView=itemView.findViewById(R.id.layout_top);

        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
