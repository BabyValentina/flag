package com.longke.flag.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.longke.flag.R;


/**
 * Created by jiyongxiang on 2017/6/24.
 */

public class CommonDialog extends Dialog
{
    private Context mContext;
    private String mConfirm;
    private OnClickConfirm mOnClickConfirm;


    public CommonDialog(Context context, OnClickConfirm onClickConfirm)
    {
        super(context);
        mContext = context;
        mOnClickConfirm = onClickConfirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init()
    {
//        //去掉自定义布局顶部的蓝色title线
        View divider = findViewById(mContext.getResources().getIdentifier("android:id/titleDivider", null, null));
        //此处divider为null,有些手机取不到divider
        if(divider != null)
        {
            divider.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
        //设置自定义布局
//        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = View.inflate(mContext, R.layout.common_dialog, null);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.75); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView tvDetail = (TextView) view.findViewById(R.id.dialog_message);
        TextView tvConfirm = (TextView) view.findViewById(R.id.btn_comfirm);
        tvConfirm.setOnClickListener(new clickListener());


    }

    public static void Show(Context context)
    {
        new CommonDialog(context, null).show();
    }

    public static void Show(Context context, int title, int detail, OnClickConfirm onClickConfirm)
    {
        new CommonDialog(context, onClickConfirm).show();
    }


    private class clickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch(id)
            {
                case R.id.btn_comfirm:
                    dismiss();
                    if(mOnClickConfirm != null)
                    {
                        mOnClickConfirm.doConfirm();
                    }
                    break;
            }
        }
    }

    public interface OnClickConfirm
    {
        void doConfirm();
    }
}
