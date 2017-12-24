package com.longke.flag.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.longke.flag.R;

public class WithdrawActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.layout_bank:
                startActivity(new Intent(WithdrawActivity.this,MyBankActivity.class));
                break;
        }
    }
}
