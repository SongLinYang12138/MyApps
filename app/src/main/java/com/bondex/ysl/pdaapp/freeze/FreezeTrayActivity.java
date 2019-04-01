package com.bondex.ysl.pdaapp.freeze;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.FreezeSearchBean;
import com.bondex.ysl.pdaapp.ui.IconText;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FreezeTrayActivity extends BaseActivtiy<FreezeTrayPresenter> implements FreezeView {


    @BindView(R.id.freeze_et_code)
    MaterialEditText freezeEtCode;
    @BindView(R.id.freeze_it_code)
    IconText freezeItCode;
    @BindView(R.id.freeze_tv_batch)
    TextView freezeTvBatch;
    @BindView(R.id.freeze_tv_location)
    TextView freezeTvLocation;
    @BindView(R.id.freeze_tv_code)
    TextView freezeTvCode;
    @BindView(R.id.freeze_tv_amount)
    TextView freezeTvAmount;
    @BindView(R.id.freeze_tv_freeze_amount)
    TextView freezeTvFreezeAmount;
    @BindView(R.id.freeze_tv_freeze_num)
    TextView freezeTvFreezeNum;
    @BindView(R.id.freeze_rg_reason)
    RadioGroup freezeRgReason;
    @BindView(R.id.freeze_bt_freeze)
    Button freezeBtFreeze;
    @BindView(R.id.freeze_bt_un_freeze)
    Button freezeBtUnFreeze;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoading;
    @BindView(R.id.freeze_rb_wait)
    RadioButton freezeRbWait;
    @BindView(R.id.freeze_rb_stained)
    RadioButton freezeRbStained;

    private int freezeCanUsed = 0;//0为搜索 1 可用 2不可用
    private FreezeSearchBean freezeSearchBean;
    private String freezeReason = "待检";
    private String freezeCode = "DJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze_tray);
        ButterKnife.bind(this);
    }

    @Override
    public FreezeTrayPresenter getPresenter() {

        if (presenter == null) {

            presenter = new FreezeTrayPresenter(this, this);
        }


        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()) {

            case R.id.freeze_it_code:

                search();
                break;
            case R.id.freeze_bt_freeze:

                if (freezeCanUsed == 0 || freezeCanUsed == 2) {
                    ToastUtils.showToast(FreezeTrayActivity.this, "冻结功能不可用");
                    return;
                }

                if (freezeSearchBean == null) {
                    ToastUtils.showToast(FreezeTrayActivity.this, "请先搜索托盘号");
                    return;
                }

                freezeSearchBean.setHoldcode(freezeCode);
                freezeSearchBean.setHoldreason(freezeReason);
                presenter.freeze(freezeSearchBean);


                break;

            case R.id.freeze_bt_un_freeze:
                if (freezeCanUsed == 0 || freezeCanUsed == 1) {
                    ToastUtils.showToast(FreezeTrayActivity.this, "解冻功能不可用");
                    return;
                }

                if (freezeSearchBean == null) {
                    ToastUtils.showToast(FreezeTrayActivity.this, "请先搜索托盘号");
                    return;
                }

                presenter.unFreeze(freezeSearchBean);
                break;


        }

    }

    @Override
    public void initView() {


        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "托盘冻结");

        hideSoftInputMethod(freezeEtCode);

        freezeItCode.setOnClickListener(clickListener);
        freezeEtCode.setOnClickListener(selectAll);
        freezeBtFreeze.setOnClickListener(clickListener);
        freezeBtUnFreeze.setOnClickListener(clickListener);
        freezeEtCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    search();
                    return true;
                }
                return false;
            }
        });
        freezeRgReason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.freeze_rb_stained:
                        freezeCode = "NG";
                        freezeReason = "污损";
                        break;

                    case R.id.freeze_rb_wait:
                        freezeCode = "DJ";
                        freezeReason = "待检";

                        break;

                }
            }
        });


    }


    private void search() {

        String code = freezeEtCode.getText().toString();

        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast(this, "请输入托盘号");
            return;
        }

        code = code.replaceAll("\n", code);
        presenter.search(code);
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

    }

    @Override
    public void faile(String msg) {

    }

    @Override
    public void searchSuccess(FreezeSearchBean info) {

        freezeSearchBean = info;

        freezeTvBatch.setText("批次属性:  " + info.getLotnum());
        freezeTvLocation.setText("库位： " + info.getLocationid());
        freezeTvCode.setText("托盘号: " + info.getTraceid());
        freezeTvAmount.setText("数量: " + info.getQty());
        freezeTvFreezeAmount.setText("冻结数量:  " + info.getQtyonhold());

        if (info.getHoldcode().equals("NG")) {

            freezeRbStained.setChecked(true);
            freezeRbWait.setFocusable(false);
        } else if (info.getHoldcode().equals("DJ")) {
            freezeRbStained.setChecked(false);
            freezeRbWait.setFocusable(true);
        } else {
            freezeRbStained.setChecked(false);
            freezeRbWait.setFocusable(false);
        }

        if (CommonUtil.isNotEmpty(info.getHoldreason())) {

            freezeCanUsed = 2;

        } else {

            freezeCanUsed = 1;

        }


    }

    @Override
    public void searchFaile(String search) {

        ToastUtils.showToast(this, search);
    }

    @Override
    public void freezeResult(boolean isSuccess, String msg) {

        if (isSuccess) {
            clear();
        }
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void unFreezeResult(boolean isSuccess, String msg) {


        if (isSuccess) {
            clear();
        }
        ToastUtils.showToast(this, msg);
    }

    private void clear() {

        freezeEtCode.getText().clear();
        freezeTvBatch.setText("批次属性:  ");
        freezeTvLocation.setText("库位： ");
        freezeTvCode.setText("托盘号: ");
        freezeTvAmount.setText("数量: ");
        freezeTvFreezeAmount.setText("冻结数量:  ");
        freezeReason = "待检";
        freezeCode = "DJ";
        freezeRbWait.setChecked(true);
        freezeRbStained.setChecked(false);
        freezeSearchBean = null;
        freezeCanUsed = 0;
    }
}
