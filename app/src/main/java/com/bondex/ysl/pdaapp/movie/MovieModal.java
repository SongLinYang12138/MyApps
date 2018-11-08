package com.bondex.ysl.pdaapp.movie;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.ResultBean;
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

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpConnection.traceId(id, num);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Logger.i("  " + response.body());
                        if (response.body() != null) {

                            emitter.onNext(response.body());

                        } else {
                            emitter.onNext("N");

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext("N");
                        Logger.i("" + t.getMessage());
                    }
                });


            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {


                if (s.equals("N")) {

                    resultback.onFailed("连接服务器失败");
                } else {

                    Gson gson = new Gson();

                    ResultBean bean = gson.fromJson(s, ResultBean.class);
                    resultback.traceIdExists(bean);
                }


            }
        };
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer);

    }


    public synchronized void removieStowrage(final String id, final int num) {


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpConnection.removeStowrage(id, num);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.body() == null) emitter.onNext("N");
                        else emitter.onNext(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Logger.i(t.getMessage());
                        emitter.onNext("N");
                    }
                });


            }
        });

        final Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if(s.equals("N"))
                    resultback.onFailed("连接服务器失败");
                else {

                    Gson gson = new Gson();

                    ResultBean bean = gson.fromJson(s,ResultBean.class);
                    resultback.removeStowrage(bean);
                }

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);


    }

}
