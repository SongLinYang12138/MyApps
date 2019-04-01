package com.bondex.ysl.pdaapp.freeze;

import android.content.Context;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.FreezeSearchBean;
import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.util.interf.HtppReuquest;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.google.gson.Gson;

/**
 * date: 2019/4/1
 * Author: ysl
 * description:
 */
public class FreezeTrayModle extends BaseModel<FreezeTrayBack> {


    public FreezeTrayModle(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }

    public void searCode(String param) {

        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                if (param.isSuccess()) {

                    Gson gson = new Gson();

                    FreezeSearchBean bean = gson.fromJson(param.getMsg(), FreezeSearchBean.class);
                    resultback.searchSuccess(bean);
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

    public void freeze(String param) {

        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                if (param.isSuccess()) {
                    resultback.freezeResult(true, "冻结成功");
                } else {
                    resultback.freezeResult(false, param.getErrormsg() + param.getMsg());
                }

            }

            @Override
            public void httpError(String msg) {
                resultback.freezeResult(false, msg);
            }
        });

    }

    public void unFreeze(String param) {

        HttpConnection.connect(param, new HtppReuquest() {
            @Override
            public void httpSuccess(HttpRequestParam param) {

                resultback.unFreezeResult(param.isSuccess(), param.getMsg() + param.getErrormsg());

            }

            @Override
            public void httpError(String msg) {

                resultback.unFreezeResult(false, msg);
            }
        });


    }


}
