package com.ooo.rxjavaretrofit1.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ooo.rxjavaretrofit1.R;
import com.ooo.rxjavaretrofit1.base.BaseApi;
import com.ooo.rxjavaretrofit1.base.BaseConfig;
import com.ooo.rxjavaretrofit1.mode.entity.ResultBean;
import com.ooo.rxjavaretrofit1.mode.entity.Weather;
import com.ooo.rxjavaretrofit1.utils.DataFormatUtils;
import com.ooo.rxjavaretrofit1.utils.glide.GlideUtils;
import com.ooo.rxjavaretrofit1.utils.net.RRWebHelper;
import com.ooo.rxjavaretrofit1.utils.net.SubscriberSi;
import com.ooo.rxjavaretrofit1.utils.toast.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private ImageView ivUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivUrl = findViewById(R.id.iv_url);
        tvResult = findViewById(R.id.tv_result);


        GlideUtils.loadImage(this,ivUrl,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569305014310&di=cedd6fb710fec1431f127b340911aba5&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201504%2F03%2F20150403155458_wYK82.jpeg");
    }

    /**
     * @param view
     */
    public void onClickQueryWeather(View view) {

        RRWebHelper.RequestServer(
                this,
                RRWebHelper.getCgwApi(BaseApi.class).queryWeather(BaseConfig.URL_WEATHER, "西安", BaseConfig.KEY_WEATHER),
                new SubscriberSi<ResultBean<Weather>>(this) {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showToast(MainActivity.this,"" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultBean<Weather> weatherResultBean) {
                        super.onNext(weatherResultBean);
                        tvResult.setText(DataFormatUtils.GsonString(weatherResultBean));
                    }
                }
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RRWebHelper.closeSubscribe();
    }
}
