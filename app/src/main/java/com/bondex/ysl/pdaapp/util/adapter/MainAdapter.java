package com.bondex.ysl.pdaapp.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MainBean;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private Context  context;
    private ArrayList<MainBean> list;

    public MainAdapter(Context context, ArrayList<MainBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        private TextView tvName,tvTitel;
        private ImageView ivIcon;


    }

    private ViewHolder holder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
            holder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,null);

            holder.tvName = view.findViewById(R.id.menu_tv_name);
            holder.tvTitel =view.findViewById(R.id.menu_tv_title);
            holder.ivIcon = view.findViewById(R.id.menu_iv_icon);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        MainBean bean = list.get(position);

        holder.tvName.setText(bean.getName());
        holder.tvTitel.setText(bean.getTitle());
        holder.ivIcon.setImageResource(bean.getResourceId());
        return view;
    }
}
