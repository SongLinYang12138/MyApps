package com.bondex.ysl.pdaapp.query;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;
import com.bondex.ysl.pdaapp.databinding.RecyclerviewItemQueryLayoutBinding;

import java.util.ArrayList;

/**
 * date: 2018/11/22
 * Author: ysl
 * description:
 */
public class QueryStowrageAdapter extends RecyclerView.Adapter<QueryStowrageAdapter.ViewHodler> {

    private ArrayList<QueryStowrageBean> list;

    public QueryStowrageAdapter(ArrayList<QueryStowrageBean> list) {

        this.list = list;
    }

    public void setList(ArrayList<QueryStowrageBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList(){

        this.list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_query_layout, viewGroup, false);
        RecyclerviewItemQueryLayoutBinding binding = DataBindingUtil.bind(view);
        ViewHodler hodler = new ViewHodler(binding.getRoot());
        hodler.setBinding(binding);

        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {

        QueryStowrageBean bean = list.get(i);

        viewHodler.binding.queryItemCustomId.setText("客户ID:  " + bean.getCustomerid());
        viewHodler.binding.queryItemCustomName.setText("客户姓名:  " + bean.getCustomername());
        viewHodler.binding.queryItemSkuId.setText("产品 :  " + bean.getSku());
        viewHodler.binding.queryItemLocationId.setText("库位:  " + bean.getLocationid());
        viewHodler.binding.queryItemTraceId.setText("跟踪号:  " + bean.getTraceid());
        viewHodler.binding.queryItemNum.setText("库存数量" + bean.getQty() + "   |  分配数量" + bean.getQtyallocated());
        viewHodler.binding.queryItemNumfreeze.setText("冻结数量" + bean.getQtyonhold() + "   |   可用数据" + bean.getQtyused() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHodler extends RecyclerView.ViewHolder {

        public RecyclerviewItemQueryLayoutBinding binding;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
        }

        public void setBinding(RecyclerviewItemQueryLayoutBinding binding) {
            this.binding = binding;
        }
    }
}
