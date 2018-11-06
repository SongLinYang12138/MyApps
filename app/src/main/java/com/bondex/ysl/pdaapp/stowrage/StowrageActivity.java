package com.bondex.ysl.pdaapp.stowrage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.loginebean.WarehousesBean;
import com.bondex.ysl.pdaapp.login.LoginActivity;
import com.bondex.ysl.pdaapp.main.MainActivity;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StowrageActivity extends BaseActivtiy<StowragePresenter> implements StowrageView {

    @BindView(R.id.stowrage_tv_choose)
    TextView stowrageTvChoose;
    @BindView(R.id.stowrage_tv_choose1)
    Spinner selectPopupWindow;
    @BindView(R.id.stowrage_ll_center)
    LinearLayout stowrageLlCenter;
    @BindView(R.id.stowrage_bt_confirm)
    ButtonRectangle stowrageBtConfirm;
    @BindView(R.id.stowrage_bt_cancel)
    ButtonRectangle stowrageBtCancel;
    @BindView(R.id.stowarge_tv_bottom)
    TextView tvBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stowrage);
        ButterKnife.bind(this);

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        showTitle(true, "请选择仓库");

        setSize();
    }

    private void setSize() {

        Window window = getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = CommonUtil.getScreentW(this)-10;
        lp.height = CommonUtil.getScreenH(this) -CommonUtil.getScreenH(this)/4;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private void toLogin(){


        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            toLogin();
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public StowragePresenter getPresenter() {

        if (presenter == null)
            presenter = new StowragePresenter(this, this);
        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()){
            case R.id.stowrage_bt_confirm:

                Intent intent = new Intent(this,MainActivity.class);
                startBaseActivity(intent);

                break;

            case R.id.stowrage_bt_cancel:

                finish();
                break;


        }

    }

    @Override
    public void initView() {

        stowrageBtCancel.setOnClickListener(clickListener);
        stowrageBtConfirm.setOnClickListener(clickListener);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void faile(String msg) {

    }


    @Override
    public void setBottomText(String msg) {

        tvBottom.setText(msg);
    }

    @Override
    public void setAdapter(ArrayAdapter<String> adapter) {


        selectPopupWindow.setAdapter(adapter);
        selectPopupWindow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WarehousesBean bean = presenter.getWarehousesBeans().get((int) id);
                SharedPreferecneUtils.saveValue(StowrageActivity.this,Constant.STORWAGEPAGE,Constant.SUBSYSTEM_NAME,bean.getSUBSYSTEM_NAME());
                SharedPreferecneUtils.saveInteger(StowrageActivity.this,Constant.STORWAGEPAGE,Constant.SUBSYSTEM_NO,bean.getNo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
