package com.longke.flag.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.longke.flag.R;
import com.longke.flag.activity.setting.PacketbagActivity;

public class SettingActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.packetbag_layout:
                startActivity(new Intent(SettingActivity.this, PacketbagActivity.class));
                break;
            case R.id.password_layout:
                //startActivity(new Intent(SettingActivity.this, Pass.class));
                break;
            case R.id.feedback_layout:
                //startActivity(new Intent(SettingActivity.this, F.class));
                break;
            case R.id.aboutus_layout:
                break;
        }
    }
}
