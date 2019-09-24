package com.ooo.rxjavaretrofit1.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ooo.rxjavaretrofit1.R;
import com.ooo.rxjavaretrofit1.base.BaseConfig;

/**
 * 自定义加载进度条视图
 * lhfBoy on 2017/6/9 14:29
 */

public class MyProgressCircleDialog {
    private static final String COMMON_PROMPT = "加载中...";

    private Context context;
    private Dialog progressDialog;
    private TextView msg;
    public static boolean isDismiss = true; // 标识Dialog是否关闭
    public static boolean isShowProgress = true; // 标识Dialog是否打开
    public static boolean isDestroyRequest = false; // 标识Progress取消时是否销毁网络请求


    private static MyProgressCircleDialog sIsntance;

    private OnProgressListener onProgressListener;

    /**
     * 获取到MyProgressDialog单例对象
     *
     * @return
     */
    public static synchronized MyProgressCircleDialog getInstance() {
        if (sIsntance == null) {
            sIsntance = new MyProgressCircleDialog();
        }
        return sIsntance;
    }

    /**
     * 设置视图上下文，用来承载dialog
     *
     * @param context
     * @return
     */
    public MyProgressCircleDialog setContext(Context context) {
        this.context = context;
        return sIsntance;
    }

    /**
     * 仅显示圆形加载进度条
     */
    public void showWaitPrompt() {
        if (!isShowProgress) {
            isShowProgress = true;
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        Log.d(BaseConfig.LOG, "显示进度条");
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.view_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setVisibility(View.GONE);
        progressDialog.show();
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e(BaseConfig.LOG, "网络进度条关闭");
                if (onProgressListener != null && isDestroyRequest) {
                    onProgressListener.onProgressDismiss();
                }
            }
        });
        progressDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
    }


    /**
     * 显示原型加载进度条和文字提示
     *
     * @param prompt
     */
    public void showWaitPrompt(String prompt) {
        try {
            Log.e(BaseConfig.LOG, "showWaitPrompt:" + isShowProgress);
            if (!isShowProgress) {
                isShowProgress = true;
                return;
            }
            Log.d(BaseConfig.LOG, "显示进度条");
            if (progressDialog == null) {
                progressDialog = new Dialog(context, R.style.progress_dialog);
                progressDialog.setContentView(R.layout.view_dialog);
                progressDialog.setCancelable(true);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
                msg.setVisibility(View.VISIBLE);
                if (prompt == null || prompt.length() == 0) {
                    msg.setText(COMMON_PROMPT);
                } else {
                    msg.setText(prompt);
                }
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.e(BaseConfig.LOG, "网络进度条关闭");
                        if (onProgressListener != null && isDestroyRequest) {
                            onProgressListener.onProgressDismiss();
                        }
                    }
                });
                progressDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
            } else {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(BaseConfig.LOG, "进度条异常：" + e.getMessage());
        }
    }

    /**
     * 关闭进度条
     */
    public void dismiss() {
        if (null != progressDialog && progressDialog.isShowing() && isDismiss) {
            progressDialog.dismiss();
            Log.d(BaseConfig.LOG, "关闭进度条");
        }
        context = null;
        isShowProgress = true;
        isDismiss = true;
        isDestroyRequest = false;
    }

    public void dismiss(boolean isDismiss) {
        if (null != progressDialog && progressDialog.isShowing() && isDismiss) {
            progressDialog.dismiss();
            Log.d(BaseConfig.LOG, "关闭进度条");
        }
        context = null;
        isShowProgress = true;
        MyProgressCircleDialog.isDismiss = true;
        isDestroyRequest = false;
    }

    /**
     * 判断dialog是否存在并且是否在show
     *
     * @return
     */
    public boolean isShow() {
        return progressDialog != null && progressDialog.isShowing();
    }

    /**
     * 网络进度条监听
     */
    public interface OnProgressListener {
        void onProgressDismiss();
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
