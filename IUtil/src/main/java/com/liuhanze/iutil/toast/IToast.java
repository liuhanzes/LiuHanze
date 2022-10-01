package com.liuhanze.iutil.toast;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.liuhanze.iutil.IUtil;
import com.liuhanze.iutil.R;

public final class IToast {

    private static Toast sToast = null;

    public static void longToast(final String text){
        toast(text,Toast.LENGTH_LONG);
    }

    public static void shortToast(final String text){
        toast(text,Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast在主线程中
     *
     * @param text     提示信息
     * @param duration 提示长度
     */
    private static void toast(final String text, final int duration) {
        if (isMainLooper()) {
            showToast(text, duration);
        } else {
            IUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(text, duration);
                }
            });
        }
    }

    /**
     * 显示单一的toast
     *
     * @param text     提示信息
     * @param duration 提示长度
     */
    private static void showToast(String text, int duration) {
        if (sToast == null) {
            sToast = makeText(IUtil.getContext(), text, duration);
        } else {
            ((TextView) sToast.getView().findViewById(R.id.tv_info)).setText(text);
        }
        sToast.show();
    }

    /**
     * 构建Toast
     *
     * @param context
     * @param msg
     * @param duration
     * @return
     */
    private static Toast makeText(Context context, String msg, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.iutil_layout_toast, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv = view.findViewById(R.id.tv_info);
        if (tv != null) {
            tv.setText(msg);
            if (tv.getBackground() != null) {
                tv.getBackground().setAlpha(100);
            }
        }
        toast.setDuration(duration);
        return toast;
    }


    /**
     * 是否是主线程
     *
     * @return
     */
    private static boolean isMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 取消toast显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}
