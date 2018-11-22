package com.bondex.ysl.pdaapp.query;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.databinding.DialogQueryStowrageLayoutBinding;
import com.bondex.ysl.pdaapp.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class QueryStowrageActivity extends BaseActivtiy<QueryStowragePresenter> implements QueryStowrageView {

    @BindView(R.id.querysto_recyclerview)
    RecyclerView querystoRecyclerview;
    private DialogQueryStowrageLayoutBinding dialogBinding;
    private Dialog searchDailog;
    private boolean isSearchCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stowrage_query_layout);
        ButterKnife.bind(this);

        showLeft(true, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle(true, "库存查询");

        showRight(true, R.string.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSearDialog();
            }
        });
    }

    @Override
    public QueryStowragePresenter getPresenter() {

        if (presenter == null) {
            presenter = new QueryStowragePresenter(this, this);
        }
        return presenter;
    }


    @Override
    public void noDoubleClick(View v) {

    }

    @Override
    public void initView() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        querystoRecyclerview.setLayoutManager(manager);
    }

    @Override
    public void showLoading() {

        if (dialogBinding != null) {
            dialogBinding.avLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopLoading() {

        if (dialogBinding != null) {
            dialogBinding.avLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void faile(String msg) {

    }

    @Override
    public void showSearDialog() {

        if (searchDailog == null) {

            searchDailog = new Dialog(this, R.style.dialog);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_query_stowrage_layout, null, false);
            dialogBinding = DataBindingUtil.bind(view);
            searchDailog.setContentView(dialogBinding.getRoot());

            WindowManager.LayoutParams lp = searchDailog.getWindow().getAttributes();

            lp.width = CommonUtil.getScreentW(this) - 100;
            lp.height = CommonUtil.getScreenH(this) * 2 / 3;
            lp.gravity = Gravity.CENTER;

            dialogBinding.querystoEtStolocation.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        dialogBinding.querystoEtTrckcode.requestFocus();
                        return true;
                    }
                    return false;
                }
            });

            dialogBinding.querystoEtTrckcode.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        if (dialogBinding.querystoEtTrckcode.getText().length() > 0)
                            dialogBinding.querystoEtSku.requestFocus();
                        return true;
                    }

                    return false;
                }
            });
            dialogBinding.querystoEtSku.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        return true;
                    }

                    return false;
                }
            });


            dialogBinding.querystoBtClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogBinding.querystoEtStolocation.getText().clear();
                    dialogBinding.querystoEtTrckcode.getText().clear();
                    dialogBinding.querystoEtSku.getText().clear();
                }
            });
            dialogBinding.querystoBtConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String traceId = dialogBinding.querystoEtTrckcode.getText().toString();
                    String stoId = dialogBinding.querystoEtStolocation.getText().toString();
                    String sku = dialogBinding.querystoEtSku.getText().toString();


                    if (CommonUtil.isEmpty(stoId) && CommonUtil.isEmpty(traceId) && CommonUtil.isEmpty(sku)) {
                        showShort("请输入一项进行查询");
                        return;
                    }

                    presenter.search(stoId, traceId, sku);
                }
            });


            searchDailog.getWindow().setAttributes(lp);
            searchDailog.setCanceledOnTouchOutside(false);

            searchDailog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    if (!isSearchCode) {
                        finish();
                    }
                }
            });
        }
        searchDailog.show();

    }

    @Override
    public void resultSuccess(QueryStowrageAdapter adapter) {

        isSearchCode = true;
        searchDailog.dismiss();
        if (adapter != null) querystoRecyclerview.setAdapter(adapter);

    }

    @Override
    public void resultFailed(String msg) {

        showShort(msg);

    }

}
