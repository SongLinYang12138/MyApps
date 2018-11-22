package com.bondex.ysl.pdaapp.receive.standand;

import android.content.Context;
import android.util.Log;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
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
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class StandardModal extends BaseModel<StandardCallback> {


    private int stoId;
    private String userId;

    public StandardModal(Context context) {
        super(context);


        stoId = SharedPreferecneUtils.getInteger(context, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NO);
        userId = PdaApplication.LOGINBEAN.getUserid();
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    protected void searchCode(String code) {


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String method = "asn.receiveASN";//方法名
                JSONObject map = new JSONObject();
                map.put("userid", userId);//当前登录用户ID
                map.put("warehouseno", stoId);//仓库ID
                map.put("asnno", code);
                String business_param = map.toString();

                business_param = ParamUtils.getParams(business_param, method);
                Call<String> call = HttpConnection.getCall(business_param);

                call.enqueue(new Callback<String>() {
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
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

                if (s.equals("N")) {
                    resultback.searchFaile("连接服务器失败");
                } else {

                    try {
                        JSONObject object = new JSONObject(s);
                        boolean isSuccess = object.getBoolean("success");

                        if (isSuccess) {

                            Gson gson = new Gson();

                            ReceiveStandardCodeBean bean = gson.fromJson(object.getString("msg"), ReceiveStandardCodeBean.class);
                            resultback.searchBack(bean);
                        } else {

                            String error = object.getString("errormsg");
                            resultback.searchFaile(error);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        resultback.searchFaile("连接服务器失败");
                    }


                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);


    }


    public void searchProduct(String ano, String productId) {


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String method = "asn.receiveSKU";//方法名
                JSONObject map = new JSONObject();
                map.put("userid", userId);//当前登录用户
                map.put("warehouseno", stoId);
                map.put("asnno", ano);//第一步返回的asnno
                map.put("sku", productId);//产品文本框里获取

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
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

                if (s.equals("N")) {

                    resultback.productFailed("连接服务器失败");
                } else {

                    try {
                        JSONObject object = new JSONObject(s);

                        boolean isSuccess = object.getBoolean("success");

                        if (isSuccess) {

                            Gson gson = new Gson();

                            ReceiveStandardCodeBean bean = gson.fromJson(object.getString("msg"), ReceiveStandardCodeBean.class);

                            resultback.resultProduct(bean);
                        } else {

                            resultback.productFailed(object.getString("errormsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        resultback.productFailed("连接服务器失败");

                    }


                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    public void receiving(String asno, int asnlineno, int receivedQty, String receivingLocation, String holdRejectCode) {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {


                String method = "asn.receive";//方法名

                JSONObject map = new JSONObject();
                map.put("userid", userId);
                map.put("warehouseno", stoId);
                map.put("asnno", asno);//上一步返回
                map.put("asnlineno", asnlineno);//上一步返回值
                map.put("ReceivedQty", receivedQty);//取自收货数量文本框
                map.put("ReceivingLocation", receivingLocation);//取自收货库位文本框
                map.put("HoldRejectCode", holdRejectCode);//取自冻结代码选择框，当前有两个选项 {“OK”:”正常”,”DJ”:”待检”}，”正常”的默认选中

                String param = ParamUtils.getParams(map.toString(), method);

                HttpConnection.getCall(param).enqueue(new Callback<String>() {
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
            public void accept(String s) throws Exception {
//                {"success":true,"msg":"收货成功","business_param":null,"errormsg":null,"method":null}
                if ("N".equals(s)) {
                    resultback.receiveFailed("连接服务器失败");
                } else {

                    JSONObject object = new JSONObject(s);

                    boolean isSuccess = object.getBoolean("success");

                    if (isSuccess) {

                        resultback.receiveSuccess(object.getString("msg"));
                    } else {

                        resultback.receiveFailed(object.getString("errormsg"));
                    }

                }


            }

        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }


}
