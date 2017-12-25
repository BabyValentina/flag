package com.longke.flag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longke.flag.R;

import butterknife.ButterKnife;

/**
 * Created by Jackie
 * on 2017/12/24
 * email jishengji@xianyulc.com
 */

public class FlagAdapter extends RecyclerView.Adapter<FlagAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_weiboitem_original_pictext, parent, false);
        /*view.findViewById(R.id.viewer_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, 0);
                }
            }
        });*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);


        }
    }

    private FollowAdapter.OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(FollowAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
