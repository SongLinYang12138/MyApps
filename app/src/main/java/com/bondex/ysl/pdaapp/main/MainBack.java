package com.bondex.ysl.pdaapp.main;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.UpdateBean;

public interface MainBack extends BaseBack {

    void checkUpdate(UpdateBean bean);

}
