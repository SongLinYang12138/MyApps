package com.bondex.ysl.pdaapp.main;

import android.widget.ArrayAdapter;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bondex.ysl.pdaapp.base.BaseView;

import java.util.ArrayList;

public interface MainView extends BaseView {


    void setBnnerrs(CBViewHolderCreator holderCreator, ArrayList<Integer> localImages);



}
