package com.bondex.ysl.pdaapp.util;

import android.content.Context;
import android.content.Intent;

import static com.bondex.ysl.pdaapp.util.SystemBroadCast.SCN_CUST_ACTION_CANCEL;

public class PdaUtils {

    public static void turnOnOffPda(boolean isOpen, Context context) {

        int value = isOpen ? 1 : 0;

        Intent intent = new Intent(SystemBroadCast.SCANNER_POWER);
        intent.putExtra("scanneronoff", value);
        context.sendBroadcast(intent);
    }

    public static void startScan(Context context) {

        Intent intent = new Intent(SystemBroadCast.SCN_CUST_ACTION_SCODE);
        context.sendBroadcast(intent);
    }

    public static void cancelScan(Context context) {

        Intent scannerIntent = new Intent(SCN_CUST_ACTION_CANCEL);
        context.sendBroadcast(scannerIntent);
    }


}
