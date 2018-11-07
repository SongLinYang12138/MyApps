package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MenuBean;
import com.bondex.ysl.pdaapp.util.NoDoubleClickListener;

import java.util.ArrayList;

/**
 * date: 2018/11/7
 * Author: ysl
 * description:
 */
public class MenuAdapter extends BaseAdapter {

    private ArrayList<MenuBean> list;
    private Context context;

    public MenuAdapter(ArrayList<MenuBean> list, Context context) {
        this.list = list;
        this.context = context;
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

        TextView bt;
    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item_main_menu_layou, null);
            holder = new ViewHolder();
            holder.bt = view.findViewById(R.id.menu_bt);
            MyClickListener listener = new MyClickListener(holder);
            holder.bt.setOnClickListener(listener);
           view.setTag(holder);
        }else {

            holder = (ViewHolder) view.getTag();
        }

        holder.bt.setTag(Integer.valueOf(position));

        holder.bt.setText(list.get(position).getName());
        return view;
    }


    private class MyClickListener extends NoDoubleClickListener {

        private ViewHolder holder;

        public MyClickListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void click(View v) {

            Integer postion = (Integer) holder.bt.getTag();

            switch (list.get(postion).getFlag()){

                case "":

                    break;


            }

        }
    }


}
