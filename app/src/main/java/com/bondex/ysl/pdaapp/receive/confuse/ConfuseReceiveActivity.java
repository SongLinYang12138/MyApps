package com.bondex.ysl.pdaapp.receive.confuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.base.BasePresnter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class ConfuseReceiveActivity extends BaseActivtiy<ConfusePrensenter> {

    private final String[] ASN_TYPE = {"已提取", "入库通知", "生产收货", "客户拒收", "采购收货", "RR", "商务退机", "国内仓库调拨", "销售退回"};



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


    }

    @Override
    public ConfusePrensenter getPresenter() {
        return null;
    }

    @Override
    public void noDoubleClick(View v) {

    }
}
