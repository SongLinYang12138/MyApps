package com.bondex.ysl.pdaapp.consigement;

import android.content.Context;
import android.util.Log;

import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

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

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {


                String no = param[1];
                String id = param[0];



                Call<String> call = HttpConnection.consigement(id,Integer.valueOf(no));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.body() == null) emitter.onNext("N");
                        else emitter.onNext(response.body());
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
            public void accept(String s) throws Exception {
                Log.i(Constant.TAG,s);
                Logger.i(" "+s);
                if(s.equals("N")) resultback.faile("连接服务器失败");
                else {
                    Gson gson = new Gson();

                    ResultBean bean = gson.fromJson(s,ResultBean.class);

                    if(bean == null) resultback.faile(s);
                    else resultback.success(bean);
                }


            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);


    }

    @Override
    protected void doLocal(String... param) {

    }
}
