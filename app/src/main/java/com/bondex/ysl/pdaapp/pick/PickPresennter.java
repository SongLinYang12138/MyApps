package com.bondex.ysl.pdaapp.pick;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.PickBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.ToastUtils;

import java.util.ArrayList;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public class PickPresennter extends BasePresnter<PickView, PickModal> implements PickBack, AdapterInteractive {

    private PickAdapter adapter;


    public PickPresennter(PickView view, Context context) {
        super(view, context);

        ArrayList<PickBean> list = new ArrayList<>();
        adapter = new PickAdapter(list, this);
        view.setAdapter(adapter);
    }

    @Override
    public PickModal getModal() {

        modal = new PickModal(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void initData() {

    }

    public void search(String code) {


        if (!isRunning) {

            modal.searchCode(code);
            isRunning = true;
            view.showLoading();
        }
    }

    @Override
    public void searchSuccess(ArrayList<PickBean> list) {

        adapter.updateList(list);
        view.setTotalSize(adapter.getItemCount(), 0);
        isRunning = false;
        view.stopLoading();
        view.onSuccess("");
    }

    @Override
    public void searchFaile(String msg) {

        isRunning = false;
        view.faile(msg);
        view.stopLoading();
    }

    @Override
    public void pickSuccess(String s) {

        isRunning = false;
        view.stopLoading();
        view.onSuccess(s);

        ArrayList<PickBean> beans = adapter.getList();

        beans.remove(adapter.getCurrentPositon());
        adapter.updateList(beans);
        view.setTotalSize(beans.size(), 0);
    }

    @Override
    public void pickFaile(String s) {

        isRunning = false;
        view.stopLoading();
        view.faile(s);
    }

    @Override
    public void last(int position) {

        view.last(position);
    }

    @Override
    public void next(int position) {

        view.next(position);
    }

    @Override
    public void pick(PickBean bean) {

        String msg = "";
        if (!bean.getScanLocation().equals(bean.getLocation())) {
            msg = " 当前库位与获取的库位不一致";
        }
        if (!bean.getScanTraceId().equals(bean.getTraceid())) {
            msg += "\n 当前跟踪号与获取的跟踪号不一致 ";
        }
        if (!bean.getScanSku().equals(bean.getSku())) {

            msg += "\n 当前产品与获取到的产品不一致";
        }

        if (CommonUtil.isNotEmpty(msg)) {

            view.showAlert(msg, bean);
        } else {

            pickModal(bean.getAllocationdetailsid().trim());
        }

    }

    public void pickModal(String allocationDetailId) {

        if (CommonUtil.isEmpty(allocationDetailId)) {

            ToastUtils.showToast(context, "AllocationDetialId为null");
            return;
        }
        if (!isRunning) {

            modal.pick(allocationDetailId);
            isRunning = true;
            view.showLoading();
        }
    }
}
