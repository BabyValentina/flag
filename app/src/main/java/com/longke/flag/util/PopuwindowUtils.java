package com.longke.flag.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/6/14.
 */

public class PopuwindowUtils
{
    private PopupWindowView popupWindowView;
    private Context mcontext;
    private Activity activity;

    private List<Integer> imageList;

    private List<Integer> textList;

    public PopuwindowUtils(Context context, View view, Activity activity)
    {
        this.activity = activity;
        this.mcontext = context;
        popupWindowView = new PopupWindowView(context);
        popupWindowView.show(view);
    }


    // TODO 更多菜单
    public class PopupWindowView extends PopupWindow
    {
        //        @InjectView(R.id.btn_pop_task_detail_cancel)
//        RelativeLayout btnPopTaskDetailCancel;
//        @InjectView(R.id.view_pop_line)
//        View viewPopLine;
//        @InjectView(R.id.tv_wechatfriend)
//        TextView tvWechatfriend;
//        @InjectView(R.id.tv_pengyouquan)
//        TextView tvPengyouquan;
//        @InjectView(R.id.tv_weibo)
//        TextView tvWeibo;
//        @InjectView(R.id.tv_sendnew)
//        TextView tvSendnew;
//        @InjectView(R.id.tv_collect)
//        TextView tvCollect;
//        @InjectView(R.id.tv_del)
//        TextView tvDel;
//        @InjectView(R.id.gv_pop_task_detail_menu)
        LinearLayout gvPopTaskDetailMenu;
        private PopupWindow popupWindow = null;

        public PopupWindowView(final Context context)
        {
            View contentView = LayoutInflater.from(context).inflate(
                    R.layout.pop_main, null);
            popupWindow = new PopupWindow(contentView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
//            contentView.setFocusableInTouchMode(true);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnTouchListener(new View.OnTouchListener()
            {

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    closePop();
                    return true;
                }
            });
            contentView.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if(event.getAction() == KeyEvent.ACTION_DOWN &&
                            keyCode == KeyEvent.KEYCODE_BACK)
                    {
                    }
                    return true;
                }
            });


            contentView.findViewById(R.id.btn_pop_task_detail_cancel).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    closePop();
                }
            });

        }

        public void show(View v)
        {
            if(popupWindow != null && !popupWindow.isShowing())
            {
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        }

        /**
         * 关闭pop
         */
        public void closePop()
        {
            if(popupWindow != null && popupWindow.isShowing())
            {
                popupWindow.dismiss();
            }
        }

        @OnClick({R.id.btn_pop_task_detail_cancel, R.id.tv_wechatfriend, R.id.tv_pengyouquan, R.id.tv_weibo, R.id.tv_sendnew, R.id.tv_collect, R.id.tv_del})
        public void onViewClicked(View view)
        {
            switch(view.getId())
            {
                case R.id.btn_pop_task_detail_cancel:
                    //nothing
                    break;
                case R.id.tv_wechatfriend:
                    break;
                case R.id.tv_pengyouquan:
                    break;
                case R.id.tv_weibo:
                    break;
                case R.id.tv_sendnew:
                    break;
                case R.id.tv_collect:
                    break;
                case R.id.tv_del:
                    break;
            }
        }
    }

    // {{定义 登记问题、扫码 --先隐藏掉


    private void closepop()
    {
        popupWindowView.closePop();
    }
}
