package com.longke.flag.activity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.entity.UploadModel;
import com.longke.flag.entity.User;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.BitmapUtil;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
import com.longke.flag.util.widget.ActionSheetDialog;
import com.squareup.picasso.Picasso;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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
    protected static final int CHOOSE_PICTURE = 1001;
    protected static final int TAKE_PICTURE = 1002;
    private static final int CROP_SMALL_PICTURE = 1003;
    protected static Uri tempUri;
    private String PhotoUrl;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        user= (User) getIntent().getSerializableExtra("user");
        if(user!=null){
             PhotoUrl=user.getPhotoUrl();
            tvName.setText(user.getUserName());
            tvSgin.setText(user.getComments());
            tvSexover.setText(user.isSex()?"男":"女");
            Picasso.with(UserDetailActivity.this).load(Urls.BASE_URL+user.getPhotoUrl()).into(head);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "image.jpg"));
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                               /* Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 启动相机
                                startActivityForResult(intent1, REQUEST_THUMBNAIL);*/
                            }
                        }).addSheetItem("从相册中选", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                    }
                }).show();
                break;
            case R.id.rel_username:
                intent.setClass(UserDetailActivity.this, UserNameActivity.class);
                intent.putExtra("userName",tvName.getText().toString());
                startActivityForResult(intent, 2);
                break;
            case R.id.rel_sex:
                intent.setClass(UserDetailActivity.this, SexActivity.class);
                intent.putExtra("sex",tvSexover.getText().toString());
                startActivityForResult(intent, 1);
                break;
            case R.id.rel_sign:
                intent.setClass(UserDetailActivity.this, SignActivity.class);
                intent.putExtra("Sign",tvSgin.getText().toString());
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_add:
                HttpUtil.getInstance().GetTimestamp("ModifyUser");
                finish();
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

    public void upLoadFile(File file) {
        HttpUtil.getInstance().getOkHttp().upload()
                .url(Urls.UploadFile)
                .addFile("avatar", file)        //上传已经存在的File
//                .addFile("avatar2", "asdsda.png", byteContents)    //直接上传File bytes
                .tag(this)
                .enqueue(new GsonResponseHandler<UploadModel>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                       // Log.d(TAG, "doUpload onFailure:" + error_msg);
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                       // Log.d(TAG, "doUpload onProgress:" + currentBytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(int statusCode, UploadModel response) {

                        PhotoUrl= response.getMessage();
                        Log.d("longke", "doUpload onSuccess:" +response.getMessage());
                    }
                });


    }

    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     *
     * @param timestamp
     */
    public void GetSignData(final String timestamp) {
        final String UserCode = (String) SharedPreferencesUtil.get(UserDetailActivity.this, SharedPreferencesUtil.UserCode, "");
        String UserSecret = (String) SharedPreferencesUtil.get(UserDetailActivity.this, SharedPreferencesUtil.UserSecret, "");
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
                                ModifyUser(UserCode, timestamp, Message);
                            } else {
                                ToastUtil.showShort(UserDetailActivity.this, response.getString("Message"));
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

    public void ModifyUser(String appKey, String timestamp, String sign) {
        HttpUtil.getInstance().getOkHttp().post().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                 addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.ModifyUser)
                .addParam("timestamp", timestamp)
                .addParam("appKey", appKey)
                .addParam("sign", sign)
                .addParam("UserName", tvName.getText().toString())
                .addParam("PhotoUrl", PhotoUrl)
                .addParam("Comments", tvSgin.getText().toString())
                .addParam("Sex", tvSexover.getText().toString().equals("男") ? "true" : "false")
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if (response.getBoolean("Success")) {
                                ToastUtil.showShort(UserDetailActivity.this,"修改成功");
                                HttpUtil.getInstance().GetTimestamp("UserCenterIndex");
                            } else {
                                ToastUtil.showShort(UserDetailActivity.this, response.getString("Message"));
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
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;

            }
            if(data!=null){
                if (data.hasExtra("sign")) {
                    tvSgin.setText(data.getStringExtra("sign"));
                } else if (data.hasExtra("username")) {
                    tvName.setText(data.getStringExtra("username"));
                } else if (data.hasExtra("sex")) {
                    tvSexover.setText(data.getStringExtra("sex"));
                }
            }


            /*if (requestCode == REQUEST_CODE_PICK_IMAGE) {
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
                *//**
                 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
                 *//*
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                String imagePath = Environment
                        .getExternalStorageDirectory().getAbsolutePath() + "/" + String
                        .valueOf(System.currentTimeMillis()) + ".jpg";
                BitmapUtil.saveBitmap(bitmap, imagePath);
                upLoadFile(new File(imagePath));
                head.setImageBitmap(bitmap);
            }*/
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            //Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url=getPath(UserDetailActivity.this,uri);
            //String url=getPath(uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        }else{
            intent.setDataAndType(uri, "image/*");
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    //以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            head.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        String imagePath =Environment
                .getExternalStorageDirectory().getAbsolutePath()+"/"+String
                .valueOf(System.currentTimeMillis())+".jpg";
        BitmapUtil.saveBitmap(bitmap,imagePath);
        /*// 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }*/
        upLoadFile(new File(imagePath));
    }
}
