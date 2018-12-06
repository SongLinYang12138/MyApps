package com.bondex.ysl.pdaapp.receive.standand;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;
import com.bondex.ysl.pdaapp.databinding.ActivityConsigementLayoutBinding;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SelecteAllListener;
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


    @BindView(R.id.receive_et_asno)
    EditText receiveEtAsno;
    @BindView(R.id.receive_et_custom)
    EditText receiveEtCustom;
    @BindView(R.id.receive_et_product)
    EditText receiveEtProduct;
    @BindView(R.id.recive_iv_number)
    ImageButton reciveIvNumber;

    @BindView(R.id.receive_et_num)
    EditText receiveEtNum;
    @BindView(R.id.receive_tv_have_receive)
    TextView receiveTvHaveReceive;
    @BindView(R.id.receive_et_stowrage)
    EditText receiveEtStowage;
    @BindView(R.id.receive_et_weight)
    EditText etWeight;
    @BindView(R.id.receive_et_fweight)
    EditText etFweight;
    @BindView(R.id.receive_et_volume)
    EditText etVolume;
    @BindView(R.id.receive_et_price)
    EditText etPrice;
    @BindView(R.id.receive_et_productname)
    EditText etProductName;


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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        receiveEtStowage.getText().clear();
        receiveEtProduct.getText().clear();
        receiveEtNum.getText().clear();
        etWeight.getText().clear();
        etFweight.getText().clear();
        etPrice.getText().clear();
        etVolume.getText().clear();
        receiveEtProduct.requestFocus();
        receiveBtConfirm.setBackgroundResource(R.drawable.bact_gray_bt);
        receiveBtConfirm.setClickable(false);
        etProductName.getText().clear();
        receiveTvHaveReceive.setText("预期/已收:   ");

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initView() {

        reciveIvNumber.setOnClickListener(clickListener);
        receiveBtConfirm.setOnClickListener(clickListener);
        receiveBtCancel.setOnClickListener(clickListener);
        etWeight.setOnClickListener(selectAll);
        etFweight.setOnClickListener(selectAll);
        etVolume.setOnClickListener(selectAll);
        etPrice.setOnClickListener(selectAll);

        receiveBtConfirm.setBackgroundResource(R.drawable.bact_gray_bt);
        receiveBtConfirm.setClickable(false);

        receiveEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = s.toString();

                if (CommonUtil.isEmpty(str)) {
                    return;
                }

                if (!CommonUtil.isNumber(str)) {
                    return;
                }

                double num = Double.valueOf(s.toString());
                presenter.modifyTotal(num);

            }
        });
        receiveEtNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    receiveEtStowage.requestFocus();
                    receiveEtStowage.selectAll();
                    return true;
                }

                return false;
            }
        });

        receiveEtStowage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    etWeight.requestFocus();
                    etWeight.selectAll();
                    return true;
                }
                return false;
            }
        });
        receiveEtStowage.setOnClickListener(selectAll);

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
                    case R.id.receive_tv_lossless:
                        freezeCode = "NG";
                        break;
                }

            }
        });

        receiveEtProduct.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    searchProduct();

                    return true;
                }
                return false;
            }
        });

        etWeight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    etFweight.requestFocus();
                    etFweight.selectAll();
                    return true;
                }
                return false;
            }
        });

        etFweight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    etVolume.requestFocus();
                    etVolume.selectAll();
                    return true;
                }

                return false;
            }
        });

        etVolume.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    etPrice.requestFocus();
                    etPrice.selectAll();
                    return true;
                }

                return false;
            }
        });


        receiveEtProduct.setOnClickListener(selectAll);

        if (Constant.SOFT_INPUTMOd) {

            hideSoftInputMethod(etPrice);
            hideSoftInputMethod(etVolume);
            hideSoftInputMethod(etFweight);
            hideSoftInputMethod(etWeight);
            hideSoftInputMethod(etProductName);
            hideSoftInputMethod(receiveEtStowage);
            hideSoftInputMethod(receiveEtNum);
            hideSoftInputMethod(receiveEtProduct);
            hideSoftInputMethod(receiveEtAsno);
            hideSoftInputMethod(receiveEtCustom);
        }

    }


    private void searchProduct() {

        String code = receiveEtProduct.getText().toString();

        if (CommonUtil.isEmpty(code)) {
            return;
        }
        presenter.searchProduct(code);

    }


    private void receiveConfirm() {

        String receiveQty = receiveEtNum.getText().toString();
        String location = receiveEtStowage.getText().toString();

        String weight = etWeight.getText().toString();
        String fweight = etFweight.getText().toString();
        String volume = etVolume.getText().toString();
        String price = etPrice.getText().toString();


        if (CommonUtil.isEmpty(receiveQty)) {

            showShort(this,"请输入自收数量");
            return;
        }
        if (CommonUtil.isEmpty(location)) {

            showShort(this,"请输入自收货库位");
            return;
        }
        if (!CommonUtil.isNumber(weight)) {

            showShort(this,"净重只能是数字");
            return;
        }
        if (!CommonUtil.isNumber(fweight)) {
            showShort(this,"毛重只能是数字");
            return;
        }
        if (!CommonUtil.isNumber(volume)) {
            showShort(this,"体积只能是数字");
            return;
        }
        if (!CommonUtil.isNumber(price)) {
            showShort(this,"总价只能是数字");
            return;
        }


        int qty = Integer.valueOf(receiveQty);

        presenter.receiveConfirm(qty, location, freezeCode, Double.valueOf(weight), Double.valueOf(fweight), Double.valueOf(volume), Double.valueOf(price));

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

    ActivityConsigementLayoutBinding dialogBinding;

    @Override
    public void showSearch() {

        if (searchDialog == null) {

            searchDialog = new Dialog(this, R.style.dialog);

            View view = LayoutInflater.from(this).inflate(R.layout.activity_consigement_layout, null, false);

            dialogBinding = DataBindingUtil.bind(view);
            searchDialog.setContentView(dialogBinding.getRoot());

            dialogBinding.consiTvCode.setText("入库单号");
            dialogBinding.confiTvConfirm.setText("确定");
            ImageView ivBack = view.findViewById(R.id.base_back);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

//            dialogBinding.consiEtCode.setInputType(InputType.TYPE_NULL);
            searchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    if (!isCodeSearched) finish();
                }
            });

            dialogBinding.confiBtClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogBinding.consiEtCode.getText().clear();
                }
            });
            dialogBinding.confiBtConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code = dialogBinding.consiEtCode.getText().toString();

                    if (CommonUtil.isEmpty(code)) {
                        return;
                    }
                    presenter.searchCode(code);

                }

            });
            dialogBinding.consiEtCode.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {


                    String code = dialogBinding.consiEtCode.getText().toString();

                    if (CommonUtil.isEmpty(code)) {
                        return true;
                    }
                    presenter.searchCode(code);

                    return false;
                }
            });

            dialogBinding.consiEtCode.setOnClickListener(selectAll);

            WindowManager.LayoutParams lp = searchDialog.getWindow().getAttributes();

            lp.width = CommonUtil.getScreentW(this) - 100;
            lp.height = CommonUtil.getScreenH(this) * 2 / 3;
            lp.gravity = Gravity.CENTER;
            searchDialog.getWindow().setAttributes(lp);
            searchDialog.setCanceledOnTouchOutside(false);

            if (Constant.SOFT_INPUTMOd) {
//
                hideSoftInputMethod(dialogBinding.consiEtCode);

            }

        } else {
            dialogBinding.consiEtCode.getText().clear();
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
        receiveEtProduct.requestFocus();
        correctSound();
    }

    @Override
    public void searchCodeFaile(String msg) {
        showShort(this,msg);
        errorSound();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void productResult(ReceiveStandardCodeBean bean) {

        receiveBtConfirm.setBackgroundResource(R.drawable.bt_blue_solid);
        receiveBtConfirm.setClickable(true);

        receiveTvHaveReceive.setText("预期/已收:   " + bean.getExpectedqty() + "/" + bean.getReceivedqty());
        receiveEtStowage.setText(bean.getReceivinglocation());
        receiveEtNum.requestFocus();
        etProductName.setText(bean.getSkuname());


        correctSound();
    }


    @Override
    public void productFaile(String msg) {

        showShort(this,msg);
        errorSound();
    }

    @Override
    public void receiveSuccess(String msg) {

        showShort(this,msg);
        reset();
        correctSound();
    }

    @Override
    public void receiveFalied(String msg) {

        showShort(this,msg);
        reset();
        errorSound();
    }

    @Override
    public void modifyTotal(double weight, double fweight, double volume, double price) {


        etWeight.setText(weight + "");
        etFweight.setText(fweight + "");
        etVolume.setText(volume + "");
        etPrice.setText(price + "");
    }

    @Override
    public String getReceiveNm() {

        return receiveEtNum.getText().toString();
    }


}
