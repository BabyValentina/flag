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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.PhotoAdapter;
import com.longke.flag.entity.UploadModel;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
import com.longke.flag.view.CustomDatePicker;
import com.longke.flag.view.FlowTagLayout;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;

import static com.longke.flag.http.Urls.GetSignData;


/**
 * 创建病历档案
 */
public class PublishFlagActivity extends AppCompatActivity {
    @InjectView(R.id.back_img_btn)
    ImageView mBackImgBtn;
    @InjectView(R.id.current_section)
    TextView mCurrentSection;
    @InjectView(R.id.send_msg_btn)
    TextView mSendMsgBtn;
    @InjectView(R.id.cra_title)
    RelativeLayout mCraTitle;
    @InjectView(R.id.flag_title)
    EditText mFlagTitle;
    @InjectView(R.id.flag_content)
    EditText mFlagContent;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.radioMale)
    RadioButton mRadioMale;
    @InjectView(R.id.radioFemale)
    RadioButton mRadioFemale;
    @InjectView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @InjectView(R.id.tixing)
    TextView mTixing;
    @InjectView(R.id.alert_time_tv)
    TextView mAlertTimeTv;
    @InjectView(R.id.dacheng_text)
    TextView mDachengText;
    @InjectView(R.id.dacheng_title)
    TextView dachengTitle;
    @InjectView(R.id.dacheng_layout)
    RelativeLayout dachengLayout;
    @InjectView(R.id.tixing_layout)
    RelativeLayout tixingLayout;
    @InjectView(R.id.hostory_tag_layout)
    FlowTagLayout mHostoryTagLayout;
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
    private int diseaseType = 1;
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_case_activity);
        ButterKnife.inject(this);
        initDatePicker();
        initData();
        initView();
        EventBus.getDefault().register(this);
        mHostoryTagLayout.setTags(new String[]{"三天", "一个月"});
        //mHostoryTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            mAdapter.refresh(images);
        }
        //ImageSelectorUtils.openPhoto(PublishFlagActivity.this, REQUEST_CODE, false, 9);
    }
*/


    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initEvents() {


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


    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());


        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mDachengText.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 显示时和分
        customDatePicker1.setIsLoop(true); // 允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mAlertTimeTv.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    @OnClick({R.id.dacheng_layout, R.id.tixing_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dacheng_layout:
                customDatePicker1.show("2016-06-02");
                break;
            case R.id.tixing_layout:
                customDatePicker2.show("2016-06-02");
                break;
        }

    }

    @OnClick(R.id.send_msg_btn)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mFlagTitle.getText().toString())) {
            ToastUtil.showShort(PublishFlagActivity.this, "请输入标题");
            return;
        }
        if (TextUtils.isEmpty(mFlagContent.getText().toString())) {
            ToastUtil.showShort(PublishFlagActivity.this, "请输入Flag内容");
            return;
        }

        HttpUtil.getInstance().GetTimestamp("SubmitPublishFlagCircle");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("SubmitPublishFlagCircle".equals(messageEvent.getTag())) {
            dialog = new ProgressDialog(PublishFlagActivity.this);
            dialog.setMessage("登陆中,请稍候...");

            dialog.show();
            GetSignData(messageEvent.getMessage());
        }

    }

    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     *
     * @param timestamp
     */
    public void GetSignData(final String timestamp) {
        final String UserCode = (String) SharedPreferencesUtil.get(PublishFlagActivity.this, SharedPreferencesUtil.UserCode, "");
        String UserSecret = (String) SharedPreferencesUtil.get(PublishFlagActivity.this, SharedPreferencesUtil.UserSecret, "");
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(GetSignData)
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
                                if (selectedPhotos.size() > 1) {
                                    upLoadFile(UserCode, timestamp, Message);
                                } else {
                                    SubmitPublishFlagCircle(UserCode, timestamp, Message, "");
                                }

                            } else {
                                ToastUtil.showShort(PublishFlagActivity.this, response.getString("Message"));
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

    public void SubmitPublishFlagCircle(String appKey, String timestamp, String sign, String path) {

        HttpUtil.getInstance().getOkHttp().post().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.SubmitPublishFlagCircle)
                .addParam("timestamp", timestamp)
                .addParam("appKey", appKey)
                .addParam("sign", sign)
                .addParam("Title", mFlagTitle.getText().toString())
                .addParam("Content", mFlagContent.getText().toString())
                .addParam("FlagType", "1")
                .addParam("FinishedTagType", "1")
                .addParam("FinishedTagName", "3天")
                .addParam("PunishType", "1") //红包
                .addParam("Money", "10")    //惩罚红包金额
                .addParam("IsNotice", "true")   //是否提醒
                .addParam("NoticeTime", mAlertTimeTv.getText().toString())   //提醒时间
                .addParam("AttentionType", "1")   //关注类型，1-公开；2-仅好友；3-仅自己
                .addParam("ImgPaths", path)   //图片路径，多张图片;隔开
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        dialog.dismiss();
                        try {
                            if (response.getBoolean("Success")) {
                                ToastUtil.showShort(PublishFlagActivity.this, "发布成功");
                                finish();
                            } else {
                                ToastUtil.showShort(PublishFlagActivity.this, response.getString("Message"));
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
        Map<String, File> files = new HashMap<>();
        for (int i = 0; i < selectedPhotos.size() - 1; i++) {
            if (!selectedPhotos.get(i).equals(String.valueOf(R.drawable.cam_photo))) {
                files.put("avatar" + i, compressImage(selectedPhotos.get(i)));
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
                        ToastUtil.showShort(PublishFlagActivity.this, "上传文件失败，请重试！");
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        // Log.d(TAG, "doUpload onProgress:" + currentBytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(int statusCode, UploadModel response) {
                        SubmitPublishFlagCircle(appKey, timestamp, sign, response.getMessage());
                        Log.d("longke", "doUpload onSuccess:" + response.getMessage());
                    }
                });


    }

    @OnClick(R.id.back_img_btn)
    public void onClick() {
        finish();
    }
}
