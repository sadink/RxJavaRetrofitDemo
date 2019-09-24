package com.ooo.rxjavaretrofit1.utils.net;

import android.content.Context;
import android.util.Log;


import com.ooo.rxjavaretrofit1.base.BaseConfig;
import com.ooo.rxjavaretrofit1.ui.widget.MyProgressCircleDialog;
import com.ooo.rxjavaretrofit1.utils.DataFormatUtils;

import rx.Subscriber;

/**
 * 网络框架响应处理
 * Created by lhfBoy 2017/12/17 21:18
 */

public abstract class SubscriberSi<T> extends Subscriber<T> {
    Context context;

    public SubscriberSi(Context context) {
        MyProgressCircleDialog.getInstance().setContext(context);
        this.context = context;
    }

    /**
     * 屏蔽网络请求成功后的完成方法
     */
    @Override
    public void onCompleted() {
        Log.d(BaseConfig.LOG, "progress onCompleted()");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(BaseConfig.LOG, "onError 异常：" + e.getMessage());
        if (MyProgressCircleDialog.getInstance().isShow()) {
            MyProgressCircleDialog.getInstance().dismiss();
            Log.d(BaseConfig.LOG, "progress  onError(): dismiss");
        }
        e.printStackTrace();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(BaseConfig.LOG, "progress onStart()");
        if (!MyProgressCircleDialog.getInstance().isShow()) {
            Log.d(BaseConfig.LOG, "progress onStart():显示默认进度条");
            MyProgressCircleDialog.getInstance().showWaitPrompt();
        }
    }

    @Override
    public void onNext(T t) {
        Log.d(BaseConfig.LOG, "onNext()");
        if (MyProgressCircleDialog.getInstance().isShow()) {
            MyProgressCircleDialog.getInstance().dismiss();
            Log.d(BaseConfig.LOG, "progress onNext(): dismiss");
        }
        Log.e(BaseConfig.LOG, "response:" + DataFormatUtils.GsonString(t));
    }
}
