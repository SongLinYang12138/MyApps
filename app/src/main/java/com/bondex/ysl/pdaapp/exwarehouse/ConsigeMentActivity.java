package com.bondex.ysl.pdaapp.exwarehouse;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
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
    @BindView(R.id.consi_bt_out)
    ButtonRectangle consiBtOut;
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
        showTitle(true, "发货");


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

                break;
            case R.id.consi_bt_out:

                break;


        }

    }

    @Override
    public void initView() {

        confiBtConfirm.setOnClickListener(clickListener);
        consiBtOut.setOnClickListener(clickListener);

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
}
