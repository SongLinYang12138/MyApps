package com.bondex.ysl.pdaapp.base;

public interface BaseView {

    void initView();
    void showLoading();
    void stopLoading();
    void onSuccess(String data);
    void faile(String msg);
}
