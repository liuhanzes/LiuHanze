package com.liuhanze.demos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.liuhanze.demos.bean.Sheep;
import com.liuhanze.iutil.data.ISharedPreferences;
import com.liuhanze.iutil.lang.IString;
import com.liuhanze.iutil.log.ILog;
import com.liuhanze.iutil.net.INetWork;
import com.liuhanze.iutil.toast.IToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sheep sheep = new Sheep();
        sheep.setAge(12);
        sheep.setName("花花");
        ISharedPreferences.putObject("huahua",sheep);

        ILog.LogDebug("哈哈哈哈");
        IToast.shortToast("哈哈哈");
        ILog.LogDebug("下面输出花花");
        Sheep huahua = ISharedPreferences.getObject("huahua",Sheep.class);
        ILog.LogDebug(IString.concat("花花年龄 = ",huahua.getAge(),"花花名字 = ",huahua.getName()));

        ISharedPreferences.put("one","哈哈");
        ILog.LogDebug(IString.concat("one text",ISharedPreferences.get("one","kong")));
    }
}