package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MainBean;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private Context context;
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

        private TextView tvTitel;
        private RecyclerView recyclerView;

        private FlexboxLayoutManager layoutManager;

        public ViewHolder() {

            layoutManager = new FlexboxLayoutManager();
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setAlignItems(AlignItems.STRETCH);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        }


    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            holder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, parent,false);
            holder.recyclerView = view.findViewById(R.id.menu_gridview);
            holder.tvTitel = view.findViewById(R.id.menu_tv_title);

            holder.recyclerView.setLayoutManager(holder.layoutManager);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MainBean bean = list.get(position);
        holder.tvTitel.setText(bean.getTitle());

        holder.recyclerView.setAdapter(new MenuAdapter(list.get(position).getMenuList(),context));

        return view;
    }


}
