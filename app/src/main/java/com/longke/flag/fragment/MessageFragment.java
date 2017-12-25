package com.longke.flag.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longke.flag.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private View mDecorView;
    private Activity mActivity;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity= (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDecorView = mActivity.getWindow().getDecorView();
        return inflater.inflate(R.layout.messagefragment_layout, container, false);
    }
    private void showSystemUI() {
        if(mDecorView==null)return;
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        showSystemUI();
    }
}
