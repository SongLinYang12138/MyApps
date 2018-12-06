package com.bondex.ysl.pdaapp.receive.standand;

import android.content.Context;
import android.util.Log;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.interf.HtppReuquest;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
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
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class StandardModal<T> extends BaseModel<StandardCallback> {

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

        String method = "asn.receiveASN";//方法名
        JSONObject map = new JSONObject();
        try {
            map.put("warehouseno", stoId);//仓库ID
            map.put("asnno", code);
            map.put("userid", userId);//当前登录用户ID
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String business_param = map.toString();


        business_param = ParamUtils.getParams(business_param, method);


        HttpConnection.connect(business_param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                Gson gson = new Gson();
//                {"success":false,"msg":null,"business_param":null,"errormsg":"无效的ASNNO","method":null}
                ReceiveStandardCodeBean bean = gson.fromJson(param.getMsg(), ReceiveStandardCodeBean.class);
                if(bean == null){
                    resultback.searchFaile("连接服务器失败");
                    return;
                }
                resultback.searchBack(bean);
            }

            @Override
            public void httpError(String msg) {

                resultback.searchFaile(msg);
            }
        });
    }


    public void searchProduct(String ano, String productId) {

        String method = "asn.receiveSKU";//方法名
        JSONObject map = new JSONObject();
        try {

            map.put("userid", userId);//当前登录用户
            map.put("warehouseno", stoId);
            map.put("asnno", ano.trim());//第一步返回的asnno
            map.put("sku", productId.trim());//产品文本框里获取
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String params = ParamUtils.getParams(map.toString(),method);
        HttpConnection.connect(params, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                Gson gson = new Gson();

                ReceiveStandardCodeBean bean = gson.fromJson(param.getMsg(), ReceiveStandardCodeBean.class);
                Logger.i("产品返回:  " + bean.getCubic() + "   " + bean.getPrice() + "  receiveQty" + bean.getReceivedqty());
                resultback.resultProduct(bean);
            }

            @Override
            public void httpError(String msg) {

                resultback.productFailed(msg);
            }
        });

    }

    public void receiving(String asno, int asnlineno, int receivedQty, String receivingLocation, String holdRejectCode, double weight, double fweight, double volume, double price) {

        String method = "asn.receive";//方法名

        JSONObject map = new JSONObject();
        try {
            map.put("userid", userId);

            map.put("warehouseno", stoId);
            map.put("asnno", asno.trim());//上一步返回
            map.put("asnlineno", asnlineno);//上一步返回值
            map.put("ReceivedQty", receivedQty);//取自收货数量文本框
            map.put("ReceivingLocation", receivingLocation.trim());//取自收货库位文本框
            map.put("HoldRejectCode", holdRejectCode.trim());//取自冻结代码选择框，当前有两个选项 {“OK”:”正常”,”DJ”:”待检”}，”正常”的默认选中
            map.put("HoldRejectCode", holdRejectCode.trim());//取自冻结代码选择框，当前有两个选项 {“OK”:”正常”,”DJ”:”待检”}，”正常”的默认选中
            map.put("TotalCubic", volume);
            map.put("TotalGrossWeight", fweight);
            map.put("TotalNetWeight", weight);
            map.put("TotalPrice", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String param = ParamUtils.getParams(map.toString(), method);

        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {
                resultback.receiveSuccess(param.getMsg());

            }

            @Override
            public void httpError(String msg) {

                resultback.receiveFailed(msg);
            }
        });



    }


}
