package com.bondex.ysl.pdaapp.main;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bondex.ysl.pdaapp.base.BaseView;
import com.bondex.ysl.pdaapp.bean.UpdateBean;

import java.util.ArrayList;

public interface MainView extends BaseView {


    void setBnnerrs(CBViewHolderCreator holderCreator, ArrayList<String> localImages);

    void listAdapter(MainAdapter adapter);

    void showUpdate(UpdateBean bean);
    void installApk(String path);



}
