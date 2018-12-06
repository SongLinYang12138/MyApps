package com.bondex.ysl.pdaapp.query;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;

import java.util.ArrayList;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class QueryStowragePresenter extends BasePresnter<QueryStowrageView, QueryStowrageModel> implements QueryStowrageCallBack {

    QueryStowrageAdapter adapter;

    public QueryStowragePresenter(QueryStowrageView view, Context context) {
        super(view, context);
        view.showSearDialog();

        ArrayList<QueryStowrageBean> list = new ArrayList<>();
        adapter = new QueryStowrageAdapter(list);
        view.resultSuccess(adapter);
    }

    @Override
    public QueryStowrageModel getModal() {

        modal = new QueryStowrageModel(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void initData() {

    }


    public void search(String locationId, String traceId, String sku) {



        if(!isRunning){

            modal.search(traceId, locationId, sku);
            view.showLoading();
            isRunning = true;
        }

    }

    public void adapterClear() {

        if (adapter != null) {
            adapter.clearList();
            view.setSize(0);
        }
    }


    @Override
    public void searchResult(ArrayList<QueryStowrageBean> list) {


        adapter.setList(list);
        view.resultSuccess(null);
        view.setSize(list.size());
        view.stopLoading();
        isRunning = false;
    }

    @Override
    public void searchFailed(String msg) {

        view.resultFailed(msg);
        view.stopLoading();
        isRunning = false;
    }
}
