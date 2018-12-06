package com.bondex.ysl.pdaapp.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bondex.ysl.installlibrary.download.DownloadListener;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.MainBean;
import com.bondex.ysl.pdaapp.bean.MenuBean;
import com.bondex.ysl.pdaapp.bean.UpdateBean;
import com.bondex.ysl.pdaapp.ui.ProgressView;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.ToastUtils;
import com.bondex.ysl.pdaapp.util.netutil.MD5;
import com.orhanobut.logger.Logger;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;

public class MainPresenter extends BasePresnter<MainView, MainModal> implements MainBack, DownloadListener {


    private ArrayList<String> localImages = new ArrayList<>();

    private String filePath;
    private TextView updateTv;
    private ProgressView updateProgress;
    private Button updateConfirm;
    private Dialog updateDialog;
    private UpdateBean updateBean;


    public MainPresenter(MainView view, final Context context) {
        super(view, context);


        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bondex/Pda.apk";

        setBainner();
        setListAdapter();
    }

    private void setBainner() {


        localImages.add("欢迎使用仓库PDA系统");
        localImages.add(PdaApplication.LOGINBEAN.getUsername());

        CBViewHolderCreator holderCreator = new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.image_layout;
            }
        };

        view.setBnnerrs(holderCreator, localImages);

    }


    private void setListAdapter() {


        ArrayList<MainBean> mainBeans = new ArrayList<>();

        ArrayList<MenuBean> inList = new ArrayList<>();
        inList.add(new MenuBean("标准收货", "standardReceive"));
//        inList.add(new MenuBean("混托盘收货", "confusionReceive"));

        ArrayList<MenuBean> outList = new ArrayList<>();

        outList.add(new MenuBean("按订单发货", "orderConsignment"));
        outList.add(new MenuBean("订单拣货", "orderPick"));

        ArrayList<MenuBean> movieList = new ArrayList<>();
        movieList.add(new MenuBean("单元移库", "unitmovie"));
        movieList.add(new MenuBean("库存查询", "querystorage"));

        mainBeans.add(new MainBean("入库", inList));
        mainBeans.add(new MainBean("出库", outList));
        mainBeans.add(new MainBean("库存", movieList));


        MainAdapter adapter = new MainAdapter(context, mainBeans);
        view.listAdapter(adapter);
    }

    private void checkCode() {

        modal.checkVersion();
    }


    @Override
    public MainModal getModal() {

        modal = new MainModal(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void initData() {

        checkCode();
    }


    @Override
    public void checkUpdate(UpdateBean bean) {

        updateBean = bean;
//        对比version code 检查是否需要更新
        if (bean.getVersion_id() > CommonUtil.getVersionCode(context)) {

            showUpdateDialog(bean);
        } else {

        }


    }


    private void showUpdateDialog(UpdateBean bean) {

        if (updateDialog == null) {


            updateDialog = new Dialog(context, R.style.dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_layout, null);

            updateDialog.setContentView(view);
            ImageView baseBack = view.findViewById(R.id.base_back);

            updateConfirm = view.findViewById(R.id.update_confirm);
            Button updateCancel = view.findViewById(R.id.update_cancel);
            updateTv = view.findViewById(R.id.update_tv);
            updateProgress = view.findViewById(R.id.update_progress);
            baseBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDialog.dismiss();
                }
            });
            updateConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateConfirm.setClickable(false);
                    modal.download(updateBean.getDownload_url(), filePath, MainPresenter.this);
                }
            });
            updateCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDialog.dismiss();
                }
            });
            updateTv.setText(bean.getVersion_remark());


            WindowManager.LayoutParams lp = updateDialog.getWindow().getAttributes();
            lp.width = CommonUtil.getScreentW(context) - 20;
            int height = CommonUtil.getScreenH(context);
            lp.height = height - height / 4;

            lp.gravity = Gravity.CENTER;

            updateDialog.getWindow().setAttributes(lp);

            updateDialog.setCanceledOnTouchOutside(true);

        }

        updateDialog.show();

    }


    @Override
    public void onStart() {

        progressHandler.sendEmptyMessage(START);
    }

    @Override
    public void onProgress(int progress) {

        progressHandler.sendEmptyMessage(progress);
    }

    @Override
    public void onFinish(String path,byte[] data) {


        Logger.i("datalength   "+data.length);
        String myMd5 = MD5.getFileMD5(filePath);

//        Logger.i("updateMd5 " + updateBean.getDownlad_md5() + "\n myMd5: " + myMd5);

        if (updateBean.getDownlad_md5().equals(myMd5)) {

            Logger.i("DM5解析完毕");
        } else {

            Logger.i("DM5不一致");
        }

        progressHandler.sendEmptyMessage(FINISH);
    }

    @Override
    public void onFail(String errorInfo) {

        Message msg = new Message();
        msg.obj = errorInfo;
        msg.what = FAILED;

        progressHandler.sendMessage(msg);
    }


    private final  int START = 1001;
    private final  int FINISH = 1100;
    private final  int FAILED = 1110;
    private  Handler progressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what != START && msg.what != FINISH && msg.what != FAILED) {

                updateProgress.setCurrentValue(msg.what);
            } else {


                switch (msg.what) {
                    case START:
                        if (updateTv != null) {
                            updateTv.setVisibility(View.GONE);
                            updateProgress.setVisibility(View.VISIBLE);
                        }

                        break;
                    case FINISH:

                        if (updateTv != null) {
                            updateDialog.dismiss();
                            ToastUtils.showToast(context,"下载成功");
                            updateTv.setVisibility(View.VISIBLE);
                            updateProgress.setVisibility(View.GONE);
                            updateConfirm.setClickable(true);
                            view.installApk(filePath);

                        }
                        break;
                    case FAILED:

                        if (updateTv != null) {

                            if (CommonUtil.isNotEmpty(msg.obj.toString()))
                                ToastUtils.showToast(context,msg.obj.toString());
                            updateConfirm.setClickable(true);
                            updateTv.setVisibility(View.VISIBLE);
                            updateProgress.setVisibility(View.GONE);
                        }
                        break;
                }

            }

        }
    };
}
