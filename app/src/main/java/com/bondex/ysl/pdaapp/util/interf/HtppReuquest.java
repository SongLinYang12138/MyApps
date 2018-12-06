package com.bondex.ysl.pdaapp.util.interf;

import com.bondex.ysl.pdaapp.bean.HttpRequestParam;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public interface HtppReuquest {

    void httpSuccess(HttpRequestParam param);
    void httpError(String msg);
}
