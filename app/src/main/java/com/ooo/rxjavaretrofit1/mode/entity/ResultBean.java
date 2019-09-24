package com.ooo.rxjavaretrofit1.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 同服务器接口交互中的数据仓库格式定义
 * Created by lhfBoy 2017/10/11 16:18
 */

public class ResultBean<T> implements Serializable {
    public static final int RESULT_SUCCESS = 200;
    @SerializedName("error_code")
    private int code;
    @SerializedName("reason")
    private String msg;
    @SerializedName("result")
    private T repData;


    public ResultBean() {
    }

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.repData = data;
    }

    public static int getResultSuccess() {
        return RESULT_SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRepData() {
        return repData;
    }

    public void setRepData(T repData) {
        this.repData = repData;
    }
}
