package com.liuhanze.iutil.net;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import com.liuhanze.iutil.IUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class INetWork {

    /**
     * 获取获取连接管理者ConnectivityManager
     *
     * @return 获取连接管理者
     */
    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) IUtil.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取WifiManager
     *
     * @return
     */
    public static WifiManager getWifiManager() {
        return (WifiManager) IUtil.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 判断网络连接是否打开,包括移动数据连接
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return 是否联网
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        boolean netState = false;
        ConnectivityManager manager = getConnectivityManager();
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        netState = true;
                        break;
                    }
                }
            }
        }
        return netState;
    }

    /**
     * 判断当前是否有网
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: 有网<br>{@code false}: 无网
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isHaveInternet() {
        try {
            ConnectivityManager connectivity = getConnectivityManager();
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Gps是否打开
     * 需要<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />权限
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(ACCESS_FINE_LOCATION)
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) IUtil.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取活动网络信息
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return NetworkInfo
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager = getConnectivityManager();
        if (manager == null) {
            return null;
        }
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isMobile() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前打开的网络类型是否是WIFI
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: connected<br>{@code false}: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifi() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 检测当前打开的网络类型是否是3G
     *
     * @return 是否是3G上网
     */
    public static boolean is3G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前开打的网络类型是否是4G
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return 是否是4G上网
     */
    public static boolean is4G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting() && activeNetInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断 wifi 是否打开
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isWifiEnabled() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = getWifiManager();
        return manager != null && manager.isWifiEnabled();
    }

    /**
     * 打开或关闭 wifi
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    @SuppressLint("MissingPermission")
    public static void setWifiEnabled(final boolean enabled) {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = getWifiManager();
        if (manager == null) {
            return;
        }
        if (enabled) {
            if (!manager.isWifiEnabled()) {
                manager.setWifiEnabled(true);
            }
        } else {
            if (manager.isWifiEnabled()) {
                manager.setWifiEnabled(false);
            }
        }
    }

    /**
     * 判断移动数据是否打开
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean getMobileDataEnabled() {
        try {
            TelephonyManager tm =
                    (TelephonyManager) IUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return false;
            }
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 枚举网络状态  NET_NO：没有网络 , NET_2G:2g网络 , NET_3G：3g网络, NET_4G：4g网络, NET_5G：5g网络, NET_WIFI：wifi, NET_ETHERNET：有线网络, NET_UNKNOWN：未知网络
     */
    public enum NetState {
        /**
         * 没有网络
         */
        NET_NO,
        /**
         * 2g网络
         */
        NET_2G,
        /**
         * 3g网络
         */
        NET_3G,
        /**
         * 4g网络
         */
        NET_4G,
        /**
         * 5g网络
         */
        NET_5G,
        /**
         * wifi
         */
        NET_WIFI,
        /**
         * 有线网络
         */
        NET_ETHERNET,
        /**
         * 未知网络
         */
        NET_UNKNOWN
    }

    /**
     * 是否是以太网
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isEthernet() {
        final ConnectivityManager cm = getConnectivityManager();
        if (cm == null) {
            return false;
        }
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) {
            return false;
        }
        NetworkInfo.State state = info.getState();
        if (null == state) {
            return false;
        }
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }


    /**
     * 判断当前是否网络连接,返回当前网络状态的类型
     * * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return type of network
     * <ul>
     * <li>{@link NetState#NET_WIFI   } </li>
     * <li>{@link NetState#NET_5G     } </li>
     * <li>{@link NetState#NET_4G     } </li>
     * <li>{@link NetState#NET_3G     } </li>
     * <li>{@link NetState#NET_2G     } </li>
     * <li>{@link NetState#NET_UNKNOWN} </li>
     * <li>{@link NetState#NET_NO     } </li>
     * </ul>
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static NetState getNetStateType() {
        if (isEthernet()) {
            return NetState.NET_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetState.NET_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetState.NET_2G;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetState.NET_3G;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetState.NET_4G;

                    case TelephonyManager.NETWORK_TYPE_NR:
                        return NetState.NET_5G;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetState.NET_3G;
                        } else {
                            return NetState.NET_UNKNOWN;
                        }
                }
            } else {
                return NetState.NET_UNKNOWN;
            }
        }
        return NetState.NET_NO;
    }


    /**
     * 获取URL中参数 并返回Map
     *
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> params = null;
        try {
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                params = new HashMap<>();
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }
                    params.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return params;
    }

    /**
     * 解析网络请求的url
     * 解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
     * 解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
     *
     * @param url
     * @return
     */
    public static String parseUrl(String url) {
        if (!"".equals(url) && url.contains("?")) {// 如果URL不是空字符串
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }


    /**
     * url是否有效合法
     *
     * @param url 匹配 http://www.allkins.com | http://255.255.255.255 | http://allkins.com/page.asp?action=1
     *            不匹配 http://test.testing
     * @return
     */
    public static boolean isUrlValid(String url) {
        String expr = "^(http|https|ftp)\\://(((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])|([a-zA-Z0-9_\\-\\.])+\\.(com|cn|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|uk|me))((:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&%\\$#\\=~])*)$";
        return url.matches(expr);
    }

    /**
     * IP地址校验
     *
     * @param ip 待校验是否是IP地址的字符串
     * @return 是否是IP地址
     */
    public static boolean isIP(String ip) {
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


}
