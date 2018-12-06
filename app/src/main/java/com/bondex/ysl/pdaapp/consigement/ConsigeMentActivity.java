package com.bondex.ysl.pdaapp.consigement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SelecteAllListener;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class ConsigeMentActivity extends BaseActivtiy<ConsigementPresenter> implements ConsigementView {


    @BindView(R.id.consi_tv_code)
    TextView consiTvCode;
    @BindView(R.id.confi_bt_confirm)
    ButtonRectangle confiBtConfirm;
    @BindView(R.id.confi_bt_clear)
    ButtonRectangle confibtClear;
    @BindView(R.id.consi_ll_title)
    LinearLayout llTitle;

    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoading;
    @BindView(R.id.consi_et_code)
    MaterialEditText etCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consigement_layout);
        ButterKnife.bind(this);


        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "按订单发货");

        llTitle.setVisibility(View.INVISIBLE);
    }

    @Override
    public ConsigementPresenter getPresenter() {

        presenter = new ConsigementPresenter(this, this);
        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()) {


            case R.id.confi_bt_confirm:

                String code = etCode.getText().toString();

                if (CommonUtil.isEmpty(code)) {
                    ToastUtils.showToast(this,"请输入单号");
                    return;
                }

                presenter.consignment(code);
                break;
            case R.id.confi_bt_clear:

                if (etCode != null) etCode.getText().clear();
                break;


        }

    }

    @Override
    public void initView() {

        confiBtConfirm.setOnClickListener(clickListener);
        confibtClear.setOnClickListener(clickListener);


        if (Constant.SOFT_INPUTMOd) {
            hideSoftInputMethod(etCode);
        }

        etCode.setOnClickListener(new SelecteAllListener());

        etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    String code = etCode.getText().toString();
                    if (CommonUtil.isEmpty(code)) {
                        return true;
                    }
                    presenter.consignment(code);
                }
                return false;
            }
        });

    }

    @Override
    public void showLoading() {

        avLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        avLoading.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String data) {

        correctSound();
        showShort(this,data);
        finish();
    }

    @Override
    public void faile(String msg) {

        errorSound();
        showLong(this,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @Override
    public void setCode(String result) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(Constant.TAG, "" + etCode.getText().toString());
        etCode.getText().clear();
        etCode.setText(result);
    }
}
