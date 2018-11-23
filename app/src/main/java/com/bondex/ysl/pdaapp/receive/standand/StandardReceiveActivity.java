package com.bondex.ysl.pdaapp.receive.standand;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;
import com.bondex.ysl.pdaapp.databinding.ActivityConsigementLayoutBinding;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class StandardReceiveActivity extends BaseActivtiy<StandardPrensenter> implements StandardView {

    private final String[] ASN_TYPE = {"已提取", "入库通知", "生产收货", "客户拒收", "采购收货", "RR", "商务退机", "国内仓库调拨", "销售退回"};
    @BindView(R.id.receive_tv_asno)
    TextView receiveTvAsno;
    @BindView(R.id.receive_et_asno)
    EditText receiveEtAsno;
    @BindView(R.id.receive_tv_custom)
    TextView receiveTvCustom;
    @BindView(R.id.receive_et_custom)
    EditText receiveEtCustom;
    @BindView(R.id.receive_tv_product)
    TextView receiveTvProduct;
    @BindView(R.id.receive_et_product)
    EditText receiveEtProduct;
    @BindView(R.id.recive_iv_number)
    ImageButton reciveIvNumber;
    @BindView(R.id.receive_tv_number)
    TextView receiveTvNumber;
    @BindView(R.id.receive_et_num)
    EditText receiveEtNum;
    @BindView(R.id.receive_tv_have_receive)
    TextView receiveTvHaveReceive;
    @BindView(R.id.receive_tv_stowrage)
    TextView receiveTvStowrage;
    @BindView(R.id.receive_et_shipper)
    EditText receiveEtShipper;
    @BindView(R.id.receive_tv_code)
    TextView receiveTvCode;
    @BindView(R.id.receive_tv_normal)
    AppCompatRadioButton receiveTvNormal;
    @BindView(R.id.receive_tv_wait)
    AppCompatRadioButton receiveTvWait;
    @BindView(R.id.receive_bt_confirm)
    Button receiveBtConfirm;
    @BindView(R.id.receive_bt_cancel)
    Button receiveBtCancel;
    @BindView(R.id.receive_freeze_code)
    RadioGroup radioGroup;

    @BindView(R.id.av_loading)
    AVLoadingIndicatorView loading;


    private Dialog searchDialog;

    private boolean isCodeSearched = false;
    private String freezeCode = "OK"; //false 正常 true待检


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receive_standard_layout);
        ButterKnife.bind(this);

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showTitle(true, "标准收货");
    }

    @Override
    public StandardPrensenter getPresenter() {

        if (presenter == null) {
            presenter = new StandardPrensenter(this, this);
        }
        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {


        switch (v.getId()) {

            case R.id.receive_bt_cancel:

                reset();
                break;
            case R.id.receive_bt_confirm:

                receiveConfirm();
                break;

            case R.id.recive_iv_number:

                searchProduct();
                break;
        }

    }

    private void reset() {

        receiveEtShipper.getText().clear();
        receiveEtProduct.getText().clear();
//        receiveEtCustom.getText().clear();
//        receiveEtAsno.getText().clear();
        receiveEtNum.getText().clear();
        receiveEtProduct.requestFocus();
        receiveBtConfirm.setBackgroundResource(R.drawable.bact_gray_bt);
        receiveBtConfirm.setClickable(false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initView() {

        reciveIvNumber.setOnClickListener(clickListener);
        receiveBtConfirm.setOnClickListener(clickListener);
        receiveBtCancel.setOnClickListener(clickListener);


        receiveBtConfirm.setBackgroundResource(R.drawable.bact_gray_bt);
        receiveBtConfirm.setClickable(false);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.receive_tv_normal:

                        freezeCode = "OK";
                        break;
                    case R.id.receive_tv_wait:

                        freezeCode = "DJ";
                        break;
                }

            }
        });

        receiveEtProduct.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    searchProduct();
                }
                return true;
            }
        });

    }


    private void searchProduct() {

        String code = receiveEtProduct.getText().toString();

        if (CommonUtil.isEmpty(code)) {

            showShort("请输入产品");
            return;
        }
        presenter.searchProduct(code);

    }


    private void receiveConfirm() {

        String receiveQty = receiveEtNum.getText().toString();
        String location = receiveEtShipper.getText().toString();

        if (CommonUtil.isEmpty(receiveQty)) {

            ToastUtils.showToast("请输入自收数量");
            return;
        }
        if (CommonUtil.isEmpty(location)) {

            ToastUtils.showToast("请输入自收货库位");
            return;
        }

        int qty = Integer.valueOf(receiveQty);
        presenter.receiveConfirm(qty, location, freezeCode);

    }

    @Override
    public void showLoading() {

        loading.setVisibility(View.VISIBLE);

    }

    @Override
    public void stopLoading() {


        loading.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void faile(String msg) {

    }

    ActivityConsigementLayoutBinding dailogBinding;

    @Override
    public void showSearch() {

        if (searchDialog == null) {

            searchDialog = new Dialog(this, R.style.dialog);

            View view = LayoutInflater.from(this).inflate(R.layout.activity_consigement_layout, null, false);

            dailogBinding = DataBindingUtil.bind(view);
            searchDialog.setContentView(dailogBinding.getRoot());

            dailogBinding.consiTvCode.setText("入库单号");
            dailogBinding.confiTvConfirm.setText("确定");

            searchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    if (!isCodeSearched) finish();
                }
            });

            dailogBinding.confiBtClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dailogBinding.consiEtCode.getText().clear();
                }
            });
            dailogBinding.confiBtConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code = dailogBinding.consiEtCode.getText().toString();

                    if (CommonUtil.isEmpty(code)) {
                        ToastUtils.showToast("请输入单号");
                        return;
                    }
                    presenter.searchCode(code);

                }

            });
            dailogBinding.consiEtCode.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {


                    String code = dailogBinding.consiEtCode.getText().toString();

                    if (CommonUtil.isEmpty(code)) {
                        ToastUtils.showToast("请输入单号");
                        return false;
                    }
                    presenter.searchCode(code);

                    return false;
                }
            });


            WindowManager.LayoutParams lp = searchDialog.getWindow().getAttributes();

            lp.width = CommonUtil.getScreentW(this) - 100;
            lp.height = CommonUtil.getScreenH(this) * 2 / 3;
            lp.gravity = Gravity.CENTER;
            searchDialog.getWindow().setAttributes(lp);
            searchDialog.setCanceledOnTouchOutside(false);
        } else {
            dailogBinding.consiEtCode.getText().clear();
        }

        searchDialog.show();
    }

    @Override
    public void codeResult(ReceiveStandardCodeBean bean) {

        isCodeSearched = true;
        receiveEtAsno.setText(bean.getAsnno());
        receiveEtCustom.setText(bean.getCustomerid() + "/" + bean.getCustomername());
        searchDialog.cancel();
        receiveEtProduct.getText().clear();

    }

    @Override
    public void searchCodeFaile(String msg) {
        showShort(msg);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void productResult(ReceiveStandardCodeBean bean) {

        receiveBtConfirm.setBackgroundResource(R.drawable.bt_blue_solid);
        receiveBtConfirm.setClickable(true);

        receiveTvHaveReceive.setText("已收数量: " + bean.getReceivedqty());
        receiveEtShipper.setText(bean.getReceivinglocation());
        receiveEtNum.requestFocus();
    }

    @Override
    public void productFaile(String msg) {

        showShort(msg);
    }

    @Override
    public void receiveSuccess(String msg) {

        showShort(msg);
        reset();
    }

    @Override
    public void receiveFalied(String msg) {

        showShort(msg);
        reset();
    }


}
