package com.bondex.ysl.pdaapp.inventory.movie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.PdaUtils;
import com.bondex.ysl.pdaapp.util.SystemBroadCast;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class MovieInventoryActivity extends BaseActivtiy<MoviePresenter> implements MovieView {

    @BindView(R.id.movie_tv_tracknum)
    TextView movieTvTracknum;
    @BindView(R.id.movie_et_tracknum)
    MaterialEditText movieEtTracknum;
    @BindView(R.id.movie_tv_storwagelo)
    TextView movieTvStorwagelo;
    @BindView(R.id.movie_et_storwagelo)
    MaterialEditText movieEtStorwagelo;
    @BindView(R.id.movie_bt_out)
    ButtonRectangle movieBtOut;
    @BindView(R.id.movie_bt_scan)
    ButtonRectangle movieBtScan;
    @BindView(R.id.movie_iv_tracknum)
    ImageView ivtrackNum;
    @BindView(R.id.movie_iv_storwagelo)
    ImageView ivStowragelo;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avloading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_move_inventory_layout);
        ButterKnife.bind(this);
        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showTitle(true, "单元移库");
    }

    @Override
    public MoviePresenter getPresenter() {

        presenter = new MoviePresenter(this, this);
        return null;
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()) {

            case R.id.movie_bt_out:

                finish();
                break;

            case R.id.movie_iv_storwagelo:

                removeStowrage();
                break;

            case R.id.movie_iv_tracknum:

                searchTraceId();
                break;
        }

    }


    private void searchTraceId() {
        String traceId = movieEtTracknum.getText().toString();

        if (CommonUtil.isEmpty(traceId)) {
            showShort("请输入原始单号");
            return;
        }
        traceId = traceId.replace("\n", "").trim();
        if (avloading.getVisibility() == View.VISIBLE) {
            return;
        }
        presenter.traceId(traceId);

    }

    private void removeStowrage() {

        String stowrageNum = movieEtStorwagelo.getText().toString();

        if (CommonUtil.isEmpty(stowrageNum)) {
            showShort("请输入原始库位");
            return;
        }

        stowrageNum = stowrageNum.replace("\n", stowrageNum);
        if (avloading.getVisibility() == View.VISIBLE) return;

        presenter.removeStowrage(stowrageNum);


    }

    @Override
    public void initView() {

        ivtrackNum.setOnClickListener(clickListener);
        ivStowragelo.setOnClickListener(clickListener);
        movieBtOut.setOnClickListener(clickListener);
        ivtrackNum.setOnClickListener(clickListener);
        ivStowragelo.setOnClickListener(clickListener);

        movieBtScan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        PdaUtils.startScan(MovieInventoryActivity.this);
                        break;
                    case MotionEvent.ACTION_UP:

                        PdaUtils.cancelScan(MovieInventoryActivity.this);
                        break;

                }

                return false;
            }
        });


        movieEtStorwagelo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    removeStowrage();
                }

                return false;
            }
        });

        movieEtTracknum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchTraceId();
                }
                return false;
            }
        });

    }


    @Override
    public void showLoading() {

        avloading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        avloading.setVisibility(View.GONE);

    }

    @Override
    public void onSuccess(String data) {

        showShort(data);
//        finish();

    }

    @Override
    public void faile(String msg) {

        showLong(msg);
    }

    @Override
    public void pdaResult(String result) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (CommonUtil.isEmpty(result)) {
            showShort("没有扫描到数据");
            return;
        }

        if (movieEtStorwagelo.isFocused()) {

            if (checkStorwage(result)) {
                movieEtStorwagelo.getText().clear();
                movieEtStorwagelo.setText(result);
            }
        } else if (movieEtTracknum.isFocused()) {


            if (checkTraceId(result)) movieEtTracknum.setText(result);
        } else if (CommonUtil.isEmpty(movieEtTracknum.getText().toString())) {

            if (checkTraceId(result)) movieEtTracknum.setText(result);
        } else if (CommonUtil.isEmpty(movieEtStorwagelo.getText().toString())) {


            if (checkStorwage(result)) {

                movieEtStorwagelo.getText().clear();
                movieEtStorwagelo.setText(result);
            }
        }

    }

    private boolean checkTraceId(String str) {

        String text = movieEtTracknum.getText().toString();

        if (CommonUtil.isEmpty(text)) {
            return true;
        }

        if (str.equals(text)) {
            showShort("该单号已存在");
            return false;
        }

        return true;
    }

    private boolean checkStorwage(String str) {

        String text = movieEtStorwagelo.getText().toString();

        if (CommonUtil.isEmpty(text)) return true;

        movieEtStorwagelo.getText().clear();
        if (text.equals(str)) {
            showShort("该单号已存在");
            return false;
        }
        return true;

    }


    @Override
    public void toStorLocaltion() {

        movieEtStorwagelo.setFocusable(true);
        movieEtStorwagelo.setFocusableInTouchMode(true);
        movieEtStorwagelo.requestFocus();
        Log.i(Constant.TAG, "获取焦点");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}
