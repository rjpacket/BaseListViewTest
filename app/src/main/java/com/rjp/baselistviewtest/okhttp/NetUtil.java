package com.rjp.baselistviewtest.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by afs on 2015/3/14.
 * 网络类型
 */
public class NetUtil {

    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    /**
     * wap网络
     */
    public static final int NETWORKTYPE_WAP = 1;
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORKTYPE_3G = 3;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 4;


    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},
     * *{@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}* <p>{@link #NETWORKTYPE_WIFI}
     */
    public static int getNetWorkType(Context context) {

        int mNetWorkType = NETWORKTYPE_INVALID;

        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                String type = networkInfo.getTypeName();
                if (!TextUtils.isEmpty(type)) {
                    if (type.equalsIgnoreCase("WIFI")) {
                        mNetWorkType = NETWORKTYPE_WIFI;
                    } else if (type.equalsIgnoreCase("MOBILE")) {
                        String proxyHost = android.net.Proxy.getDefaultHost();
                        mNetWorkType = TextUtils.isEmpty(proxyHost)
                                ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                                : NETWORKTYPE_WAP;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mNetWorkType = NETWORKTYPE_WIFI;
        }

        return mNetWorkType;
    }

    public static String getNetWorkTypeStr(Context context) {
        int type = getNetWorkType(context);
        switch (type) {
            case NETWORKTYPE_2G:
                return "2G";
            case NETWORKTYPE_3G:
                return "3G";
            case NETWORKTYPE_INVALID:
                return "None";
            case NETWORKTYPE_WAP:
                return "WAP";
            case NETWORKTYPE_WIFI:
                return "WIFI";
            default:
                return "INVALID";
        }
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        return getNetWorkType(context) > NETWORKTYPE_INVALID;
    }


    /**
     * 获取网络连接类型
     */
    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getTypeName();
            }
        }catch (Exception e){}
        return null;
    }

    /**
     * 获取mac地址
     */
    public static String getMacAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } // for now eat exceptions
        return "";
    }

    /**
     * 获取手机设备IMEI号码（唯一）
     */
    public static String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
