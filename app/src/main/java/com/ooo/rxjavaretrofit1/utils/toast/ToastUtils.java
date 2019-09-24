package com.ooo.rxjavaretrofit1.utils.toast;

import android.app.Activity;
import android.view.Gravity;

import com.dovar.dtoast.DToast;
import com.ooo.rxjavaretrofit1.R;


/**
 * 提示框
 * dongdd on 2016/2/6 12:28
 */
public class ToastUtils {


    /**
     * @param activity
     * @param message Toast.LENGTH_SHORT = 2s Toast.LENGTH_LONG = 3.5s
     */
    public static void showToast(Activity activity, String message) {
        DToast.make(activity)
                .setText(R.id.tv_content_default, message)
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200)
                .show();
    }

    /**
     * @param activity
     * @param message Toast.LENGTH_SHORT = 2s Toast.LENGTH_LONG = 3.5s
     */
    public static void showToast(Activity activity, int message) {
        DToast.make(activity)
                .setText(R.id.tv_content_default, activity.getResources().getString(message))
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200)
                .show();
    }

    /**
     * 清除与{@param mActivity}关联的ActivityToast，避免窗口泄漏
     *
     * @param activity
     */
    public static void cancelActivityToast(Activity activity) {
        DToast.cancelActivityToast(activity);
    }


}
