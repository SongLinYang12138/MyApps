package com.bondex.ysl.pdaapp.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.adapter.MainAdapter;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.MainBean;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivtiy<MainPresenter> implements MainView {


    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.tv_stowrage)
    TextView tvSorage;
    @BindView(R.id.user_id)
    TextView tvUserId;
    @BindView(R.id.menu_bt_loginout)
    ButtonRectangle btLoginout;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.check_update)
    TextView checkUpdate;

    @BindView(R.id.main_panel)
    SlidingPaneLayout mainPanel;
    @BindView(R.id.main_banner)
    ConvenientBanner banner;
    @BindView(R.id.main_listvew)
    ListView listView;


    private ColorStateList normal, select;


    private String subSystemName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showLeft(true, R.mipmap.menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isOpen = mainPanel.isOpen() ? mainPanel.closePane() : mainPanel.openPane();
            }
        });
        showRight(false, 0, null);
        showTitle(true,"海程邦达");

    }

    @Override
    public MainPresenter getPresenter() {

        if (presenter == null) {
            subSystemName = SharedPreferecneUtils.getValue(this,Constant.STORWAGEPAGE,Constant.SUBSYSTEM_NAME);
            presenter = new MainPresenter(this, this);

        }
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("开启");
    banner.startTurning();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("关闭");
    banner.stopTurning();
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()){

            case R.id.menu_bt_loginout:

                break;
        }

    }

    @Override
    public void initView() {


        normal = getResources().getColorStateList(R.color.bottom_normal);
        select = getResources().getColorStateList(R.color.colorPrimary);
        btLoginout.setOnClickListener(clickListener);

        tvUserId.setText("用户ID:"+PdaApplication.LOGINBEAN.getUserid());
        userName.setText("用户名: "+PdaApplication.LOGINBEAN.getUsername());
        tvSorage.setText(""+subSystemName);




        ArrayList<MainBean> mainBeans = new ArrayList<>();
        mainBeans.add(new MainBean("入库","入库",R.mipmap.attend));
        mainBeans.add(new MainBean("出库","出库",R.mipmap.ticket));
        mainBeans.add(new MainBean("库存","库存",R.mipmap.warehouse));

        MainAdapter adapter = new MainAdapter(this,mainBeans);
        listView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void faile(String msg) {

    }

    @Override
    public void setBnnerrs(CBViewHolderCreator holderCreator,ArrayList<Integer> localImages) {

        banner.setPages(holderCreator,localImages).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Logger.i("click banner: "+position);
            }
        });


    }
}
