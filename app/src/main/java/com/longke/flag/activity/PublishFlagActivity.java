package com.longke.flag.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.PhotoAdapter;
import com.longke.flag.view.CustomDatePicker;

import net.bither.util.NativeUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_case_activity);
        ButterKnife.inject(this);
        initDatePicker();
        initData();
        initView();


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
}
