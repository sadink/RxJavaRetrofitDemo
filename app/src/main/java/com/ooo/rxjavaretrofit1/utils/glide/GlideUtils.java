package com.ooo.rxjavaretrofit1.utils.glide;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ooo.rxjavaretrofit1.base.BaseConfig;

import java.io.File;
import java.math.BigDecimal;

/**
 * 图片加载工具类-Glide
 * lhfBoy on 2017/3/7 17:08
 */
public class GlideUtils {

    /**
     * 使用Glide加载图片且缓存所有版本的图像（默认行为）
     *
     * @param context
     * @param ivImg
     * @param url
     */
    public static void loadImage(Context context, ImageView ivImg, String url) {
//        Log.d(BaseConfig.LOG, "url:" + url);
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false) // 设置内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 采用磁盘缓存
//                .placeholder(R.mipmap.errorloading) // 占位图
//                .error(R.mipmap.errorloading) // 加载错误时显示的图
                .into(ivImg);
    }

    public static void loadImage(Fragment fragment, ImageView ivImg, String url) {
//        Log.d(BaseConfig.LOG, "url:" + url);
        Glide.with(fragment)
                .load(url)
                .skipMemoryCache(false) // 设置内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 采用磁盘缓存
//                .placeholder(R.mipmap.errorloading) // 占位图
//                .error(R.mipmap.errorloading) // 加载错误时显示的图
                .into(ivImg);
    }


    /**
     * Glide加载配置占位图
     *
     * @param context
     * @param ivImg
     * @param url
     * @param placeholder
     */
    public static void loadImage(Context context, ImageView ivImg, String url, int placeholder, int error) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false) // 设置内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT) // 采用磁盘缓存
                .crossFade() // 设置淡入淡出动画
                .placeholder(placeholder) // 占位图
                .error(error) // 加载错误时显示的图
                .into(ivImg);
    }


    /**
     * Glide加载配置占位图和图片加载的优先级
     *
     * @param context
     * @param ivImg
     * @param url
     * @param placeholder
     * @param error
     * @param priority
     */
    public static void loadImage(Context context, ImageView ivImg, String url, int placeholder, int error, Priority priority) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false) // 设置内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT) // 采用磁盘缓存
                .crossFade() // 设置淡入淡出动画
                .placeholder(placeholder) // 占位图
                .error(error) // 加载错误时显示的图
                .priority(priority) // 加载图片的优先级
                .into(ivImg);
    }


    /**
     * 使用Glide加载图片并做圆形裁剪
     *
     * @param context
     * @param ivImg
     * @param url
     * @param placeholder
     */
    public static void loadImageCircle(final Context context, final ImageView ivImg, String url, int placeholder) {
        Log.d(BaseConfig.LOG, "用户头像url:" + url);
        Glide.with(context) // 设置上下文对象
                .load(url) // 设置图片的url
                .asBitmap()
                .fitCenter() // 设置图片的填充方式为居中填满
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // 原图缓存
                .placeholder(placeholder) // 设置图片的站位图
                .into(new BitmapImageViewTarget(ivImg) { // 设置图片转圆形操作
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    /**
     * 使用Glide加载图片并做圆形裁剪 带边框
     *
     * @param context
     * @param ivImg
     * @param url
     * @param borderWidth 边框宽度
     * @param borderColor 边框颜色
     * @param placeholder
     */
    public static void loadImageCircle(Context context, ImageView ivImg, String url, int borderWidth, int borderColor, int placeholder) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .placeholder(placeholder)
                .transform(new GlideCircleTransform(context, borderWidth, context.getResources().getColor(borderColor)))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivImg);
    }


    /**
     * 使用Glide从本地加载图片并做圆形裁剪
     *
     * @param context
     * @param ivImg
     * @param file
     * @param placeholder
     */
    public static void loadImgCircleByLocal(final Context context, final ImageView ivImg, File file, int placeholder) {
        Log.d(BaseConfig.LOG, "用户头像path:" + file.getAbsolutePath());
        Glide.with(context) // 设置上下文对象
                .load(file) // 设置图片的url
                .asBitmap()
                .fitCenter() // 设置图片的填充方式为居中填满
                .placeholder(placeholder) // 设置图片的站位图
                .into(new BitmapImageViewTarget(ivImg) { // 设置图片转圆形操作
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    /**
     * 使用Glide加载图片并做圆角矩形裁剪
     *
     * @param context
     * @param ivImg
     * @param url
     * @param placeholder
     */
    public static void loadImageRoundRectangle(final Context context, final ImageView ivImg, String url, int placeholder) {
        Log.d(BaseConfig.LOG, "用户头像url:" + url);
        Glide.with(context) // 设置上下文对象
                .load(url) // 设置图片的url
                .asBitmap()
                .fitCenter() // 设置图片的填充方式为居中填满
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // 原图缓存
                .placeholder(placeholder) // 设置图片的站位图
                .into(new BitmapImageViewTarget(ivImg) { // 设置图片转圆角操作
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(15);
                        ivImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载gif图显示 不缓存
     *
     * @param context
     * @param ivImg
     * @param resId
     */
    public static void loadImageGif(Context context, ImageView ivImg, int resId) {
        Glide.with(context)
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivImg);
    }

    /**
     * 加载gif图显示 缓存
     *
     * @param context
     * @param ivImg
     * @param resId
     */
    public static void loadImageGifFromRefresh(Context context, ImageView ivImg, int resId) {
        Glide.with(context)
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);
    }

    /***
     * 加载gif图显示
     * @param context
     * @param ivImg
     * @param resId
     * @param maxLoopCount 控制gif动画的播放次数
     */
    public static void loadImageGif(Context context, ImageView ivImg, int resId, int maxLoopCount) {
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(ivImg, maxLoopCount));
    }


    /**
     * 清除图片磁盘缓存
     *
     * @param context
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }


    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
