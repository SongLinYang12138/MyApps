package com.bondex.ysl.pdaapp.movie;

import android.content.Context;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;
import java.util.LinkedHashMap;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class MoviePresenter extends BasePresnter<MovieView, MovieModal> implements PdaCallback, MovieBack {



    private boolean isTraceIdExist;
    private String traceId; //被移除的单号

    private String removeKey;//设置的是上一次移除的id
    private LinkedHashMap<String, Boolean> resultMap = new LinkedHashMap<>();//将移除id的结果收集到map中

    public MoviePresenter(MovieView view, Context context) {
        super(view, context);

    }

    @Override
    public MovieModal getModal() {

        modal = new MovieModal(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void initData() {

    }

    public void traceId(String id) {

        view.showLoading();
        isTraceIdExist = false;
        int num = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        modal.searchTruckNum(id, num);
        traceId = id;
    }

    public void removeStowrage(String stowrage) {

        if (!isTraceIdExist) {
            ToastUtils.showToast(context,"请先查询原始单号成功");
            return;
        }

     if(resultMap.get(stowrage) == null)   resultMap.put(stowrage, false);

        removeKey = stowrage;

//        判断一个库位移除一次
        if(resultMap.get(stowrage)){

            ToastUtils.showToast(context,"该库位已移库成功");
            return;
        }

        view.showLoading();
        int num = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        modal.removieStowrage(traceId, num,stowrage);
    }


    @Override
    public void pdaResult(String result) {

       if(CommonUtil.isNotEmpty(result)) view.pdaResult(result);
    }

    @Override
    public void traceIdExists(ResultBean bean) {

        if (bean.isSuccess()) {

            view.toStorLocaltion();
            isTraceIdExist = true;
            view.setBtBack(true,bean);
        } else {
            view.setBtBack(false,bean);
            ToastUtils.showToast(context,bean.getErrormsg());
        }

        view.stopLoading();
    }

    @Override
    public void onFailed(String msg) {

        ToastUtils.showToast(context,msg);
        view.showErrorSound();
        view.stopLoading();
    }

    @Override
    public void removeStowrage(ResultBean bean) {


        view.stopLoading();
        if (bean.isSuccess()) {

            resultMap.put(removeKey,true);
            view.onSuccess("移库成功");
        } else {

            view.faile(bean.getErrormsg());
        }

    }

    public void destroy() {

        resultMap.clear();
    }

}
