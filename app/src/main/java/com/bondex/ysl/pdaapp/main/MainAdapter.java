package com.bondex.ysl.pdaapp.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.bean.MainBean;
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

            MyClickListener clickListener = new MyClickListener(holder);
            holder.tvName.setOnClickListener(clickListener);
            holder.ivIcon.setOnClickListener(clickListener);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        MainBean bean = list.get(position);

        holder.tvName.setTag(Integer.valueOf(position));

        holder.tvName.setText(bean.getName());
        holder.tvTitel.setText(bean.getTitle());
        holder.ivIcon.setImageResource(bean.getResourceId());
        return view;
    }

  private   class MyClickListener extends NoDoubleClickListener{

        private ViewHolder holder;

        public MyClickListener(ViewHolder viewHolder){

            holder = viewHolder;
        }

        @Override
        public void click(View v) {

            Integer poition  = (Integer) holder.tvName.getTag();

            Logger.i("position  "+poition);
            Intent intent = null;
            switch (poition){

                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    intent = new Intent(context,InventoryActivity.class);
                    break;
            }

            if(intent != null) context.startActivity(intent);
        }
    }
}
