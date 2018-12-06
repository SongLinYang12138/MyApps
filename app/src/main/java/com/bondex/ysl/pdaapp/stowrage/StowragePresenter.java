package com.bondex.ysl.pdaapp.stowrage;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.loginebean.WarehousesBean;

import java.util.ArrayList;

public class StowragePresenter extends BasePresnter<StowrageView, StowrageModal> {


    private ArrayList<WarehousesBean> warehousesBeans;

    public ArrayList<WarehousesBean> getWarehousesBeans() {
        return warehousesBeans;
    }

    public StowragePresenter(StowrageView view, Context context) {
        super(view, context);


        ArrayList<String> list = new ArrayList<>();
        warehousesBeans = PdaApplication.LOGINBEAN.getWarehouses();

        for (WarehousesBean bean : warehousesBeans) {
            list.add(bean.getSUBSYSTEM_NAME());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.text_item, list);

        view.setAdapter(adapter);
        view.setBottomText("有" + warehousesBeans.size() + "个数据仓库可用");


    }

    @Override
    public StowrageModal getModal() {

        modal = new StowrageModal(context);
        return modal;
    }

    @Override
    public void initData() {

    }
}
