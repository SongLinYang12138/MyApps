package com.bondex.ysl.pdaapp.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bondex.ysl.pdaapp.R;


public class LocalImageHolderView extends Holder<Integer> {
    private ImageView imageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setImageResource(data);
    }
}
