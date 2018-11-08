package com.bondex.ysl.pdaapp.exwarehouse;

import android.content.Context;
import android.content.IntentFilter;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.SystemBroadCast;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.bondex.ysl.pdaapp.util.broadcast.PdaBroadCast;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class ConsigementPresenter extends BasePresnter<ConsigementView, ConsigeMentModel> implements ConsigeMentBack, PdaCallback {

    private PdaBroadCast pdaBroadCast = new PdaBroadCast(this);

    public ConsigementPresenter(ConsigementView view, Context context) {
        super(view, context);


        IntentFilter filter = new IntentFilter(SystemBroadCast.SCN_CUST_ACTION_SCODE);
        context.registerReceiver(pdaBroadCast, filter);
    }

    @Override
    public void initData() {


    }


    public void consignment(String id) {

        view.showLoading();
        int no = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

        modal.doNet(id, no + "");
    }

    @Override
    public ConsigeMentModel getModal() {

        modal = new ConsigeMentModel(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void faile(String msg) {
        view.stopLoading();
        ToastUtils.showToast(msg);
    }

    @Override
    public void success(ResultBean bean) {
        view.stopLoading();

        if (bean.isSuccess()) {

            view.onSuccess(bean.getMsg());
        } else {

            view.faile(bean.getErrormsg());
        }

    }

    @Override
    public void pdaResult(String result) {

        view.setCode(result);
    }

    public void onDestroy() {

        context.unregisterReceiver(pdaBroadCast);
    }

}
