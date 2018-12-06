package com.bondex.ysl.pdaapp.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.ui.IconText;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.NoDoubleClickListener;

/**
 * date: 2018/11/20
 * Author: ysl
 * description:
 */
public abstract class BindBaseActivity<T extends BasePresnter> extends FragmentActivity {

    private LinearLayout llRoot;
    private TextView tvTitle;
    private IconText tvRight;
    private ImageView iv_left;
    public T presenter;
    public MyClickListener clickListener = new MyClickListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);

        setToolBar();
    }



    protected void showLeft(boolean isShow, int resourceId, View.OnClickListener listener) {


        llRoot = findViewById(R.id.ll_basetitle_root);
        iv_left = findViewById(R.id.base_back);

        if (isShow) iv_left.setVisibility(View.VISIBLE);
        else iv_left.setVisibility(View.INVISIBLE);

        if (resourceId != 0) iv_left.setImageResource(resourceId);

        if (listener != null) iv_left.setOnClickListener(listener);
    }


    protected void showRight(boolean isShow, int resourceId, View.OnClickListener listener) {
        llRoot = findViewById(R.id.ll_basetitle_root);
        tvRight = findViewById(R.id.base_right);

        if (isShow) tvRight.setVisibility(View.VISIBLE);
        else tvRight.setVisibility(View.INVISIBLE);

        if (resourceId != 0) tvRight.setText(resourceId);

        if (listener != null) tvRight.setOnClickListener(listener);
    }

    protected void showTitle(boolean isShow, String text) {

        llRoot = findViewById(R.id.ll_basetitle_root);
        tvTitle = findViewById(R.id.base_title);
        if (isShow) tvTitle.setVisibility(View.VISIBLE);
        else tvTitle.setVisibility(View.INVISIBLE);

        if (CommonUtil.isNotEmpty(text)) tvTitle.setText(text);
    }




    @Override
    protected void onResume() {
        super.onResume();

        getPresenter();
    }

    public abstract T getPresenter();


    public void setToolBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }

    }

    @Override
    public Resources getResources() {

//        确保显示的字体不随主题进行变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void startBaseActivity(Intent intent){

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    protected void startBaseActivityForResult(Intent intent,int requestCode){

        startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public abstract void noDoubleClick(View v);


    private class MyClickListener extends NoDoubleClickListener {


        @Override
        public void click(View v) {

            noDoubleClick(v);
        }
    }

    public void showLong(Context context, String msg) {

        if(context == null) return;
        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(PdaApplication.context, msg, Toast.LENGTH_SHORT).show();
    }


    public void showShort(Context context,String msg) {
        if(context == null) return;
        if(CommonUtil.isNotEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}