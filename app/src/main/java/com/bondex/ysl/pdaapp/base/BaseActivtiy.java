package com.bondex.ysl.pdaapp.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.bondex.ysl.pdaapp.util.SelecteAllListener;
import com.bondex.ysl.pdaapp.util.SystemBroadCast;
import com.bondex.ysl.pdaapp.util.broadcast.PdaBroadCast;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;

import java.lang.reflect.Method;

public abstract class BaseActivtiy<T extends BasePresnter> extends FragmentActivity {

    private LinearLayout llRoot;
    private TextView tvTitle;
    private IconText tvRight;
    private ImageView iv_left;
    public T presenter;
    private SoundPool correctPool, errorPool;

    public MyClickListener clickListener = null;
    public SelecteAllListener selectAll = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);
        findView();
        setToolBar();

        clickListener = new MyClickListener();
        selectAll = new SelecteAllListener();
        correctPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        correctPool.load(this, R.raw.correct, 1);

        errorPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        errorPool.load(this, R.raw.error, 1);


    }


    private void findView() {

        llRoot = findViewById(R.id.ll_basetitle_root);
        iv_left = findViewById(R.id.base_back);
        tvRight = findViewById(R.id.base_right);
        tvTitle = findViewById(R.id.base_title);
    }


    protected void showLeft(boolean isShow, int resourceId, View.OnClickListener listener) {

        if (isShow) iv_left.setVisibility(View.VISIBLE);
        else iv_left.setVisibility(View.INVISIBLE);

        if (resourceId != 0) iv_left.setImageResource(resourceId);

        if (listener != null) iv_left.setOnClickListener(listener);
    }


    protected void showRight(boolean isShow, int resourceId, View.OnClickListener listener) {


        if (isShow) tvRight.setVisibility(View.VISIBLE);
        else tvRight.setVisibility(View.INVISIBLE);

        if (resourceId != 0) tvRight.setText(resourceId);

        if (listener != null) tvRight.setOnClickListener(listener);
    }

    protected void showTitle(boolean isShow, String text) {

        if (isShow) tvTitle.setVisibility(View.VISIBLE);
        else tvTitle.setVisibility(View.INVISIBLE);

        if (CommonUtil.isNotEmpty(text)) tvTitle.setText(text);
    }


    /**
     * 重点是重写setContentView，让继承者可以继续设置setContentView
     * 重写setContentView
     *
     * @param resId
     */
    @Override
    public void setContentView(int resId) {


        View view = getLayoutInflater().inflate(resId, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.ll_basetitle_root);
        if (null != llRoot)
            llRoot.addView(view, lp);

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

    protected void startBaseActivity(Intent intent) {

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void startBaseActivityForResult(Intent intent, int requestCode) {

        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public abstract void noDoubleClick(View v);


    private class MyClickListener extends NoDoubleClickListener {


        @Override
        public void click(View v) {

            noDoubleClick(v);
        }
    }

    public void showLong(Context context,String msg) {

        if(context == null) return;
        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public void showShort(Context context,String msg) {
        if(context == null) return;
        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    protected void correctSound() {
        correctPool.play(1, 1, 1, 0, 0, 1);

    }

    protected void errorSound() {
        errorPool.play(1, 1, 1, 0, 0, 1);

    }


    /** ==== 隐藏系统键盘 ======*/
    //用这个方法关闭系统键盘就不会出现光标消失的问题了
    public void hideSoftInputMethod(EditText ed){

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String methodName = null;
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if(currentVersion >= 16){
            // 4.2
            methodName = "setShowSoftInputOnFocus";  //
        }else if(currentVersion >= 14){
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if(methodName == null){
            //最低级最不济的方式，这个方式会把光标给屏蔽
            ed.setInputType(InputType.TYPE_NULL);
        }else{
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        selectAll = null;
        clickListener = null;
        errorPool = null;
        correctPool = null;

    }
}