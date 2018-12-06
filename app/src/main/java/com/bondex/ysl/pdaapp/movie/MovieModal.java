package com.bondex.ysl.pdaapp.movie;

import android.content.Context;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.interf.HtppReuquest;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class MovieModal extends BaseModel<MovieBack> {

    public MovieModal(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    public synchronized void searchTruckNum(final String id, final int num) {

        String method = "inv.MovementBefore";//方法名
        JSONObject object = new JSONObject();
        try {
            object.put("userid", PdaApplication.LOGINBEAN.getUserid());

            object.put("warehouseno", num);//此处为仓库编号，成都为1，烟台为2这样
            object.put("traceid", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String param = object.toString();
        param = ParamUtils.getParams(param, method);


        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {


                ResultBean bean = new ResultBean();
                bean.setSuccess(param.isSuccess());
                bean.setMsg(param.getMsg());
                bean.setBusiness_param(param.getBusiness_param());
                bean.setErrormsg(param.getErrormsg());
                bean.setMethod(param.getMethod());
                resultback.traceIdExists(bean);
            }

            @Override
            public void httpError(String msg) {
                resultback.onFailed(msg);
            }
        });


    }


    public synchronized void removieStowrage(final String id, final int num, final String stowrage) {


        String method = "inv.movementID";

        JSONObject object = new JSONObject();
        try {
            object.put("locationid", stowrage);
            object.put("userid", PdaApplication.LOGINBEAN.getUserid());
            object.put("warehouseno", num);
            object.put("traceid", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String param = object.toString();
        param = ParamUtils.getParams(param, method);

        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                ResultBean bean = new ResultBean();
                bean.setSuccess(param.isSuccess());
                bean.setMsg(param.getMsg());
                bean.setBusiness_param(param.getBusiness_param());
                bean.setErrormsg(param.getErrormsg());
                bean.setMethod(param.getMethod());

                resultback.removeStowrage(bean);
            }

            @Override
            public void httpError(String msg) {
                resultback.onFailed(msg);
            }
        });

    }

}
