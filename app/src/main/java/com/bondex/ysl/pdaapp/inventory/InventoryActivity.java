package com.bondex.ysl.pdaapp.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.inventory.movie.MovieInventoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class InventoryActivity extends BaseActivtiy<InventoryPresenter> implements InvertoryView{

    @BindView(R.id.inventory_standart_move)
    ConstraintLayout inventoryStandartMove;
    @BindView(R.id.line1)
    ConstraintLayout line1;
    @BindView(R.id.inventory_one_move)
    ConstraintLayout inventoryOneMove;
    @BindView(R.id.line2)
    ConstraintLayout line2;
    @BindView(R.id.inventory_query)
    ConstraintLayout inventoryQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inventory_layout);
        ButterKnife.bind(this);

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "库存");

    }

    @Override
    public InventoryPresenter getPresenter() {

        presenter = new InventoryPresenter(this,this);

        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

        Intent intent = null;

        switch (v.getId()){

            case R.id.inventory_one_move:

                intent = new Intent(InventoryActivity.this,MovieInventoryActivity.class);

                break;


        }


        if(intent != null) startBaseActivity(intent);
    }

    @Override
    public void initView() {

        inventoryOneMove.setOnClickListener(clickListener);
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
