package com.bondex.ysl.pdaapp.login;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.util.provider.LoginProvider;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.BaseBean;
import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;
import com.bondex.ysl.pdaapp.util.net.HttpConnection;
import com.bondex.ysl.pdaapp.util.CommonUtil;
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


public class LoginModal extends BaseModel<LoginBack> {


    public LoginModal(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

        final String name = param[0];
        final String password = param[1];


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {

                HttpConnection.login(name, password).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.body() != null) {

                            emitter.onNext(response.body());
                        } else {
                            emitter.onNext("N");
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext(t.getMessage());
                        resultback.loginFailed("");
                    }
                });


            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i(" " + s);


                try {

                    Gson gson = new Gson();

                    BaseBean bean = gson.fromJson(s, BaseBean.class);
                    if (bean.isSuccess()) {

                        LoginBean loginBean = gson.fromJson(bean.getMsg(), LoginBean.class);
                        loginBean.setPassword(password);
                        loginBean.setLogined(true);
                        saveUserBean(loginBean);
                        resultback.loginSuccess("登录成功");
                        PdaApplication.LOGINBEAN = loginBean;
                    } else {

                        if (CommonUtil.isNotEmpty(bean.getErrormsg())) {
                            resultback.loginFailed(bean.getErrormsg());
                        } else {
                            resultback.loginFailed("获取失败");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resultback.loginFailed("服务器错误");
                }
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    @Override
    protected void doLocal(String... param) {

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(LoginProvider.CONTENT_URI, LoginBean.COLUMNS, null, null, null);

        LoginBean loginBean = LoginBean.toLoginBean(cursor);

        if (loginBean != null)
            resultback.localData(loginBean.getUserid(), loginBean.getPassword(), loginBean.isLogined());

        PdaApplication.LOGINBEAN = loginBean;
    }

    private void saveUserBean(LoginBean bean) {

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(LoginProvider.CONTENT_URI, LoginBean.COLUMNS, null, null, null);

        boolean isInsert = false;

        isInsert = isInsert || cursor == null;
        isInsert = isInsert || cursor.getCount() == 0;

        LoginBean oldBean = null;
        if (cursor != null) {
            oldBean = LoginBean.toLoginBean(cursor);
            cursor.close();
        }


        ContentValues values = bean.toContentValue();

//        if (isInsert || oldBean == null) {

        resolver.delete(LoginProvider.CONTENT_URI, null, null);
        resolver.insert(LoginProvider.CONTENT_URI, values);
//        } else {
//
//            resolver.update(LoginProvider.CONTENT_URI, values, "where " + LoginBean.USER_ID + " = ?", new String[]{oldBean.getUserid()});
//        }


    }


}
