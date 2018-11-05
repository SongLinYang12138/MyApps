package com.bondex.ysl.pdaapp.base;

import android.content.Context;

public abstract class BaseModel<T extends BaseBack> {

    public T resultback;
    public Context context;

    public BaseModel(Context context){
        this.context = context;
    }

    /**
     * 回调modal的结果
     */
    public void setCallback(T callback) {

        this.resultback = callback;
    }

    public abstract void doNet(String... param);

    protected abstract void doLocal(String... param);

}
