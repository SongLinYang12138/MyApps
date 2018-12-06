package com.bondex.ysl.pdaapp.query;

import android.content.Context;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;
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
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class QueryStowrageModel extends BaseModel<QueryStowrageCallBack> {

    private String userId = PdaApplication.LOGINBEAN.getUserid();
    private int stoId = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

    public QueryStowrageModel(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    public void search(String traceId, String locationId, String sku) {


        String method = "inv.searchInv";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("userid", userId);
            map.put("warehouseno", stoId);//此处为仓库编号，成都为1，烟台为2这样
            map.put("traceid", traceId);//对应库位
            map.put("locationid", locationId);//对应跟踪号，库位和跟踪号不能都为空
            map.put("sku", sku);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String params = ParamUtils.getParams(map.toString(), method);


        HttpConnection.connect(params, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {



                ArrayList<QueryStowrageBean> beans = new ArrayList<>();
                Gson gson = new Gson();
                String msg = param.getMsg();
                JSONArray array = null;
                try {
                    array = new JSONArray(msg);

                    for (int i = 0; i < array.length(); ++i) {

                        JSONObject object1 = array.getJSONObject(i);
                        QueryStowrageBean bean = gson.fromJson(object1.toString(), QueryStowrageBean.class);
                        beans.add(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                resultback.searchResult(beans);

            }

            @Override
            public void httpError(String msg) {

                resultback.searchFailed(msg);
            }
        });
    }

}
