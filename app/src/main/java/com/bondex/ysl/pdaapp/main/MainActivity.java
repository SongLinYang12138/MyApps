package com.bondex.ysl.pdaapp.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.UpdateBean;
import com.bondex.ysl.pdaapp.login.LoginActivity;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.PdaUtils;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import com.bondex.ysl.pdaapp.util.ToastUtils;
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
    @BindView(R.id.menu_bt_porwer)
    SwitchCompat swPower;

    @BindView(R.id.main_panel)
    SlidingPaneLayout mainPanel;
    @BindView(R.id.main_banner)
    ConvenientBanner banner;
    @BindView(R.id.main_listvew)
    ListView listView;


    private ColorStateList normal, select;
    private String subSystemName;

    private long lastback = 0;

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
        showTitle(true, "仓库PDA");

    }

    @Override
    public MainPresenter getPresenter() {

        if (presenter == null) {
            subSystemName = SharedPreferecneUtils.getValue(this, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NAME);
            presenter = new MainPresenter(this, this);

        }
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();

        banner.startTurning();
    }

    @Override
    protected void onPause() {
        super.onPause();

        banner.stopTurning();
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()) {

            case R.id.menu_bt_loginout:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra(Constant.LOGIN_OUT, true);
                startBaseActivity(intent);
                finish();
                break;
        }

    }

    @Override
    public void initView() {


        normal = getResources().getColorStateList(R.color.bottom_normal);
        select = getResources().getColorStateList(R.color.colorPrimary);
        btLoginout.setOnClickListener(clickListener);

        tvUserId.setText("用户ID:" + PdaApplication.LOGINBEAN.getUserid());
        userName.setText("用户名: " + PdaApplication.LOGINBEAN.getUsername());
        tvSorage.setText("" + subSystemName);


        version.setText(CommonUtil.getVersionName(this));
        swPower.setChecked(SharedPreferecneUtils.getBoolean(this, Constant.STORWAGEPAGE, Constant.POWER_STATE));
        swPower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                PdaUtils.turnOnOffPda(isChecked, PdaApplication.context);
                SharedPreferecneUtils.saveBoolean(MainActivity.this, Constant.STORWAGEPAGE, Constant.POWER_STATE, isChecked);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - lastback > 2000) {

                ToastUtils.showToast("请再按一次退出");
                lastback = System.currentTimeMillis();
            } else {

                SharedPreferecneUtils.saveValue(this, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NAME, "");
                Intent intent = new Intent(this,LoginActivity.class);
                intent.putExtra(Constant.LOGIN_OUT,true);
                startBaseActivity(intent);
                finish();
            }
            return false;
        }


        return super.onKeyDown(keyCode, event);
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
    public void setBnnerrs(CBViewHolderCreator holderCreator, ArrayList<String> localImages) {

        banner.setPages(holderCreator, localImages).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Logger.i("click banner: " + position);
            }
        });
    }

    @Override
    public void listAdapter(MainAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void showUpdate(UpdateBean bean) {

    }
}
