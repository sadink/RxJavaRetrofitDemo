package com.ooo.rxjavaretrofit1.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ooo.rxjavaretrofit1.base.BaseConfig;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 浮点型数据格式转换类
 * Created by lhfBoy 2016/12/27 0012 08:42
 */
public class DataFormatUtils {

    /**
     * 精确到一位
     *
     * @return String
     */
    public static String accurateDataTo1(Float value) {
        return new DecimalFormat("0.0").format(value);
    }

    /**
     * 精确到两位
     *
     * @param value
     * @return
     */
    public static String accurateDataTo2(Double value) {
        return new DecimalFormat("0.00").format(value);
    }

    /**
     * 将Double数据精确(四舍五入)到指定位数
     *
     * @param value
     * @param scale
     * @return
     */
    public static String accurateDataToScale(Double value, int scale) {
        double result = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result + "";
    }

    /**
     * 将Double数据精确(四舍五入)到2位数
     *
     * @param value
     * @return
     */
    public static String accurateDataToScale2(Double value) {
        return accurateDataToScale(value, 2);
    }

    /**
     * 精确到两位
     *
     * @param value
     * @return
     */
    public static Double accurateDataTo2Double(Double value) {
        String decimalFormat = new DecimalFormat("0.00").format(value);
        return Double.parseDouble(decimalFormat);
    }


    /**
     * 设置价格.00的字体大小
     *
     * @param money
     * @return
     */
    public static SpannableString setMoneySize(String money) {
        SpannableString span = new SpannableString("¥ " + money);
        //设置需要的字体大小，已经需要设置文本起始位置
        span.setSpan(new AbsoluteSizeSpan(24), span.length() - 2, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }


    /**
     * 定义Gson静态对象
     */
    private static Gson gson = null;

    static {
        /**
         * 添加字段转换过滤：final(常量)、transient(瞬态)、static(静态)
         */
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        gson = builder.create();
    }

    /**
     * obj转成json
     *
     * @param obj
     * @return
     */
    public static String GsonString(Object obj) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(obj);
        }
        return gsonString;
    }

    /**
     * 将json字符串转换为对象实体
     *
     * @param json
     * @param typeOfT new TypeToken<T>() {}.getType()
     * @param <T>
     * @return
     */
    public static <T> T jsonToObj(String json, Type typeOfT) {
        if (json.length() > 0) {
            T classOfT = gson.fromJson(json, typeOfT);
            return classOfT;
        } else {
            return null;
        }
    }

    /**
     * jsonString转成list<bean>
     *
     * @param gsonString
     * @param <T>
     * @return
     */
    public static <T> List<T> GsonToBeans(String gsonString) {
        List<T> ts = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return ts;
    }

    /**
     * 电话号码格式处理
     *
     * @param phoneNo
     * @return
     */
    public static String phoneFormat(String phoneNo) {
        if (phoneNo == null || phoneNo.length() == 0) {
            return "";
        }
        return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
    }

    /**
     * 根据手机号码获取手机号码相关信息
     *
     * @param addressAreaCode
     * @return 区号、手机号码、区域名称
     */
    public static String[] phoneAreaCode(String addressAreaCode) {
        String phone[] = new String[]{"+86", addressAreaCode, "中国大陆"};
        if (addressAreaCode.length() == 0) {
            return phone;
        }
        // 内陆
        if (addressAreaCode.substring(0, 3).equals("+86")) {
            phone[0] = "+86";
            phone[1] = addressAreaCode.substring(3);
            phone[2] = "中国大陆";
        } else if (addressAreaCode.substring(0, 4).equals("+852")) {
            // 香港
            phone[0] = "+852";
            phone[1] = addressAreaCode.substring(4);
            phone[2] = "中国香港";
        } else if (addressAreaCode.substring(0, 4).equals("+853")) {
            // 澳门
            phone[0] = "+853";
            phone[1] = addressAreaCode.substring(4);
            phone[2] = "中国澳门";
        } else if (addressAreaCode.substring(0, 4).equals("+886")) {
            // 台湾
            phone[0] = "+886";
            phone[1] = addressAreaCode.substring(4);
            phone[2] = "中国台湾";
        } else {
            // 其他
            phone[0] = "+86";
            phone[1] = addressAreaCode;
            phone[2] = "中国大陆";
        }
        return phone;
    }


    /**
     * 处理从通讯录中获取到的不同格式的手机号码
     * 例如:
     * 1-839-212-1466
     * 029-372-99615
     * 156 9286 3886
     * 15692863880
     *
     * @param phoneNumber
     * @return
     */
    public static String phoneNumberFormatCount(String phoneNumber) {
        String result = "";
        phoneNumber = phoneNumber.trim();
        if (phoneNumber == null || phoneNumber.length() == 0) {
            return result;
        }
        String[] phoneNumbers = null;
        if (phoneNumber.contains(" ")) {
            phoneNumbers = phoneNumber.split(" ");
        } else if (phoneNumber.contains("-")) {
            phoneNumbers = phoneNumber.split("-");
        } else {
            return phoneNumber;
        }
        StringBuffer phoneNumberResult = new StringBuffer();
        for (int i = 0; phoneNumbers != null && i < phoneNumbers.length; i++) {
            phoneNumberResult.append(phoneNumbers[i]);
        }
        result = phoneNumberResult.toString();
        return result;
    }

    /**
     * 获取baseUrl(url的根地址)
     *
     * @param urlString
     * @return
     */
    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        Log.d(BaseConfig.LOG, "hoseName:" + head + urlString);
        return head + urlString;
    }

    /**
     * 获取格式化后的文件的大小
     *
     * @param var0
     * @return
     */
    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }


    /**
     * 将字符串转换为ASCII码
     *
     * @param value
     * @return
     */
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }


    /**
     * 将ASCII码转换为字符串
     *
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

}
