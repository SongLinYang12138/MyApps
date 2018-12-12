package com.bondex.ysl.pdaapp.pick;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.PickBean;
import com.bondex.ysl.pdaapp.databinding.PickRecyclerViewItemLayoutBinding;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.NoDoubleClickListener;
import com.bondex.ysl.pdaapp.util.SelecteAllListener;
import com.bondex.ysl.pdaapp.util.ToastUtils;

import java.util.ArrayList;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public class PickAdapter extends RecyclerView.Adapter<PickAdapter.ViewHolder> {

    private ArrayList<PickBean> list;
    private AdapterInteractive interactive;
    private int currentPositon;


    public PickAdapter(ArrayList<PickBean> list, AdapterInteractive interactive) {

        this.list = list;
        this.interactive = interactive;
    }

    public void updateList(ArrayList<PickBean> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<PickBean> getList() {
        return list;
    }


    public int getCurrentPositon() {
        return currentPositon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pick_recycler_view_item_layout, viewGroup, false);

        PickRecyclerViewItemLayoutBinding binding = DataBindingUtil.bind(view);

        ViewHolder holder = new ViewHolder(binding.getRoot());

        holder.setBinding(binding);
        currentPositon = i;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        PickBean bean = list.get(i);

        viewHolder.binding.pickTvCustomName.setText("客户名称: " + bean.getCustomername());
        viewHolder.binding.pickTvStowrage.setText("库位: " + bean.getLocation());
        viewHolder.binding.pickTvTraceId.setText("跟踪号: " + bean.getTraceid());
        viewHolder.binding.pickTvProduct.setText("产品: " + bean.getSku());
        viewHolder.binding.pickTvProductname.setText("产品名称: " + bean.getSkuname());
        viewHolder.binding.pickTvNum.setText("数量: " + bean.getQty());
        viewHolder.binding.pickBtLast.setTag(i);
        viewHolder.binding.pickEtSku.setHint(bean.getSku());
        viewHolder.binding.pickEtStowrage.setHint(bean.getLocation());
        viewHolder.binding.pickEtTraceId.setHint(bean.getTraceid());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private PickRecyclerViewItemLayoutBinding binding;

        private SelecteAllListener allListener = new SelecteAllListener();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void setBinding(PickRecyclerViewItemLayoutBinding binding) {

            this.binding = binding;
            binding.pickEtSku.setOnClickListener(allListener);
            binding.pickEtStowrage.setOnClickListener(allListener);
            binding.pickEtTraceId.setOnClickListener(allListener);

            if (Constant.SOFT_INPUTMOd) {
                CommonUtil.hideSoftInputMethod(binding.pickEtSku);
                CommonUtil.hideSoftInputMethod(binding.pickEtStowrage);
                CommonUtil.hideSoftInputMethod(binding.pickEtTraceId);
            }


            binding.pickEtStowrage.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                        binding.pickEtTraceId.requestFocus();
                        binding.pickEtTraceId.setText(binding.pickEtTraceId.getText().toString());
                        binding.pickEtTraceId.selectAll();
                        return true;
                    }

                    return false;
                }
            });

            binding.pickEtTraceId.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                        binding.pickEtSku.requestFocus();
                        binding.pickEtSku.setText(binding.pickEtSku.getText().toString());
                        binding.pickEtSku.selectAll();
                        return true;
                    }

                    return false;
                }
            });

            binding.pickEtSku.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        return true;
                    }

                    return false;
                }
            });

            binding.pickBtLast.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {

                    int position = (int) binding.pickBtLast.getTag();
                    interactive.last(position);
                }
            });
            binding.pickBtNext.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {

                    int postion = (int) binding.pickBtLast.getTag();
                    interactive.next(postion);
                }
            });

            binding.pickBtConfirm.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {

                    int postion = (int) binding.pickBtLast.getTag();

                    PickBean bean = list.get(postion);

                    String scanLocation = binding.pickEtStowrage.getText().toString();
                    String scanTraceId = binding.pickEtTraceId.getText().toString();
                    String scanSku = binding.pickEtSku.getText().toString();

                    if (CommonUtil.isEmpty(scanLocation)) {

//                        ToastUtils.showToast(binding.getRoot().getContext(), "请输入库位");
//                        return;
                        scanLocation = bean.getLocation();
                    }

                    if (CommonUtil.isEmpty(scanTraceId)) {

//                        ToastUtils.showToast(binding.getRoot().getContext(), "请输入跟踪号");
//                        return;
                        scanTraceId = bean.getTraceid();
                    }

                    if (CommonUtil.isEmpty(scanSku)) {

//                        ToastUtils.showToast(binding.getRoot().getContext(), "请输入产品");
//                        return;

                        scanSku = bean.getSku();
                    }


                    bean.setScanLocation(scanLocation);
                    bean.setScanTraceId(scanTraceId);
                    bean.setScanSku(scanSku);
                    interactive.pick(bean);

                }
            });
            binding.pickBtClear.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {

                    binding.pickEtSku.getText().clear();
                    binding.pickEtTraceId.getText().clear();
                    binding.pickEtStowrage.getText().clear();
                    binding.pickEtStowrage.requestFocus();
                }
            });

        }


    }

}
