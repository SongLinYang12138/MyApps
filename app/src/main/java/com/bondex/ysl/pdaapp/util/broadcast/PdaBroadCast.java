package com.bondex.ysl.pdaapp.util.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.SystemBroadCast;
import com.bondex.ysl.pdaapp.util.interf.PdaCallback;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class PdaBroadCast extends BroadcastReceiver {

    private PdaCallback pdaCallback;

    public PdaBroadCast(PdaCallback pdaCallback) {
        this.pdaCallback = pdaCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals(SystemBroadCast.SCN_CUST_ACTION_SCODE)) {
            String message = "";

            try {
                message = intent.getStringExtra(SystemBroadCast.SCN_CUST_EX_SCODE).toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("in", e.toString());
            }
            if (pdaCallback != null && CommonUtil.isNotEmpty(message)) pdaCallback.pdaResult(message);


        }
    }
}
