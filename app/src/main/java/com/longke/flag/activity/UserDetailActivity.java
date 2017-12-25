package com.longke.flag.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
import com.longke.flag.util.widget.ActionSheetDialog;
import com.squareup.picasso.Picasso;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * author：wanjianhua on 2017/12/23 10:42
 * email：1243381493@qq.com
 */

public class UserDetailActivity extends AppCompatActivity {
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.follow_tv)
    TextView followTv;
    @InjectView(R.id.title_bar)
    RelativeLayout titleBar;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.tv_touxiang)
    TextView tvTouxiang;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.rel_touxiang)
    RelativeLayout relTouxiang;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.image_1)
    ImageView image1;
    @InjectView(R.id.rel_username)
    RelativeLayout relUsername;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.image_3)
    ImageView image3;
    @InjectView(R.id.rel_sex)
    RelativeLayout relSex;
    @InjectView(R.id.tv_sign)
    TextView tvSign;
    @InjectView(R.id.image_2)
    ImageView image2;
    @InjectView(R.id.rel_sign)
    RelativeLayout relSign;
    @InjectView(R.id.tv_sgin)
    TextView tvSgin;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_sexover)
    TextView tvSexover;
    @InjectView(R.id.head)
    CircleImageView head;
    /**
     * 拍照临时保存文件
     */
    private File tempFile;
    int REQUEST_CODE_PICK_IMAGE = 100;
    int REQUEST_THUMBNAIL = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_view);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.rel_touxiang, R.id.rel_username, R.id.rel_sex, R.id.rel_sign, R.id.iv_add})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rel_touxiang:
                new ActionSheetDialog(UserDetailActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍一张", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 启动相机
                                startActivityForResult(intent1, REQUEST_THUMBNAIL);
                            }
                        }).addSheetItem("从相册中选", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");//相片类型
                        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                    }
                }).show();
                break;
            case R.id.rel_username:
                intent.setClass(UserDetailActivity.this, UserNameActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.rel_sex:
                intent.setClass(UserDetailActivity.this, SexActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rel_sign:
                intent.setClass(UserDetailActivity.this, SignActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_add:
                HttpUtil.getInstance().GetTimestamp("ModifyUser");

                break;
            default:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("ModifyUser".equals(messageEvent.getTag())) {
            GetSignData(messageEvent.getMessage());
        }
    }
    public void upLoadFile(File file){
        HttpUtil.getInstance().getOkHttp().upload()
                .url(Urls.UserCenterIndex)
                .addParam("name", "tsy")
                .addFile("avatar", file)        //上传已经存在的File
//                .addFile("avatar2", "asdsda.png", byteContents)    //直接上传File bytes
                .tag(this)
                .enqueue(new GsonResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Object response) {

                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                    }

                });


    }

    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     * @param timestamp
     */
    public void GetSignData(final String timestamp){
        final String UserCode= (String) SharedPreferencesUtil.get(UserDetailActivity.this, SharedPreferencesUtil.UserCode,"");
        String UserSecret= (String) SharedPreferencesUtil.get(UserDetailActivity.this,SharedPreferencesUtil.UserSecret,"");
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetSignData)
                .addParam("Timestamp", timestamp)
                .addParam("appKey",UserCode)
                .addParam("appSecret",UserSecret)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if(response.getBoolean("Success")){
                                String Message=response.getString("Message");
                                ModifyUser(UserCode,timestamp,Message);
                            }else{
                                ToastUtil.showShort(UserDetailActivity.this,response.getString("Message"));
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
    public void ModifyUser(String appKey,String timestamp,String sign){
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                 addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.ModifyUser)
                .addParam("timestamp", timestamp)
                .addParam("appKey",appKey)
                .addParam("sign",sign)
                .addParam("UserName",tvName.getText().toString())
                .addParam("PhotoUrl",true+"")
                .addParam("Comments",tvSgin.getText().toString())
                .addParam("Sex",tvSexover.getText().toString().equals("男")?"true":"false")
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if(response.getBoolean("Success")){
                                String Message=response.getString("Message");
                            }else{
                                ToastUtil.showShort(UserDetailActivity.this,response.getString("Message"));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("sign")) {
                tvSgin.setText(data.getStringExtra("sign"));
            } else if (data.hasExtra("username")) {
                tvName.setText(data.getStringExtra("username"));
            } else if (data.hasExtra("sex")) {
                tvSexover.setText(data.getStringExtra("sex"));
            }
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                Uri uri = data.getData();
                try {
                    File file = new File(new URI(uri.toString()));
                    upLoadFile(file);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                //to do find the path of pic
                Picasso.with(UserDetailActivity.this).load(uri).into(head);
            }
            if (requestCode == REQUEST_THUMBNAIL) {//对应第一种方法
                /**
                 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
                 */
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                upLoadFile(saveBitmapFile(bitmap));
                head.setImageBitmap(bitmap);
            }
        }
        }
    public File saveBitmapFile(Bitmap bitmap){
        File file=new File("/mnt/sdcard/pic/01.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            return null;
        }

        return file;

    }
