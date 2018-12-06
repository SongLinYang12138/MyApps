package com.bondex.ysl.pdaapp.consigement;

import android.content.Context;
import android.util.Log;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.Constant;
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
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class ConsigeMentModel extends BaseModel<ConsigeMentBack> {


    public ConsigeMentModel(Context context) {
        super(context);
    }

    @Override
    public void doNet(final String... param) {


                String no = param[1];
                String id = param[0];

                String method = "so.shipment";//方法名

                JSONObject map = new JSONObject();

                try {
                    map.put("warehouseno", Integer.valueOf(no));//当前登录用户选择的仓库ID
                    map.put("Action", "Ship");//固定值
                    map.put("ProcessBy", "OrderNo");//固定值
                    map.put("userid", PdaApplication.LOGINBEAN.getUserid());//系统当前登录人ID
                    map.put("OrderNO", id);//第一步扫描的发运单号

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String params = map.toString();
                params = ParamUtils.getParams(params, method);

                HttpConnection.connect(params, new HtppReuquest() {
                    @Override
                    public void httpSuccess(HttpRequestParam param) {


                        ResultBean bean = new ResultBean();
                        bean.setSuccess(param.isSuccess());
                        bean.setMsg(param.getMsg());
                        bean.setBusiness_param(param.getBusiness_param());
                        bean.setErrormsg(param.getErrormsg());
                        bean.setMethod(param.getMethod());

                         resultback.success(bean);
                    }

                    @Override
                    public void httpError(String msg) {
                        resultback.faile(msg);
                    }
                });



    }

    @Override
    protected void doLocal(String... param) {

    }

}
