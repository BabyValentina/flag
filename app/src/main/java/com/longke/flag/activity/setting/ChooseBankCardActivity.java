package com.longke.flag.activity.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.CardTypeAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseBankCardActivity extends AppCompatActivity {

    @InjectView(R.id.back_img_btn)
    ImageView mBackImgBtn;
    @InjectView(R.id.current_section)
    TextView mCurrentSection;
    @InjectView(R.id.finish_tv)
    TextView mFinishTv;
    @InjectView(R.id.cra_title)
    RelativeLayout mCraTitle;
    @InjectView(R.id.card_lv)
    ListView mCardLv;
    @InjectView(R.id.activity_choose_bank_card)
    LinearLayout mActivityChooseBankCard;
    private CardTypeAdapter mCardTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_card);
        ButterKnife.inject(this);
        mCardTypeAdapter=new CardTypeAdapter(this);
        mCardLv.setAdapter(mCardTypeAdapter);
    }

    @OnClick({R.id.back_img_btn, R.id.finish_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img_btn:
                finish();
                break;
            case R.id.finish_tv:
                finish();
                break;
        }
    }
}
