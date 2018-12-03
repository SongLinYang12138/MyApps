package com.bondex.ysl.pdaapp.movie;

import com.bondex.ysl.pdaapp.base.BaseView;
import com.bondex.ysl.pdaapp.bean.ResultBean;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public interface MovieView  extends BaseView {

    void pdaResult(String result);

    void toStorLocaltion();

    void setBtBack(boolean isClick, ResultBean bean);

    void showErrorSound();

}
