package com.rjp.baselistviewtest.okhttp;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


/**
 * author：RJP on 2017/4/20 20:35
 * <p>
 * 请求统一接口
 * 默认不显示加载框
 */

public class NetUtils {
    public static final String TAG = "NetUtils";
//    public static boolean DEBUG = BuildConfig.DEBUG;
    public static boolean DEBUG = false;
    private static String DOMAIN = DEBUG ? "https://test.dajiang365.com/" : "https://filter.dajiang365.com/";  //测试线 外网：118.26.65.150 | 内网：192.168.2.237  正式线  https://filter.dajiang365.com/
    public static final String URL_LOT_SERVER_API = DOMAIN + "lotserver/api/request";
    public static final String URL_EURASIAN_API = DOMAIN + "sports/api/v1/"; // (新的欧亚析接口)
    public static final String CODE_OK = "0";

    private static final int TIME_OUT = 15000;

    private static final String KEY = "B4F9CED935M9419D";

    private NetUtils() {

    }

    private static NetUtils mInstance = new NetUtils();

    public static NetUtils getInstance() {
        return mInstance;
    }

    /**
     * 回调接口 需要两个回调
     */
    public interface NetCallBack {
        void onSuccess(String code, BaseModel result);

        void onError(String errorMsg);
    }

    /**
     * 回调接口  只需要一个回调
     */
    public interface NetCallBack1 {
        void onSuccess(String code, BaseModel result);
    }

