package com.bondex.ysl.pdaapp.freeze;

import android.content.Context;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.FreezeSearchBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * date: 2019/4/1
 * Author: ysl
 * description:
 */
public class FreezeTrayPresenter extends BasePresnter<FreezeView, FreezeTrayModle> implements FreezeTrayBack {

    int no = 0;


    public FreezeTrayPresenter(FreezeView view, Context context) {
        super(view, context);
        no = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

        modal.setCallback(this);
    }


    @Override
    public FreezeTrayModle getModal() {

        if (modal == null) {
            modal = new FreezeTrayModle(context);
        }

        return modal;
    }

    @Override
    public void initData() {

    }

    public void search(String str) {

        String method = "inv.beforeFreeze";//方法名

        JSONObject map = new JSONObject();

        try {
            map.put("userid", PdaApplication.LOGINBEAN.getUserid());//当前登录用户ID
            map.put("warehouseno", Integer.valueOf(no));//仓库ID
            map.put("traceid", str);
            String param = map.toString();

            param = ParamUtils.getParams(param, method);

            modal.searCode(param);

            view.showLoading();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void freeze(FreezeSearchBean searchBean) {


        String method = "inv.freeze";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("userid", "feisi");//当前登录用户

            map.put("warehouseno", Integer.valueOf(no));
            map.put("traceid", searchBean.getTraceid());//托盘号
            map.put("type", "hold");//冻结标志，默认值
            map.put("lotnum", searchBean.getLotnum());//批次
            map.put("locationid", searchBean.getLocationid());//库位
            map.put("holdcode", searchBean.getHoldcode().trim());//冻结原因Code
            map.put("holdreason", searchBean.getHoldreason().trim());//冻结原因名称

            String param = map.toString();

            param = ParamUtils.getParams(param, method);

            modal.freeze(param);
            view.showLoading();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void unFreeze(FreezeSearchBean bean) {


        String method = "inv.freeze";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("userid", PdaApplication.LOGINBEAN.getUserid());//当前登录用户

            map.put("warehouseno", Integer.valueOf(no));
            map.put("type", "cancelhold");//解冻标志，默认值
            map.put("inventoryholdid", bean.getInventoryholdid());//冻结编号

            String param = map.toString();

            param = ParamUtils.getParams(param, method);
            modal.unFreeze(param);
            view.showLoading();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void searchSuccess(FreezeSearchBean bean) {
        view.searchSuccess(bean);
        view.stopLoading();
    }

    @Override
    public void searchFaile(String str) {
        view.stopLoading();

    }

    @Override
    public void freezeResult(boolean isSuccess, String msg) {

        view.stopLoading();
        view.freezeResult(isSuccess,msg);
    }

    @Override
    public void unFreezeResult(boolean isSuccess, String msg) {

        view.stopLoading();
        view.unFreezeResult(isSuccess,msg);
    }
}
