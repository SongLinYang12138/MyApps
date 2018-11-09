package com.bondex.ysl.pdaapp.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bondex.ysl.pdaapp.R;


public class LocalImageHolderView extends Holder<String> {
    private TextView tv;

    public LocalImageHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        tv =itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(String data) {
        tv.setText(data);
    }
}
