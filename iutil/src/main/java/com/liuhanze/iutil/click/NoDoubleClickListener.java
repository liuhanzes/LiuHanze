package com.liuhanze.iutil.click;

import android.view.View;
import android.widget.CheckBox;

import java.util.Calendar;

/**
 * 防止频繁点击
 */
public abstract class NoDoubleClickListener implements View.OnClickListener{
    public int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public NoDoubleClickListener(int nullTime){
        this.MIN_CLICK_DELAY_TIME = nullTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }else if(v instanceof CheckBox){
            ((CheckBox) v).setChecked(!((CheckBox) v).isChecked());
        }
    }

    public abstract void onNoDoubleClick(View v);
}
