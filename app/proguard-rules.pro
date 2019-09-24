# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#---------------------------------1.实体类---------------------------------
-keep class com.ooo.rxjavaretrofit1.mode.entity.** { *; }


#---------------------------------2.第三方包-------------------------------

#rxjava
-dontwarn rx.**
-keep class rx.** { *; }

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn org.robovm.**
-keep class org.robovm.** { *; }

#okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-keep class okhttp3.** { *;}
-keep class okio.** { *;}
-dontwarn sun.security.**
-keep class sun.security.** { *;}
-dontwarn okio.**
-dontwarn okhttp3.**

# fastJson
-keep class com.alibaba.fastjson.**{*;}
-dontwarn com.alibaba.fastjson.**
#gson包
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep class java.lang.** { *; }
-dontwarn java.lang.**


#Glade图片加载
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

#解决Toast token失效问题
-keep class me.drakeet.support.toast.** { *; }
-dontwarn me.drakeet.support.toast.**

#解决Toast在华为9.0上连续点击不显示问题
-keep class com.dovar.** { *; }
-dontwarn com.dovar.**

#---------------------------------基本指令区----------------------------------
-optimizationpasses 5 # 代码混淆压缩比，在0~7之间，默认为5
-dontusemixedcaseclassnames #混合时不使用大小写混合，混合后的类名为小写
-dontskipnonpubliclibraryclasses #指定不去忽略非公共库的类
-verbose
-dontskipnonpubliclibraryclassmembers # 指定不去忽略非公共库的类
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/* # 混淆时所采用的算法
-keepattributes *Annotation*,InnerClasses #保留Annotation不混淆
-keepattributes Signature #避免混淆泛型
-keepattributes SourceFile,LineNumberTable #抛出异常时保留代码行号
#-ignorewarnings

-keepattributes EnclosingMethod
-dontpreverify # 不做预校验,加快混淆速度
#----------------------------------------------------------------------------
