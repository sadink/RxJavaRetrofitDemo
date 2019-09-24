package com.ooo.rxjavaretrofit1.utils.net.ssl;

import android.util.Log;

import com.ooo.rxjavaretrofit1.base.BaseConfig;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 实现HostnameVerifier接口，不进行url和服务器主机名的验证。
 * Created by lhfBoy 2016/11/8 10:52
 */
public class VerifyEverythingHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        Log.d(BaseConfig.LOG, "url和服务器主机名验证s:" + s);
        Log.d(BaseConfig.LOG, "url和服务器主机名验证sslSession_PeerHost:" + sslSession.getPeerHost() + ",Protocol:" + sslSession.getProtocol() + ",CipherSuite:" + sslSession.getCipherSuite());
        return true;
    }
}
