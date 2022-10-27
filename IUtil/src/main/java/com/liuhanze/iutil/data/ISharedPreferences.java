package com.liuhanze.iutil.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.liuhanze.iutil.IUtil;
import com.liuhanze.iutil.file.IFile;
import com.liuhanze.iutil.lang.IString;

import java.util.Map;

public final class ISharedPreferences {

    private ISharedPreferences(){
    }

    /**
     * 获取默认SharedPreferences实例
     * @return
     */
    public static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(IUtil.getContext());
    }

    /**
     * 获取SharedPreferences实例
     * @param spName
     * @return
     */
    public static SharedPreferences getSharedPreferences(String spName) {
        return IUtil.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public static <T extends Object> T get(String key, T defaultValue,SharedPreferences sp) {
        try {
            if(sp == null) sp = getDefaultSharedPreferences();

            if (defaultValue instanceof String) {
                return (T) sp.getString(key, (String) defaultValue);
            } else if (defaultValue instanceof Integer) {
                return (T)((Object)sp.getInt(key, (Integer) defaultValue));
            } else if (defaultValue instanceof Boolean) {
                return  (T)((Object)sp.getBoolean(key, (Boolean) defaultValue));
            } else if (defaultValue instanceof Float) {
                return  (T)((Object)sp.getFloat(key, (Float) defaultValue));
            } else if (defaultValue instanceof Long) {
                return (T) ((Object)sp.getLong(key, (Long) defaultValue));
            }else {
                throw new IllegalArgumentException("not support type get from Preferences ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 获取保存的参数
     * 目前只支持获取 String , Integer , Boolean , Float,Long类型
     * @param key
     * @param defaultValue
     * @return
     */
    public static <T> T get(String key, T defaultValue) {
        return get(key,defaultValue,null);
    }

    /**
     * 保存参数
     * 目前只支持获取 String , Integer , Boolean , Float,Long类型
     * @param sp SharedPreferences实例
     * @param key
     * @param object
     */
    public static boolean put(SharedPreferences sp, String key, Object object) {

        if(sp == null) sp = getDefaultSharedPreferences();

        if (object instanceof String) {
            return sp.edit().putString(key, (String) object).commit();
        } else if (object instanceof Integer) {
            return sp.edit().putInt(key, (Integer) object).commit();
        } else if (object instanceof Boolean) {
            return sp.edit().putBoolean(key, (Boolean) object).commit();
        } else if (object instanceof Float) {
            return sp.edit().putFloat(key, (Float) object).commit();
        } else if (object instanceof Long) {
            return sp.edit().putLong(key, (Long) object).commit();
        } else {
            throw new IllegalArgumentException("This type can not saved into Preferences ");
        }
    }

    /**
     * 保存参数
     * @param key
     * @param object
     */
    public static boolean put(String key, Object object) {
        return put(null,key,object);
    }

    /**
     * 去除某一键值对
     * @param sp
     * @param key
     * @return
     */
    public static boolean remove(SharedPreferences sp, String key) {
        if(sp == null) sp = getDefaultSharedPreferences();
        return sp.edit().remove(key).commit();
    }

    /**
     * 去除某一键值对
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        return remove(null,key);
    }

    /**
     * 清空保存的参数
     * @param sp SharedPreferences实例
     */
    public static boolean clear(SharedPreferences sp) {
        if(sp == null) sp = getDefaultSharedPreferences();
        return sp.edit().clear().commit();
    }

    /**
     * 清空保存的参数
     */
    public static boolean clear() {
        return clear(null);
    }

    /**
     * 查询某个key是否已经存在
     * @param sp SharedPreferences实例
     * @param key
     * @return
     */
    public static boolean contains(SharedPreferences sp, String key) {
        if(sp == null) sp = getDefaultSharedPreferences();
        return sp.contains(key);
    }

    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return contains(null,key);
    }

    /**
     * 返回所有的键值对
     * @param sp SharedPreferences实例
     * @return
     */
    public static Map<String, ?> getAll(SharedPreferences sp) {
        try {
            if(sp == null) sp = getDefaultSharedPreferences();
            return sp.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        return getAll(null);
    }

    /**
     * 保存对象
     * @param name
     * @param object
     * @param <T>
     * @return
     */
    public static <T> boolean putObject(String name,T object){
        try {
            SharedPreferences sp = getSharedPreferences(name);
            SharedPreferences.Editor editor = sp.edit();
            String string = new Gson().toJson(object);
            editor.putString(object.getClass().getName(),string);
            editor.commit();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 读取保存的对象数据
     * @param name
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T getObject(String name,Class<T> object){
        T thisObject = null;
        try {
            SharedPreferences sp = getSharedPreferences(name);
            String string = sp.getString(object.getName(),null);
            if(!IString.isEmpty(string)){
                thisObject = new Gson().fromJson(string,object);
            }
        }catch (Exception e){
            return null;
        }
        return thisObject;
    }
}
