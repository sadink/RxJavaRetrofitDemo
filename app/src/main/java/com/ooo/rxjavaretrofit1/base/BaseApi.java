package com.ooo.rxjavaretrofit1.base;

import com.ooo.rxjavaretrofit1.mode.entity.ResultBean;
import com.ooo.rxjavaretrofit1.mode.entity.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * API接口类
 * Created by lhfBoy on 2019/9/24 0024 10:07.
 */
public interface BaseApi {

    /**
     * 查询天气
     *
     * @param cityName
     * @param key
     * @return
     */
    @GET
    Observable<ResultBean<Weather>> queryWeather(@Url String url, @Query("cityname") String cityName, @Query("key") String key);

}
