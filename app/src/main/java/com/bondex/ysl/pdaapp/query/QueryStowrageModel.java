package com.bondex.ysl.pdaapp.query;

import android.content.Context;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
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

    public QueryStowrageModel(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    public void search(String traceId, String locationId,String sku) {

        String userId = PdaApplication.LOGINBEAN.getUserid();
        int stoId = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String method = "inv.searchInv";//方法名
                JSONObject map = new JSONObject();
                map.put("userid", userId);
                map.put("warehouseno", stoId);//此处为仓库编号，成都为1，烟台为2这样
                map.put("traceid", traceId);//对应库位
                map.put("locationid", locationId);//对应跟踪号，库位和跟踪号不能都为空
                map.put("sku", sku);



                String params = ParamUtils.getParams(map.toString(), method);

                HttpConnection.getCall(params).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.body() == null) {
                            emitter.onNext("N");
                        } else {
                            emitter.onNext(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext("N");
                    }
                });
            }
        });

        Consumer<String> observer = new Consumer<String>() {
            @Override
            public void accept(String s) {

                if ("N".equals(s)) {

                    resultback.searchFailed("连接服务器失败");
                } else {

                    try {
                        JSONObject object = new JSONObject(s);

                        boolean isSuccess = object.getBoolean("success");

                        if (isSuccess) {

                            ArrayList<QueryStowrageBean> beans = new ArrayList<>();
                            Gson gson = new Gson();
                            String msg = object.getString("msg");
                            JSONArray array = new JSONArray(msg);

                            for (int i = 0; i < array.length(); ++i) {

                                JSONObject object1 = array.getJSONObject(i);
                                QueryStowrageBean bean = gson.fromJson(object1.toString(), QueryStowrageBean.class);
                                beans.add(bean);
                            }

                            resultback.searchResult(beans);
                        } else {

                            resultback.searchFailed(object.getString("errormsg"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

}
