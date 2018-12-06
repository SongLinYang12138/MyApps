package com.bondex.ysl.pdaapp.util;

import android.view.View;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    private long lastClick = 0;

    @Override
    public void onClick(View v) {

        if (System.currentTimeMillis() - lastClick > 2000) {
            click(v);
        } else {
            lastClick = System.currentTimeMillis();
        }
    }

    public abstract void click(View v);
}
