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
public class MoviePresenter extends BasePresnter<MovieView, MovieModal> implements  MovieBack {



    private boolean isTraceIdExist;
    private String traceId; //被移除的单号

    private int stoId;

    private String removeKey;//设置的是上一次移除的id

    public MoviePresenter(MovieView view, Context context) {
        super(view, context);

        stoId = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        stoId = 4;
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

        if(!isRunning){
            view.showLoading();
            isTraceIdExist = false;

            modal.searchTruckNum(id, stoId);
            isRunning = true;
            traceId = id;
        }

    }

    public void removeStowrage(String stowrage) {

        if (!isTraceIdExist) {
            ToastUtils.showToast(context,"请先查询原始单号成功");
            return;
        }


        removeKey = stowrage;



        if(!isRunning){
            view.showLoading();
            modal.removieStowrage(traceId, stoId,stowrage);
            isRunning = true;
        }

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
        isRunning = false;

        view.stopLoading();
    }

    @Override
    public void onFailed(String msg) {

        ToastUtils.showToast(context,msg);
        view.showErrorSound();
        view.stopLoading();
        isRunning = false;
    }

    @Override
    public void removeStowrage(ResultBean bean) {


        view.stopLoading();
        if (bean.isSuccess()) {

            view.onSuccess("移库成功");
        } else {

            view.faile(bean.getErrormsg());
        }
        isRunning = false;

    }

    public void destroy() {


    }

}
