package com.bondex.ysl.pdaapp.pick;

import android.content.Context;
import android.util.Log;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.bean.PickBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.interf.HtppReuquest;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public class PickModal extends BaseModel<PickBack> {

    private String userId;
    private int stoId;

    public PickModal(Context context) {
        super(context);
        userId = PdaApplication.LOGINBEAN.getUserid();
        stoId = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    public void searchCode(String code) {


        String method = "so.pickBefore";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("userid", userId);
            map.put("warehouseno", stoId);

            map.put("orderno", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String params = ParamUtils.getParams(map.toString(), method);

        HttpConnection.connect(params, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                if (param.isSuccess()) {

                    Gson gson = new Gson();

                    ArrayList<PickBean> beans = new ArrayList<>();

                    try {
                        param.setMsg(param.getMsg().replaceAll(" ", ""));
                        JSONArray array = new JSONArray(param.getMsg());


                        for (int i = 0; i < array.length(); ++i) {

                            JSONObject object = array.getJSONObject(i);

                            PickBean bean = gson.fromJson(object.toString(), PickBean.class);
                            beans.add(bean);
                            resultback.searchSuccess(beans);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        resultback.searchFaile("解析数据失败");
                    }


                } else {

                    resultback.searchFaile(param.getErrormsg());
                }

            }

            @Override
            public void httpError(String msg) {
                resultback.searchFaile(msg);
            }
        });

    }

    public void pick(String allocationDetailId) {

        String method = "so.pick";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("userid", PdaApplication.LOGINBEAN.getUserid());
            map.put("warehouseno", stoId);
            map.put("allocationdetailsid", allocationDetailId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.getParams(map.toString(), method);

        HttpConnection.connect(params, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                resultback.pickSuccess(param.getMsg());
            }

            @Override
            public void httpError(String msg) {

                resultback.pickFaile(msg);
            }
        });

    }


}
