package com.liuhanze.demos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.liuhanze.iutil.log.ILogger;
import com.liuhanze.iutil.toast.IToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ILogger.LogDebug("哈哈哈哈");
        IToast.shortToast("哈哈哈");
    }
}