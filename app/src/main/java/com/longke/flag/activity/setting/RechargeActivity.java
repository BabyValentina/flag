package com.longke.flag.activity.setting;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.longke.flag.R;

public class RechargeActivity extends FragmentActivity {

    private ImageView yueIV,wxIV,alipayIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView(){
        yueIV= (ImageView) findViewById(R.id.yue_iv);
        wxIV= (ImageView) findViewById(R.id.wx_iv);
        alipayIV= (ImageView) findViewById(R.id.alipay_iv);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.yue_layout:
                changeImageView(R.drawable.yixuanze,R.drawable.xuanzeanniou,R.drawable.xuanzeanniou);
                break;
            case R.id.wx_layout:
                changeImageView(R.drawable.xuanzeanniou,R.drawable.yixuanze,R.drawable.xuanzeanniou);
                break;
            case R.id.alipay_layout:
                changeImageView(R.drawable.xuanzeanniou,R.drawable.xuanzeanniou,R.drawable.yixuanze);
                break;
        }
    }

    private void changeImageView(int res1,int res2,int res3){
        yueIV.setImageResource(res1);
        wxIV.setImageResource(res2);
        alipayIV.setImageResource(res3);
    }
}
