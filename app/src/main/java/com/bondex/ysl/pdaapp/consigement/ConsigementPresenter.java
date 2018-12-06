package com.bondex.ysl.pdaapp.consigement;

import android.content.Context;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class ConsigementPresenter extends BasePresnter<ConsigementView, ConsigeMentModel> implements ConsigeMentBack, PdaCallback {


    public ConsigementPresenter(ConsigementView view, Context context) {
        super(view, context);



    }

    @Override
    public void initData() {


    }


    public void consignment(String id) {


        if(!isRunning){

            view.showLoading();
            int no = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

            modal.doNet(id, no + "");
            isRunning = true;
        }

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
        ToastUtils.showToast(context,msg);
        isRunning = false;
    }

    @Override
    public void success(ResultBean bean) {

        view.stopLoading();
        if (bean.isSuccess()) {

            view.onSuccess(bean.getMsg());
        } else {

            view.faile(bean.getErrormsg());
        }
        isRunning = false;

    }

    @Override
    public void pdaResult(String result) {

      if(CommonUtil.isNotEmpty(result))  view.setCode(result);


    }

    public void onDestroy() {


    }

}
