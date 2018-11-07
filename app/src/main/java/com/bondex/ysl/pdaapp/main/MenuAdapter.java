package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MenuBean;
import com.bondex.ysl.pdaapp.exwarehouse.ConsigeMentActivity;
import com.bondex.ysl.pdaapp.movie.MovieInventoryActivity;
import com.bondex.ysl.pdaapp.util.NoDoubleClickListener;
import com.bondex.ysl.pdaapp.util.ToastUtils;

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
        } else {

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

//        inList.add(new MenuBean("标准收货","standardReceive"));
//        inList.add(new MenuBean("混托盘收货","confusionReceive"));
//        outList.add(new MenuBean("按订单发货","orderConsignment"));
//        outList.add(new MenuBean("订单拣货","orderPick"));
//        movieList.add(new MenuBean("单元移库","unitmovie"));

        @Override
        public void click(View v) {

            Integer postion = (Integer) holder.bt.getTag();
            Intent intent = null;
            switch (list.get(postion).getFlag()) {

                case "unitmovie":

                    intent = new Intent(context, MovieInventoryActivity.class);
                    break;
                case "orderConsignment":

                    intent = new Intent(context, ConsigeMentActivity.class);
                    break;

                default:
                    ToastUtils.showToast("该功能正在开发中");
            }

            if (intent != null) context.startActivity(intent);


        }
    }


}
