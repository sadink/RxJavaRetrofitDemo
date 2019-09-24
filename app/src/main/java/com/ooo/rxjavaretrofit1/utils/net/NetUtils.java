package com.ooo.rxjavaretrofit1.utils.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ooo.rxjavaretrofit1.R;
import com.ooo.rxjavaretrofit1.base.BaseConfig;
import com.ooo.rxjavaretrofit1.utils.toast.ToastUtils;

/**
 * 网络处理工具类
 * lhfBoy on 2017/10/11 16:42
 */

public class NetUtils {
    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Log.d(BaseConfig.LOG, "网络可用");
        } else {
            Log.d(BaseConfig.LOG, "网络不可用");
        }
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }

    /**
     * 校验当前网络情况并做出提示
     *
     * @param activity
     * @return
     */
    public static boolean checkNetwork(Activity activity) {
        if (isNetWorkAvailable(activity)) {
            return true;
        } else {
            if (isAirPlaneModeOn(activity)) {
                ToastUtils.showToast(activity, R.string.airplanemodeOn);
            } else {
                ToastUtils.showToast(activity, R.string.system_web_busy);
            }
            return false;
        }
    }


    /**
     * 获取当前的网络状态 ：0：没有网络 1：WIFI网络 2：3G网络 3：2G网络
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        Log.d(BaseConfig.LOG, "当前网络名称：" + networkInfo.getTypeName() + ",netType:" + netType);
        return netType;
    }

    /**
     * 判断当前是否为飞行模式
     *
     * @param context
     * @return
     */
    public static boolean isAirPlaneModeOn(Context context) {
        int mode = 0;
        try {
            mode = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mode == 1; //为1的时候是飞行模式
    }

}
