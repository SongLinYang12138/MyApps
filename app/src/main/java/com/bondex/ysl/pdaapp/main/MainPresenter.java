package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MainBean;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.MenuBean;

import java.util.ArrayList;

public class MainPresenter extends BasePresnter<MainView, MainModal> implements MainBack {

    private ArrayList<Integer> localImages = new ArrayList<>();


    public MainPresenter(MainView view, final Context context) {
        super(view, context);

        setBainner();
       setListAdapter();
    }

    private void setBainner(){


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


    private void setListAdapter(){



        ArrayList<MainBean> mainBeans = new ArrayList<>();

        ArrayList<MenuBean> inList = new ArrayList<>();
        inList.add(new MenuBean("标准收货","standardReceive"));
        inList.add(new MenuBean("混托盘收货","confusionReceive"));

        ArrayList<MenuBean> outList = new ArrayList<>();

        outList.add(new MenuBean("按订单发货","orderConsignment"));
        outList.add(new MenuBean("订单拣货","orderPick"));

        ArrayList<MenuBean> movieList = new ArrayList<>();
        movieList.add(new MenuBean("单元移库","unitmovie"));

        mainBeans.add(new MainBean("入库",inList));
        mainBeans.add(new MainBean("出库",outList));
        mainBeans.add(new MainBean("库存",movieList));



        MainAdapter adapter = new MainAdapter(context,mainBeans);
      view.listAdapter(adapter);
    }


    @Override
    public MainModal getModal() {
        return null;
    }

    @Override
    public void initData() {

    }

}
