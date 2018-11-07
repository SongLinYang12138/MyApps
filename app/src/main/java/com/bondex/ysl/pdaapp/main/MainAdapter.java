package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MainBean;
import com.bondex.ysl.pdaapp.exwarehouse.ConsigeMentActivity;
import com.bondex.ysl.pdaapp.inventory.InventoryActivity;
import com.bondex.ysl.pdaapp.util.NoDoubleClickListener;
import com.orhanobut.logger.Logger;
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

        private TextView tvTitel;

        private GridView gridView;


    }

    private ViewHolder holder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){



            holder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,null);

            holder.gridView = view.findViewById(R.id.menu_gridview);
            holder.tvTitel = view.findViewById(R.id.menu_tv_title);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        MainBean bean = list.get(position);
        holder.tvTitel.setText(bean.getTitle());

        holder.gridView.setAdapter(new MenuAdapter(list.get(position).getMenuList(),context));

        return view;
    }


}
