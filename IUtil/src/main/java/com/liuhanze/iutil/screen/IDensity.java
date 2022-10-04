package com.liuhanze.iutil.screen;

import android.content.Context;
import android.util.DisplayMetrics;

import com.liuhanze.iutil.IUtil;

public final class IDensity {

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(IUtil.getContext());
    }

    /**
     * 获取屏幕密度
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(float dpValue) {
        return dip2px(IUtil.getContext(), dpValue);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 尺寸像素
     * @return DIP值
     */
    public static int px2dip(float pxValue) {
        return px2dip(IUtil.getContext(), pxValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @param pxValue 尺寸像素
     * @return DIP值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param spValue SP值
     * @return 像素值
     */
    public static int sp2px(float spValue) {
        return sp2px(IUtil.getContext(), spValue);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @param spValue SP值
     * @return 像素值
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param pxValue 尺寸像素
     * @return SP值
     */
    public static int px2sp(float pxValue) {
        return px2sp(IUtil.getContext(), pxValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @param pxValue 尺寸像素
     * @return SP值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕分辨率
     *
     * @return 屏幕分辨率幕高度
     */
    public static int getScreenDpi() {
        return getDisplayMetrics().densityDpi;
    }

    /**
     * 获取真实屏幕密度
     *
     * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
     * @return
     */
    public static int getRealDpi(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        float xdpi = metric.xdpi;
        float ydpi = metric.ydpi;

        return (int) (((xdpi + ydpi) / 2.0F) + 0.5F);
    }


    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity() {
        return getDisplayMetrics().density;
    }

}
