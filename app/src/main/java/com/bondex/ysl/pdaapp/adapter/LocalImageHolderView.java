package com.bondex.ysl.pdaapp.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bondex.ysl.pdaapp.R;


public class LocalImageHolderView extends Holder<Integer> {
    private ImageView imageView;
    private Context context;

    public LocalImageHolderView(View itemView,Context context) {
        super(itemView);
        this.context = context;
    }


    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.ivPost);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setImageResource(data);
    }
}
