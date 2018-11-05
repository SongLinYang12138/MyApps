package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.adapter.LocalImageHolderView;
import com.bondex.ysl.pdaapp.base.BasePresnter;

import java.util.ArrayList;

public class MainPresenter extends BasePresnter<MainView, MainModal> implements MainBack {

    private ArrayList<Integer> localImages = new ArrayList<>();


    public MainPresenter(MainView view, final Context context) {
        super(view, context);

        localImages.add(R.mipmap.banner_1);
        localImages.add(R.mipmap.banner_2);

        CBViewHolderCreator holderCreator = new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder(View itemView) {
                return  new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.image_layout;
            }
        };

        view.setBnnerrs(holderCreator,localImages);
    }



    @Override
    public MainModal getModal() {
        return null;
    }

    @Override
    public void initData() {

    }


    public void choosePage() {


    }
}
