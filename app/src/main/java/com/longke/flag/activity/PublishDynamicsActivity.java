package com.longke.flag.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.PhotoAdapter;
import com.longke.flag.entity.UploadModel;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;

import static com.longke.flag.http.Urls.GetSignData;


public class PublishDynamicsActivity extends AppCompatActivity {
    @InjectView(R.id.flag_content)
    EditText mFlagContent;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.btnIsChoosed)
    CheckBox mBtnIsChoosed;
    @InjectView(R.id.radio_layout)
    LinearLayout mRadioLayout;
    @InjectView(R.id.dynamic_layout)
    LinearLayout mDynamicLayout;
    @InjectView(R.id.back_img_btn)
    ImageView mBackImgBtn;
    @InjectView(R.id.guanlian_haoyou_tv)
    TextView mGuanlianHaoyouTv;

    ProgressDialog dialog;
    private ArrayList<String> mPhotoList;
    private ArrayList<String> selectPhotoList;
    private LayoutInflater mInflater;
    private ArrayList<String> paths;
    private ImageView back_img_btn;
    public final static int REQUEST_CODE = 1;
    public final static int REQUEST_ADD_CODE = 2;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    private File PHOTO_DIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamics);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        mPhotoList = new ArrayList<String>();
        paths = new ArrayList<String>();
        selectPhotoList = new ArrayList<String>();
    }

    private void initView() {
        selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            if (requestCode == REQUEST_ADD_CODE) {
                if (selectedPhotos.contains(String.valueOf(R.drawable.cam_photo))) {
                    selectedPhotos.remove(String.valueOf(R.drawable.cam_photo));
                }
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                if (selectedPhotos.size() < 9) {
                    selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
                }


            } else {
                selectedPhotos.clear();
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                if (selectedPhotos.size() < 9) {
                    selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
                }
            }
            photoAdapter.notifyDataSetChanged();

        }
    }

    @OnClick(R.id.dynamic_layout)
    public void onClick() {
        mBtnIsChoosed.setChecked(!mBtnIsChoosed.isSelected());
        //根据选中状态显示下面滑动的View
        if (mBtnIsChoosed.isSelected()) {

        }
    }

    @OnClick({R.id.back_img_btn, R.id.guanlian_haoyou_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img_btn:
                finish();
                break;
            case R.id.guanlian_haoyou_tv:
                startActivity(new Intent(PublishDynamicsActivity.this, FriendBatchActivity.class));
                break;
        }
    }

    @OnClick(R.id.send_msg_btn)
    public void onViewClicked() {
        HttpUtil.getInstance().GetTimestamp("SubmitPublishFlagCircle");
    }
    public File compressImage(String path) {
        try {
            File saveFile = new File(getExternalCacheDir(), "compress_" + System.currentTimeMillis() + ".jpg");
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            Log.e("===compressImage===", "====开始==压缩==saveFile==" + saveFile.getAbsolutePath());

            NativeUtil.compressBitmap(bitmap, saveFile.getAbsolutePath());
            Log.e("===compressImage===", "====完成==压缩==saveFile==" + saveFile.getAbsolutePath());
            return saveFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("SubmitPublishFlagCircle".equals(messageEvent.getTag())) {
            dialog = new ProgressDialog(PublishDynamicsActivity.this);
            dialog.setMessage("登陆中,请稍候...");

            dialog.show();
            GetSignData(messageEvent.getMessage())  ;
        }

    }
    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     *
     * @param timestamp
     */
    public void GetSignData(final String timestamp) {
        final String UserCode = (String) SharedPreferencesUtil.get(PublishDynamicsActivity.this,SharedPreferencesUtil.UserCode, "");
        String UserSecret = (String) SharedPreferencesUtil.get(PublishDynamicsActivity.this, SharedPreferencesUtil.UserSecret, "");
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetSignData)
                .addParam("Timestamp", timestamp)
                .addParam("appKey", UserCode)
                .addParam("appSecret", UserSecret)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if (response.getBoolean("Success")) {
                                String Message = response.getString("Message");
                                if(selectedPhotos.size()>1){
                                    upLoadFile(UserCode, timestamp, Message)  ;
                                }else{
                                    SubmitPublishFlagCircle(UserCode, timestamp, Message,"");
                                }

                            } else {
                                ToastUtil.showShort(PublishDynamicsActivity.this, response.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });

    }

    public void SubmitPublishFlagCircle(String appKey, String timestamp, String sign,String path) {

        HttpUtil.getInstance().getOkHttp().post().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.SubmitPublishFlagCircle)
                .addParam("timestamp", timestamp)
                .addParam("appKey", appKey)
                .addParam("sign", sign)
                .addParam("Content", mFlagContent.getText().toString())
                .addParam("FlagType", "2")   //类型，1:Flag;2:动态
                .addParam("ImgPaths",path)   //图片路径，多张图片;隔开
                .addParam("RefFriendIds",path)   //@好友Id,多个;隔开
                .addParam("RefFlagId",path)   //关联FlagId
                .addParam("IsForward",true+"")   //是否转发：true-转发；false-非转发
                .addParam("OriginalId",true+"")   //转发原始FlagId(若为转发,则该字段必传)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        dialog.dismiss();
                        try {
                            if (response.getBoolean("Success")) {
                                ToastUtil.showShort(PublishDynamicsActivity.this,"发布成功");
                                finish();
                            } else {
                                ToastUtil.showShort(PublishDynamicsActivity.this, response.getString("Message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        dialog.dismiss();
                    }
                });

    }
    public void upLoadFile(final String appKey, final String timestamp, final String sign) {
        Map<String, File> files=new HashMap<>();
        for(int i=0;i<selectedPhotos.size()-1;i++){
            if (!selectedPhotos.get(i).equals(String.valueOf(R.drawable.cam_photo))) {
                files.put("avatar"+i,compressImage(selectedPhotos.get(i)));
            }
        }
        HttpUtil.getInstance().getOkHttp().upload()
                .url(Urls.UploadFile)
                .files(files)        //上传已经存在的File
//                .addFile("avatar2", "asdsda.png", byteContents)    //直接上传File bytes
                .tag(this)
                .enqueue(new GsonResponseHandler<UploadModel>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        // Log.d(TAG, "doUpload onFailure:" + error_msg);
                        ToastUtil.showShort(PublishDynamicsActivity.this,"上传文件失败，请重试！");
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        // Log.d(TAG, "doUpload onProgress:" + currentBytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(int statusCode, UploadModel response) {
                        SubmitPublishFlagCircle(appKey, timestamp, sign,response.getMessage());
                        Log.d("longke", "doUpload onSuccess:" +response.getMessage());
                    }
                });


    }
}
