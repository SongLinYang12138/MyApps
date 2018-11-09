package com.bondex.ysl.pdaapp.main;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.UpdateBean;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModal extends BaseModel<MainBack> {

    public MainModal(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {

    }


    public void checkVersion() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpConnection.getVersion();

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.body() == null){
                            emitter.onNext("N");
                        }else {
                            emitter.onNext(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext("N");
                       t.getMessage();
                    }
                });


            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                if(s.equals("N")){

                }else {

                    Gson gson = new Gson();

                    UpdateBean bean = gson.fromJson(s,UpdateBean.class);
                    resultback.checkUpdate(bean);
                }
                Logger.i("getVersion  " +s);

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }
}
