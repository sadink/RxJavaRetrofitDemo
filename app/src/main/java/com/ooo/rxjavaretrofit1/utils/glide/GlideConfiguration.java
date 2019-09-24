package com.ooo.rxjavaretrofit1.utils.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.ooo.rxjavaretrofit1.base.BaseConfig;
import com.ooo.rxjavaretrofit1.utils.net.RRWebHelper;

import java.io.InputStream;

/**
 * 配置Glide网络客户端，让图片和App共享一个客户端
 * Created by Dongdd on 2019/9/18 0018 9:56.
 */
public class GlideConfiguration implements GlideModule {


    private OkHttpUrlLoader.Factory factory = null;

    /**
     * 配置相关Glide相关属性
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    /**
     * 配置Glide相关组件
     *
     * @param context
     * @param glide
     */
    @Override
    public void registerComponents(Context context, Glide glide) {
        //        初始化OkHttpUtils
        if (factory == null) {
            factory = new OkHttpUrlLoader.Factory(RRWebHelper.getInstance().getOkHttpClient());
            Log.d(BaseConfig.LOG, "图片cookie保存初始化");
        }
        glide.register(GlideUrl.class, InputStream.class, factory);
    }


}
