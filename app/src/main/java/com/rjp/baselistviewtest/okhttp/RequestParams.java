package com.rjp.baselistviewtest.okhttp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 类名称：RequestParams
 * 类描述：请求的参数
 *
 * @version: 1.6.2
 * @author: Charlie
 * @time: 2016/9/6 18:46
 */
public class RequestParams {
    public static final String IMEI = "imei";
    public static final String OSVERSION = "osversion";
    public static final String MAC = "mac";
    public static final String JPUSH_ID = "jpushId";
    public static final String GEPUSH_ID = "gtpushId";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String PLATFORM = "platform";
    public static final String APP = "app";
    public static final String APN = "apn";
    public static final String VERSION = "version";
    public static final String AGENT_ID = "agentId";
    public static final String CHANNEL = "channel";
    public static final String AUTH_TOKEN = "authtoken";
    public static final String TIMESTAMP = "timestamp";

    public static final String TOEKN = "token";
    // 请求的参数
    public static final String CLIENT_INFO = "clientInfo";
    public static final String COMMAND = "command";
    public static final String DATA = "data";


    public static String getChannel(Context context) {
        String agent;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            agent = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            agent = "gw";
            e.printStackTrace();
        }
        return agent;
    }
}
