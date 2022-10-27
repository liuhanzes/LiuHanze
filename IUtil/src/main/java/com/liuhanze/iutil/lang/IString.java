package com.liuhanze.iutil.lang;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IString {

    public final static String EMPTY = "";

    private IString(){

    }

    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空格<br> {@code false}: 不为 null 且不全空格
     */
    public static boolean isEmptyTrim(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取String内容，去除所有空格
     *
     * @param s
     * @return
     */
    public static String getStringNoSpace(String s) {
        return isEmptyTrim(s) ? "" : replaceBlank(s);
    }

    /**
     * 过滤字符串中的空格
     *
     * @param str
     * @return
     */
    public static String replaceBlank(final String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 根据分隔符将String转换为List
     *
     * <p>例如:aa,bb,cc --> {"aa","bb","cc"}</p>
     *
     * @param str
     * @param separator 分隔符
     * @return
     */
    public static List<String> stringToList(final String str, final String separator) {
        return Arrays.asList(str.split(separator));
    }

    /**
     * 根据分隔符将List转换为String
     *
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(final List<String> list, final String separator) {
        if (list == null || list.size() == 0) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @return
     */
    public static int toInt(final String value) {
        return toInt(value, 0);
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static int toInt(final String value, final int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @return
     */
    public static float toFloat(final String value) {
        return toFloat(value, 0);
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static float toFloat(final String value, final float defValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }


    /**
     * String转Short（防止崩溃）
     *
     * @param value
     * @return
     */
    public static short toShort(final String value) {
        return toShort(value, (short) 0);
    }

    /**
     * String转Short（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static short toShort(final String value, final short defValue) {
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     * @return
     */
    public static long toLong(final String value) {
        return toLong(value, 0);
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static long toLong(final String value, final long defValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Double（防止崩溃）
     *
     * @param value
     * @return
     */
    public static double toDouble(final String value) {
        return toDouble(value, 0);
    }


    /**
     * String转Double（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static double toDouble(final String value, final double defValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }


    /**
     * 字符串连接，将参数列表拼接为一个字符串
     *
     * @param more 追加
     * @return 返回拼接后的字符串
     */
    public static String concat(Object... more) {
        return concatSpiltWith("", more);
    }

    /**
     * 字符串连接，将参数列表通过分隔符拼接为一个字符串
     *
     * @param split
     * @param more
     * @return 回拼接后的字符串
     */
    public static String concatSpiltWith(String split, Object... more) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < more.length; i++) {
            if (i != 0) {
                buf.append(split);
            }
            buf.append(toString(more[i]));
        }
        return buf.toString();
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    public static boolean isNumber(String str) {
        if (!IString.isEmpty(str)) {
            Matcher isNum = Pattern.compile("[0-9]*").matcher(str);
            return isNum.matches();
        } else {
            return false;
        }
    }
}
