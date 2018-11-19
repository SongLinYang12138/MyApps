package com.bondex.ysl.pdaapp.exwarehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
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

                String code = etCode.getText().toString();

                if (CommonUtil.isEmpty(code)) {
                    ToastUtils.showToast("请输入单号");
                    return;
                }

                presenter.consignment(code);
                break;
            case R.id.confi_bt_clear:

                if(etCode != null) etCode.getText().clear();
                break;



        }

    }

    @Override
    public void initView() {

        confiBtConfirm.setOnClickListener(clickListener);
        confibtClear.setOnClickListener(clickListener);

        etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                    String code = etCode.getText().toString();
                    if (CommonUtil.isEmpty(code)) {
                        ToastUtils.showToast("请输入单号");
                        return false;
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

        showShort(data);
        finish();
    }

    @Override
    public void faile(String msg) {
        showLong(msg);
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