    public void requestOrigin(final Context mContext, final String url, Map<String, String> params, final NetCallBack callBack) {
        final long t1 = System.currentTimeMillis();
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        for (String key : params.keySet()) {
            getBuilder.addParams(key, params.get(key));
        }

        getBuilder.build().connTimeOut(TIME_OUT).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (callBack != null) {
                    callBack.onError("网络错误");
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    long t2 = System.currentTimeMillis();

                    if (callBack == null) {
                        return;
                    }

                    OriginResponse data = com.alibaba.fastjson.JSONObject.parseObject(response, OriginResponse.class);
                    BaseModel baseModel = new BaseModel();
                    baseModel.setData(data.getData());
                    baseModel.setErrorcode(data.getStatus().equals("200") ? "0" : "-1");
                    baseModel.setMsg(data.getMsg());
                    if (baseModel.getErrorcode().equals("0")) {
                        long t3 = System.currentTimeMillis();
                        callBack.onSuccess(baseModel.getErrorcode(), baseModel);
                    } else {
                        callBack.onError(baseModel.getMsg());
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.onError("网络错误");
                    }
                }
            }
        });


    }

    /**
     * @param mContext 上下文
     * @param command  命令
     * @param callBack 一个回调
     */
    public void request1(Context mContext, String command, NetCallBack1 callBack) {
        request1(mContext, command, new JSONObject(), callBack);
    }

    /**
     * @param mContext 上下文
     * @param command  命令
     * @param params   参数
     * @param callBack 一个回调
     */
    public void request1(Context mContext, String command, JSONObject params, NetCallBack1 callBack) {
        request1(mContext, command, params, false, callBack);
    }

    /**
     * @param mContext          上下文
     * @param command           命令
     * @param params            参数
     * @param showLoadingDialog 是否显示请求框
     * @param callBack          一个回调
     */
    public void request1(Context mContext, String command, JSONObject params, boolean showLoadingDialog, NetCallBack1 callBack) {
        request1(mContext, URL_LOT_SERVER_API, command, params, TIME_OUT, showLoadingDialog, "", callBack);
    }

    /**
     * 只有一个回调的 最终请求地址
     *
     * @param mContext          上下文
     * @param requestUrl        请求地址
     * @param command           命令
     * @param params            参数
     * @param timeOut           超时时间
     * @param showLoadingDialog 是否显示请求框
     * @param loadingMessage    请求框文字
     * @param callBack          回调
     */
    public void request1(Context mContext, String requestUrl, String command, JSONObject params, long timeOut, boolean showLoadingDialog, String loadingMessage, final NetCallBack1 callBack) {
        request(mContext, requestUrl, command, params, timeOut, showLoadingDialog, loadingMessage, new NetCallBack() {
            @Override
            public void onSuccess(String code, BaseModel result) {
                callBack.onSuccess(code, result);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    /**
     * @param mContext 上下文
     * @param command  命令
     * @param callBack 两个回调
     */
    public void request(Context mContext, String command, NetCallBack callBack) {
        request(mContext, command, new JSONObject(), callBack);
    }


    /**
     * @param mContext 上下文
     * @param command  命令
     * @param params   参数
     * @param callBack 两个回调
     */
    public void request(Context mContext, String command, JSONObject params, NetCallBack callBack) {
        request(mContext, command, params, false, callBack);
    }

    /**
     * @param mContext          上下文
     * @param command           命令
     * @param params            参数
     * @param showLoadingDialog 是否显示请求框
     * @param callBack          两个回调
     */
    public void request(Context mContext, String command, JSONObject params, boolean showLoadingDialog, NetCallBack callBack) {
        request(mContext, URL_LOT_SERVER_API, command, params, TIME_OUT, showLoadingDialog, "", callBack);
    }


    /**
     * 最终请求地址
     *
     * @param mContext          上下文
     * @param requestUrl        请求地址
     * @param command           命令
     * @param params            参数
     * @param timeOut           超时时间
     * @param showLoadingDialog 是否显示请求框
     * @param loadingMessage    请求框文字
     * @param callBack          两个回调
     */
    public void request(final Context mContext, final String requestUrl, final String command, JSONObject params, long timeOut, final boolean showLoadingDialog, String loadingMessage, final NetCallBack callBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(requestUrl);
        postFormBuilder.addParams("body", getEncryptParams(mContext, command, params));
        RequestCall requestCall = postFormBuilder.build();
        requestCall.connTimeOut(timeOut).execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (callBack != null) {
                    callBack.onError("网络错误");
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    String result = decodeResponse(response);
                    Log.d("-------->", result);
                    BaseModel baseModel = com.alibaba.fastjson.JSONObject.parseObject(result, BaseModel.class);
                    if (callBack != null && baseModel != null) {
                        dealResponse(mContext, baseModel, callBack);
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.onError("网络错误");
                    }
                }
            }
        });
    }

    private void dealResponse(Context mContext, BaseModel baseModel, NetCallBack callBack) {// 判断是否登录（包含多设备登录修改密码）
        String code = baseModel.getErrorcode();
        callBack.onSuccess(code, baseModel);
        if (!"0".equals(code)) {
            callBack.onError(baseModel.getMsg());
        }
    }

    /**
     * 解密获取的数据
     */
    private String decodeResponse(String response) throws Exception {
        String decrypt;
        if (response.contains("\"")) {
            decrypt = ToolsAesCrypt.Decrypt(response.substring(1, response.length() - 1), KEY);
        } else {
            decrypt = AES.decrypt(response);
        }
        byte[] base64d = Base64.decode(decrypt);
        byte[] decompress = PublicMethod.decompress2(base64d);
        return new String(decompress, "utf-8");
    }

    /**
     * 获取加密的请求参数
     *
     * @param command
     * @param data
     * @return
     */
    private String getEncryptParams(Context context, String command, JSONObject data) {
        String encryptedParams = null;
        try {
            encryptedParams = ToolsAesCrypt.Encrypt(getUnEncryptParams(context, command, data).toString(), KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedParams;
    }

    /**
     * 未加密的请求参数
     *
     * @param command
     * @param data
     * @return
     */
    private JSONObject getUnEncryptParams(Context context, String command, JSONObject data) {
        JSONObject params = new JSONObject();
        try {
            params.put(RequestParams.CLIENT_INFO, getClientInfoParam(context, command));
            params.put(RequestParams.COMMAND, command);
            params.put(RequestParams.DATA, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    private JSONObject getClientInfoParam(Context context, String command) {
        long timestamp = System.currentTimeMillis();
        JSONObject clientInfo = new JSONObject();
        try {
            clientInfo.put(RequestParams.IMEI, MyUtils.getIMEI(context));
            clientInfo.put(RequestParams.OSVERSION, Build.VERSION.RELEASE); // Build.VERSION.RELEASE
            clientInfo.put(RequestParams.MAC, MyUtils.getMobileMAC(context));
            clientInfo.put(RequestParams.JPUSH_ID, ""); //JPushInterface.getRegistrationID(App.getContext())
//            String clientid = PushManager.getInstance().getClientid(App.getContext());
            clientInfo.put(RequestParams.GEPUSH_ID, TextUtils.isEmpty("") ? "" : "");
            clientInfo.put(RequestParams.BRAND, Build.BRAND);
            clientInfo.put(RequestParams.MODEL, Build.MODEL);
            clientInfo.put(RequestParams.PLATFORM, "android");
            clientInfo.put(RequestParams.APP, "DaJiang365");
            clientInfo.put(RequestParams.APN, NetUtil.getNetworkType(context));
            clientInfo.put(RequestParams.VERSION, "1.8.0");
            clientInfo.put(RequestParams.AGENT_ID, "agent_huawei");
            clientInfo.put(RequestParams.AUTH_TOKEN, "");
            clientInfo.put(RequestParams.TIMESTAMP, timestamp);
            clientInfo.put(RequestParams.TOEKN, MD5.toMd5(MD5.toMd5((KEY + timestamp).getBytes()).getBytes()));
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO: 需要统计未收集到手机信息的异常 by RJP
        }
        return clientInfo;
    }
}
