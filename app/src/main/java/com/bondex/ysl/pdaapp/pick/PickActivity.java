package com.bondex.ysl.pdaapp.pick;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.bean.PickBean;
import com.bondex.ysl.pdaapp.ui.IconText;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickActivity extends BaseActivtiy<PickPresennter> implements PickView {

    @BindView(R.id.pick_et_ordercode)
    EditText pickEtOrdercode;
    @BindView(R.id.pick_it_ordercode)
    IconText pickItOrdercode;
    @BindView(R.id.pick_ll_search)
    LinearLayout pickLlSearch;
    @BindView(R.id.pick_recycler_view)
    RecyclerView pickRecyclerView;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView loading;
    @BindView(R.id.pick_tv_total)
    TextView tvTotal;
    private int totalSize = 0;

    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick);
        ButterKnife.bind(this);

        showRight(true, R.string.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickLlSearch.setVisibility(pickLlSearch.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                pickEtOrdercode.requestFocus();
            }
        });

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "订单拣货");


    }

    @Override
    public PickPresennter getPresenter() {

        if (presenter == null) {

            presenter = new PickPresennter(this, this);
        }

        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

        switch (v.getId()) {

            case R.id.pick_it_ordercode:

                searchCode();
                break;
        }

    }

    @Override
    public void initView() {

        pickItOrdercode.setOnClickListener(clickListener);

        pickEtOrdercode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    searchCode();
                    pickEtOrdercode.setText(pickEtOrdercode.getText().toString());
                    pickEtOrdercode.selectAll();
                    return true;
                }

                return false;
            }
        });

        if (Constant.SOFT_INPUTMOd) {
            hideSoftInputMethod(pickEtOrdercode);
        }


        pickEtOrdercode.setOnClickListener(selectAll);

        manager = new LinearLayoutManager(this) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        pickRecyclerView.setLayoutManager(manager);


    }

    private void searchCode() {

        String code = pickEtOrdercode.getText().toString();

        if (CommonUtil.isEmpty(code)) {
            return;
        }
        presenter.search(code);
    }

    @Override
    public void showLoading() {

        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccess(String data) {
        pickLlSearch.setVisibility(View.GONE);
        showShort(this, data);
    }

    @Override
    public void faile(String msg) {

        showShort(this, msg);
    }

    @Override
    public void setAdapter(PickAdapter adapter) {

        pickRecyclerView.setNestedScrollingEnabled(false);
        pickRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setTotalSize(int size, int position) {

        totalSize = size;
        position += 1;
        tvTotal.setText("共 " + size + "条记录，当前位于第" + position + "条");
    }

    @Override
    public void last(int position) {

        position -= 1;

        if (position < 0) {
            showShort(this, "没有上一页了");
            return;
        }
        pickRecyclerView.scrollToPosition(position);
        setTotalSize(totalSize, position);
    }

    @Override
    public void next(int position) {

        position += 1;
        if (position >= totalSize) {
            showShort(this, "当前没有下一页了");
            return;
        }

        pickRecyclerView.scrollToPosition(position);

        setTotalSize(totalSize, position);
    }

    private AlertDialog alertDialog;
    private AlertDialog.Builder alertBuilder;

    @Override
    public void showAlert(String msg, PickBean bean) {


        if (alertDialog == null) {

            alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("请注意: ");
//            alertBuilder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    presenter.pickModal(bean.getAllocationdetailsid());
//                }
//            });
            alertBuilder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    alertDialog.dismiss();
                }
            });

            alertBuilder.setMessage(msg);
            alertDialog = alertBuilder.create();
            alertDialog.show();
        } else {

            if (alertDialog.isShowing()) {
                return;
            }

            alertBuilder.setMessage(msg);
            alertDialog = alertBuilder.create();
            alertDialog.show();
        }


    }


}
