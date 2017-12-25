package com.longke.flag.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.longke.flag.R;

public class PacketbagActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packetbag);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.bill_tv:
                startActivity(new Intent(PacketbagActivity.this,BillActivity.class));
                break;
            case R.id.recharge_tv:
                startActivity(new Intent(PacketbagActivity.this,RechargeActivity.class));
                break;
            case R.id.withdraw_tv:
                startActivity(new Intent(PacketbagActivity.this,WithdrawActivity.class));
                break;
            case R.id.mybank_tv:
                startActivity(new Intent(PacketbagActivity.this,MyBankActivity.class));
                break;
        }
    }
}
