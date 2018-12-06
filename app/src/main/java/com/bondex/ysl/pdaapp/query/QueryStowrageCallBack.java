package com.bondex.ysl.pdaapp.query;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;

import java.util.ArrayList;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public interface QueryStowrageCallBack extends BaseBack {


  void   searchResult(ArrayList<QueryStowrageBean> list);

  void searchFailed(String msg);
}
