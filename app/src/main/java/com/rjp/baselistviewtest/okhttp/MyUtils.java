package com.rjp.baselistviewtest.okhttp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by anfs on 16/7/19.
 */
public class MyUtils {

    public static void visible(boolean b, View view) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

    /**
     * 隐藏真是手机号 替换成 ‘*’ 号
     *
     * @param phone
     *
     * @return
     */
    public static String hideCardId(String phone) {
        char[] charName = phone.toCharArray();
        for (int i = 0; i < charName.length; i++) {
            if (i > 3 && i < charName.length - 4) {
                charName[i] = '*';
            }
        }
        return String.valueOf(charName);
    }


    /**
     * 隐藏姓名中间几个字替换成 ‘*’ 号
     *
     * @param name
     *
     * @return
     */
    public static String hideMiddleRealName(String name) {
        char[] charName = name.toCharArray();
        for (int i = 0; i < charName.length; i++) {
            if (i > 0) {
                charName[i] = '*';
            }
        }
        return String.valueOf(charName);
    }

    /**
     * 隐藏真是身份证号码 替换成 ‘*’ 号
     *
     * @param certificateID
     *
     * @return
     */
    public static String hideCertificateID(String certificateID) {
        char[] charName = certificateID.toCharArray();
        for (int i = 0; i < charName.length; i++) {
            if (i > 3 && i < charName.length - 4) {
                charName[i] = '*';
            }
        }
        return String.valueOf(charName);
    }

    /**
     * 获取手机 imei
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 兼容Android 6.0以上设备获取MAC地址
     * 物理地址、硬件地址，用来定义网络设备的位置
     * 兼容原因：从android 6.0之后，android 移除了通过 WiFi 和蓝牙 API 来在应用程序中可编程的访问本地硬件标示符。现在 WifiInfo.getMacAddress() 和 BluetoothAdapter.getAddress() 方法都将返回 02:00:00:00:00:00
     * add by heliquan at 2017年6月12日
     *
     * @return
     */
    public static String getMobileMAC(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 如果当前设备系统大于等于6.0 使用下面的方法
            String heightMac = getHeightMac();
            return heightMac;
        } else {
            String lowerMac = getLowerMac(context);
            return lowerMac;
        }
    }

    /**
     * 兼容Android 6.0以下设备获取MAC地址 Android API < 23
     *
     * @return
     */
    private static String getLowerMac(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // 获取MAC地址
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String mac = wifiInfo.getMacAddress();
            if (null == mac) {
                // 未获取到
                mac = "";
            }
            return mac;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取高版本手机的MAC地址 Android API >= 23
     *
     * @return
     */
    private static String getHeightMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                return getMacAddr();
            }
        }
        return macSerial;
    }

    /**
     * 获取mac地址   7.0以后能不能获取  未知
     * @return
     */
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {

        }
        return "";
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    public static String dateHHMM(long dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static boolean restVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return true;
        } else {
            view.setVisibility(View.VISIBLE);
            return false;
        }
    }

    public static String trim(String code) {
        return TextUtils.isEmpty(code) || code.length() <= 1 ? code : code.substring(0, code.length() - 1);
    }

    public static String String2Decimal(String amount) {
        BigDecimal b = new BigDecimal(amount);
        return new DecimalFormat("0.00").format(b);
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @return
     */
    public static String getIndexOfWeek(String dateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return dateString;
        }
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }
}
