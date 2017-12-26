package com.longke.flag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;

import static com.longke.flag.activity.PublishFlagActivity.REQUEST_ADD_CODE;

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
    private ArrayList<String> mPhotoList;
    private ArrayList<String> selectPhotoList;
    private ArrayList<String> paths;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    ArrayList<String> selectedPhotos = new ArrayList<>();

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
                startActivity(new Intent(PublishDynamicsActivity.this,FriendBatchActivity.class));
                break;
        }
    }
}
