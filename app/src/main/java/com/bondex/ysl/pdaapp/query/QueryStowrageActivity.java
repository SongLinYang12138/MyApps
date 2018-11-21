package com.bondex.ysl.pdaapp.query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class QueryStowrageActivity extends BaseActivtiy<QueryStowragePresenter> implements QueryStowrageView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stowrage_query_layout);

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "库存查询");
    }

    @Override
    public QueryStowragePresenter getPresenter() {

        if (presenter == null) {
            presenter = new QueryStowragePresenter(this, this);
        }
        return presenter;
    }


    @Override
    public void noDoubleClick(View v) {


    }

    @Override
    public void initView() {

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
    public void showSearDialog() {




    }

}
