package com.bondex.ysl.pdaapp.movie;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.ResultBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.PdaUtils;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.gc.materialdesign.views.ButtonRectangle;
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
    @BindView(R.id.movie_tv_originallo)
    TextView tvOrignal;
    @BindView(R.id.movie_et_storwagelo)
    MaterialEditText movieEtStorwagelo;

    @BindView(R.id.movie_bt_scan)
    ButtonRectangle movieBtScan;
    @BindView(R.id.movie_iv_tracknum)
    ImageView ivtrackNum;


    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avloading;
    @BindView(R.id.movie_tv_success)
    TextView tvSuccess;
    @BindView(R.id.movie_btclear)
    ButtonRectangle btClear;

    private ColorStateList select, normal;


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

            case R.id.movie_bt_scan:


                removeStowrage();
                break;

            case R.id.movie_iv_tracknum:

                searchTraceId();
                break;
            case R.id.movie_btclear:

                showSuccess(View.GONE, null);
                if (movieEtStorwagelo != null) movieEtStorwagelo.getText().clear();
                if (movieEtTracknum != null) movieEtTracknum.getText().clear();
                tvOrignal.setText("原始库位:  ");
                break;
        }

    }


    /**
     * 查询原始单号
     */
    private void searchTraceId() {

        String traceId = movieEtTracknum.getText().toString();

        if (CommonUtil.isEmpty(traceId)) {
//            showShort(this,"请输入原始单号");
            return;
        }
        showSuccess(View.GONE, null);
        traceId = traceId.replace("\n", "").trim();
        if (avloading.getVisibility() == View.VISIBLE) {
            return;
        }

        presenter.traceId(traceId);
    }

    /**
     * 查询原始库位
     */
    private void removeStowrage() {

        String stowrageNum = movieEtStorwagelo.getText().toString();

        if (CommonUtil.isEmpty(stowrageNum)) {
            return;
        }


        stowrageNum = stowrageNum.replace("\n", stowrageNum);
        if (avloading.getVisibility() == View.VISIBLE) return;
        presenter.removeStowrage(stowrageNum);


    }

    @Override
    public void initView() {

        ivtrackNum.setOnClickListener(clickListener);
        ivtrackNum.setOnClickListener(clickListener);
        movieBtScan.setOnClickListener(clickListener);
        btClear.setOnClickListener(clickListener);

        select = getResources().getColorStateList(R.color.colorPrimary);
        normal = getResources().getColorStateList(R.color.text_gray);

        if (Constant.SOFT_INPUTMOd) {

            hideSoftInputMethod(movieEtTracknum);
            hideSoftInputMethod(movieEtStorwagelo);
        }

        movieEtStorwagelo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    removeStowrage();
                    return true;
                }

                return false;
            }
        });
        movieEtStorwagelo.setOnClickListener(selectAll);

        movieEtTracknum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    searchTraceId();
                    return true;
                }
                return false;
            }
        });
        movieEtTracknum.setOnClickListener(selectAll);

    }


    @Override
    public void showLoading() {

        avloading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        avloading.setVisibility(View.GONE);
    }

    /**
     * 移除成功
     */
    @Override
    public void onSuccess(String data) {

        showShort(this,data);
        showSuccess(View.VISIBLE, data);
    }

    /**
     * 移除成功或失败后显示原因
     */
    private void showSuccess(int vise, String msg) {

        tvSuccess.setVisibility(vise);
        if (CommonUtil.isNotEmpty(msg)) tvSuccess.setText(msg);
    }

    @Override
    public void faile(String msg) {

        showSuccess(View.VISIBLE, msg);
        showLong(this,msg);
        errorSound();
    }

    @Override
    public void pdaResult(String result) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (CommonUtil.isEmpty(result)) {
            return;
        }

        if (movieEtStorwagelo.isFocused()) {

            boolean flag = checkStorwage(result);
            if (flag) {
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

            return false;
        }
        return true;
    }

    private boolean checkStorwage(String str) {

        String text = movieEtStorwagelo.getText().toString();

        if (CommonUtil.isEmpty(text)) return true;

        movieEtStorwagelo.getText().clear();
        if (text.equals(str)) {

            return false;
        }
        return true;

    }


    @Override
    public void toStorLocaltion() {

        movieEtStorwagelo.setFocusable(true);
        movieEtStorwagelo.setFocusableInTouchMode(true);
        movieEtStorwagelo.requestFocus();
        correctSound();
        Log.i(Constant.TAG, "获取焦点");
    }

    @SuppressLint("ResourceType")
    @Override
    public void setBtBack(boolean isClick, ResultBean bean) {


        if (isClick) {

            correctSound();
            movieBtScan.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            tvOrignal.setText("原始库位:  " + bean.getMsg());
        } else {

            errorSound();
            movieBtScan.setBackground(new ColorDrawable(getResources().getColor(R.color.metal_gray)));
        }
        movieBtScan.setClickable(isClick);
    }

    @Override
    public void showErrorSound() {
        errorSound();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}
