package com.longke.flag.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.longke.flag.R;
import com.longke.flag.activity.PublishFlagActivity;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

  private ArrayList<String> photoPaths = new ArrayList<String>();
  private LayoutInflater inflater;

  private Context mContext;


  public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
    this.photoPaths = photoPaths;
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);

  }


  @Override
  public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.photo_adapter_item, parent, false);
    return new PhotoViewHolder(itemView);
  }


  @Override
  public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
    if(photoPaths.get(position).equals(String.valueOf(R.drawable.cam_photo))){
        holder.ivPhoto.setImageResource(R.drawable.cam_photo);
        holder.delete_iv.setVisibility(View.GONE);
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(mContext, PhotoPickerActivity.class);
            PhotoPickerIntent.setPhotoCount(intent, 9-photoPaths.size());
            PhotoPickerIntent.setColumn(intent, 3);
           ((Activity)mContext).startActivityForResult(intent, PublishFlagActivity.REQUEST_ADD_CODE);
          }
        });
    }else{
      Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
      holder.delete_iv.setVisibility(View.VISIBLE);
      holder.delete_iv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          photoPaths.remove(position);
          if (photoPaths.size()==19&&!photoPaths.contains(String.valueOf(R.drawable.cam_photo))) {
            photoPaths.add(String.valueOf(R.drawable.cam_photo));
          }
          notifyDataSetChanged();
        }
      });
      //GlideUtil.glideLoader(mContext,uri,me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp,me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp,holder.ivPhoto,1);
      Glide.with(mContext)
              .load(uri)
              .centerCrop()
              .thumbnail(0.1f)
              .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
              .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
              .into(holder.ivPhoto);

      holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(mContext, PhotoPagerActivity.class);
          intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
          ArrayList<String> paths=new ArrayList<String>();
          if(photoPaths.contains(String.valueOf(R.drawable.cam_photo))){

            for(int i=0;i<photoPaths.size()-1;i++){
              paths.add(photoPaths.get(i));
            }
          }else{
            for(int i=0;i<photoPaths.size();i++){
              paths.add(photoPaths.get(i));
            }
          }

          intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS,paths );
          intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, true);
          if (mContext instanceof PublishFlagActivity) {
            ((PublishFlagActivity) mContext).previewPhoto(intent);
          }
        }
      });
    }


  }


  @Override
  public int getItemCount() {
    return photoPaths.size();
  }


  public static class PhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private ImageView delete_iv;
    public PhotoViewHolder(View itemView) {
      super(itemView);
      ivPhoto   = (ImageView) itemView.findViewById(me.iwf.photopicker.R.id.iv_photo);
      delete_iv = (ImageView) itemView.findViewById(R.id.delete_iv);
    }
  }

}
