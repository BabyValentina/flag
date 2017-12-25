package com.longke.flag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longke.flag.R;

import butterknife.ButterKnife;

/**
 * Created by longke on 2017/12/24.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> {
    @Override
    public MyCollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_item_layout, parent, false);
        /*view.findViewById(R.id.viewer_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, 0);
                }
            }
        });*/
        return new MyCollectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCollectionAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);


        }
    }

    private MyCollectionAdapter.OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(MyCollectionAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
