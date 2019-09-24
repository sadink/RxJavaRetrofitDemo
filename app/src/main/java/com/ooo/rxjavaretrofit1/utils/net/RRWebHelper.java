package com.ooo.rxjavaretrofit1.utils.net;


import android.app.Activity;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.ooo.rxjavaretrofit1.base.BaseApi;
import com.ooo.rxjavaretrofit1.base.BaseApplication;
import com.ooo.rxjavaretrofit1.base.BaseConfig;
import com.ooo.rxjavaretrofit1.utils.net.ssl.TrustAllCertsManager;
import com.ooo.rxjavaretrofit1.utils.net.ssl.VerifyEverythingHostnameVerifier;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * App端同Web端进行数据交互基层对象处理
 * Created by lhfBoy 2017/10/11 16:12
 */

public class RRWebHelper {
    private static final Object monitor = new Object();
    private static BaseApi sCgwApi = null;
    private static Retrofit retrofit = null;
    private static List<Subscription> listSubscription = new ArrayList<>(); // 存储网络订阅对象


    /**
     * 定义网络请求参数
     */
    private static final long CONNECT_TIMEOUT = 10;  // 链接超时10s
    private static final long READ_TIMEOUT = 6000;  // 读取超时6s
    private static final long WRITE_TIMEOUT = 6000;  // 写入超时6s


    private static class SingletonHolder {
        private static final RRWebHelper INSTANCE = new RRWebHelper();
    }

    public RRWebHelper() {
    }

    public static final RRWebHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 定义普通的网络访问对象
     */
    public static OkHttpClient clientNormal = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build();
    private static OkHttpClient okHttpClient;
    private static SharedPrefsCookiePersistor sharedPrefsCookiePersistor;
    private static PersistentCookieJar cookieJar;


    /**
     * 让Android系统信任自签的CA证书,设置可访问所有的https网站(不安全)
     *
     * @return
     */
    public  OkHttpClient getOkHttpClient() {
        //  创建信任管理器
        TrustManager[] trustManager = new TrustManager[]{new TrustAllCertsManager()};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(new VerifyEverythingHostnameVerifier())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(getCookieJar())
                //其他配置
                .build();
    }

    /**
     * 获取cookieJar
     *
     * @return
     */
    protected static PersistentCookieJar getCookieJar() {
        // 持久化Cookie处理
        if (cookieJar == null) {
            sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(BaseApplication.context());
            cookieJar = new PersistentCookieJar(new SetCookieCache(), sharedPrefsCookiePersistor);
        }
        return cookieJar;
    }


    /**
     * 定义全局单例模式的网络访问对象
     *
     * @return
     */
    public  final BaseApi getCgwApi() {
        synchronized (monitor) {
            if (sCgwApi == null) {
                okHttpClient = getOkHttpClient();
                sCgwApi = new Retrofit.Builder()
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(BaseConfig.SERVICE)
                        .build()
                        .create(BaseApi.class);
            }
            return sCgwApi;
        }
    }

    /**
     * 定义全局单例模式的网络访问对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getCgwApi(Class<T> service) {
        synchronized (monitor) {
            if (retrofit == null) {
                okHttpClient = RRWebHelper.getInstance().getOkHttpClient();
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(BaseConfig.SERVICE)
                        .build();
            }
        }
        return retrofit.create(service);
    }



    /**
     * 终端访问服务处理
     *
     * @param observable 指定网络请求的具体接口
     * @param subscriber 指定网络响应的具体处理业务
     */
    public static void RequestServer(Activity activity, Observable observable, Subscriber subscriber) {
        // 判断网络是否正常
        boolean netState = NetUtils.checkNetwork(activity);
        if (netState) {
            Subscription subscription = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            new CompositeSubscription().add(subscription);
        }
    }

    /**
     * 终端访问服务处理
     *
     * @param observable                指定网络请求的具体接口
     * @param subscriber                指定网络响应的具体处理业务
     * @param isUnsubscribeFromProgress 网络请求是否随进度条的取消而取消 true:跟随，false:不跟随
     */
    public static void RequestServer(Activity activity,Observable observable, Subscriber subscriber, boolean isUnsubscribeFromProgress) {
        // 判断网络是否正常
        boolean netState = NetUtils.checkNetwork(activity);
        if (netState) {
            Subscription subscription = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            new CompositeSubscription().add(subscription);
            if (isUnsubscribeFromProgress) {
                listSubscription.add(subscription);
            }
        }
    }

    /**
     * 切断网络连接请求
     */
    public static void closeConnnectionNetwork(Subscription subscription) {
        if (okHttpClient != null) {
            Log.e(BaseConfig.LOG, "切断网络连接请求--请求连接数量前：" + okHttpClient.connectionPool().connectionCount());
            okHttpClient.connectionPool().evictAll();
            okHttpClient.dispatcher().cancelAll();
            Log.e(BaseConfig.LOG, "切断网络连接请求--请求连接数量后：" + okHttpClient.connectionPool().connectionCount());
        }
        closeSubscribe(subscription);
    }

    /**
     * 主动取消网络订阅，进度条取消时网络请求取消
     */
    public static void closeSubscribe() {
        for (int i = 0; i < listSubscription.size(); i++) {
            if (listSubscription.get(i) != null && !listSubscription.get(i).isUnsubscribed()) {
                Log.e(BaseConfig.LOG, "主动取消网络订阅，进度条取消时网络请求取消");
                listSubscription.get(i).unsubscribe();
            }
        }
        listSubscription.clear();
    }

    /**
     * 主动取消网络订阅
     *
     * @param subscription
     */
    public static void closeSubscribe(Subscription subscription) {
        if (subscription != null) {
            Log.e(BaseConfig.LOG, "切断网络连接请求--订阅状态is已取消前：" + subscription.isUnsubscribed());
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
                new CompositeSubscription().add(subscription);
            }
            Log.e(BaseConfig.LOG, "切断网络连接请求--订阅状态is已取消后：" + subscription.isUnsubscribed());
        }
    }
}
