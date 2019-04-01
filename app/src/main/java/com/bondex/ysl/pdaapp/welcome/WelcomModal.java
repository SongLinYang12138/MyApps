package com.bondex.ysl.pdaapp.welcome;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;
import com.bondex.ysl.pdaapp.util.provider.LoginProvider;

/**
 * date: 2018/12/7
 * Author: ysl
 * description:
 */
public class WelcomModal extends BaseModel<WelcomeCallBack> {

    public WelcomModal(Context context) {
        super(context);
    }

    @Override
    public void doNet(String... param) {

    }

    @Override
    protected void doLocal(String... param) {


        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(LoginProvider.CONTENT_URI, LoginBean.COLUMNS, null, null, null);


        LoginBean loginBean = LoginBean.toLoginBean(cursor);

        if (loginBean != null)
            resultback.localData(loginBean.getUserid(), loginBean.getPassword(), loginBean.isLogined());
        else {
            resultback.failed();
        }

        PdaApplication.LOGINBEAN = loginBean;

    }
}
