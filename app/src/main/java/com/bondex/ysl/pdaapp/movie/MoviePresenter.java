package com.bondex.ysl.pdaapp.movie;

import android.content.Context;
import android.content.IntentFilter;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.InventoryMovieOneTraceIdBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.SystemBroadCast;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.bondex.ysl.pdaapp.util.broadcast.PdaBroadCast;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;

import java.util.LinkedHashMap;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class MoviePresenter extends BasePresnter<MovieView, MovieModal> implements PdaCallback, MovieBack {

    private PdaBroadCast pdaBroadCast;

    private boolean isTraceIdExist;

    private String removeKey;//设置的是上一次移除的id
    private LinkedHashMap<String, Boolean> resultMap = new LinkedHashMap<>();//将移除id的结果收集到map中

    public MoviePresenter(MovieView view, Context context) {
        super(view, context);

        pdaBroadCast = new PdaBroadCast(this);
        IntentFilter intentFilter = new IntentFilter(SystemBroadCast.SCN_CUST_ACTION_SCODE);
        context.registerReceiver(pdaBroadCast, intentFilter);
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
        int num = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        modal.searchTruckNum(id, num);
    }

    public void removeStowrage(String id) {

        if (!isTraceIdExist) {
            ToastUtils.showToast("请先查询原始单号成功");
            return;
        }

     if(resultMap.get(id) == null)   resultMap.put(id, false);

        removeKey = id;

//        判断一个单号移除一次
        if(resultMap.get(id)){

            ToastUtils.showToast("该单号已移除成功");
            return;
        }

        view.showLoading();
        int num = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        modal.removieStowrage(id, num);
    }


    @Override
    public void pdaResult(String result) {

        view.pdaResult(result);
    }

    @Override
    public void traceIdExists(InventoryMovieOneTraceIdBean bean) {

        if (bean.isSuccess()) {

            ToastUtils.showToast("单号已存在，可以移除");
            view.toStorLocaltion();
            isTraceIdExist = true;
        } else {
            ToastUtils.showToast(bean.getErrormsg());
        }

        view.stopLoading();
    }

    @Override
    public void onFailed(String msg) {

        ToastUtils.showToast(msg);
        view.stopLoading();
    }

    @Override
    public void removeStowrage(InventoryMovieOneTraceIdBean bean) {


        view.stopLoading();
        if (bean.isSuccess()) {

            resultMap.put(removeKey,true);
            view.onSuccess("移除成功");
        } else {

            view.faile(bean.getErrormsg());
        }

    }

    public void destroy() {

        resultMap.clear();
        context.unregisterReceiver(pdaBroadCast);
    }

}