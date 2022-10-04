package com.liuhanze.iutil.date;

import static com.liuhanze.iutil.date.TimeConstants.DAY;
import static com.liuhanze.iutil.date.TimeConstants.HOUR;
import static com.liuhanze.iutil.date.TimeConstants.MIN;
import static com.liuhanze.iutil.date.TimeConstants.SEC;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.liuhanze.iutil.lang.IString;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IDate {
    /**
     * yyyy-MM-dd
     */
    public static final String yyyyMMdd = "yyyy-MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String yyyyMMddNoSep = "yyyyMMdd";
    /**
     * HH:mm:ss
     */
    public static final String HHmmss = "HH:mm:ss";
    /**
     * HH:mm
     */
    public static final String HHmm = "HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String yyyyMMddHHmmssNoSep = "yyyyMMddHHmmss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final String yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss:SSS";

    public final static String EMPTY = "";
    /**
     * 年，单位【s】
     */
    private static final int YEAR_S = 365 * 24 * 60 * 60;
    /**
     * 月，单位【s】
     */
    private static final int MONTH_S = 30 * 24 * 60 * 60;
    /**
     * 天，单位【s】
     */
    private static final int DAY_S = 24 * 60 * 60;
    /**
     * 小时，单位【s】
     */
    private static final int HOUR_S = 60 * 60;
    /**
     * 分钟，单位【s】
     */
    private static final int MINUTE_S = 60;

    /**
     * yyyy-MM-dd
     */
    public static final ThreadLocal<DateFormat> yyyyMMddFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMdd);
        }
    };
    /**
     * yyyyMMdd
     */
    public static final ThreadLocal<DateFormat> yyyyMMddNoSepFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddNoSep);
        }
    };
    /**
     * HH:mm:ss
     */
    public static final ThreadLocal<DateFormat> HHmmssFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(HHmmss);
        }
    };
    /**
     * HH:mm
     */
    public static final ThreadLocal<DateFormat> HHmmFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(HHmm);
        }
    };
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmssFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmmss);
        }
    };
    /**
     * yyyyMMddHHmmss
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmssNoSepFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmmssNoSep);
        }
    };
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmm);
        }
    };
    /**
     * yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmssSSSFormat = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(yyyyMMddHHmmssSSS);
        }
    };

    /**
     * 将时间戳转为时间字符串
     * <p>格式为 format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return date2String(new Date(millis), format);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            if (format != null) {
                return format.parse(time).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date Date 类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date != null ? date.getTime() : -1;
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis 毫秒时间戳
     * @return Date 类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        if (format != null) {
            return format.format(date);
        } else {
            return EMPTY;
        }
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date 类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            if (format != null) {
                return format.parse(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间字符串转换为文件名称
     *
     * @param dateTime 时间字符串
     * @param suffix   文件名后缀
     * @return
     */
    @Nullable
    public static String convertTimeToFileName(String dateTime, String suffix) {
        if (IString.isEmpty(dateTime)) {
            return null;
        }
        Pattern p = Pattern.compile("[^\\d]+");
        Matcher m = p.matcher(dateTime);
        return m.replaceAll("").trim() + suffix;
    }

    /**
     * 根据 precision 获取时间精度
     * @param millis    时间戳
     * @param precision 精度
     * @return
     */
    private static String millis2FitTimeSpan(long millis, int precision) {
        if (millis < 0 || precision <= 0) {
            return null;
        }
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) {
            return 0 + units[precision - 1];
        }
        StringBuilder sb = new StringBuilder();
        int[] unitLen = {DAY, HOUR, MIN, SEC, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0 和 time1 格式都为 format</p>
     *
     * @param time0  时间字符串 0
     * @param time1  时间字符串 1
     * @param format 时间格式
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return 时间戳
     */
    public static long getTimeSpan(final String time0,
                                   final String time1,
                                   final DateFormat format,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(
                Math.abs(string2Millis(time0, format) - string2Millis(time1, format)), unit
        );
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param date0 Date 类型时间 0
     * @param date1 Date 类型时间 1
     * @param unit  单位类型
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *              <li>{@link TimeConstants#SEC }: 秒</li>
     *              <li>{@link TimeConstants#MIN }: 分</li>
     *              <li>{@link TimeConstants#HOUR}: 小时</li>
     *              <li>{@link TimeConstants#DAY }: 天</li>
     *              </ul>
     * @return 时间戳
     */
    public static long getTimeSpan(final Date date0,
                                   final Date date1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param millis0 毫秒时间戳 0
     * @param millis1 毫秒时间戳 1
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstants#SEC }: 秒</li>
     *                <li>{@link TimeConstants#MIN }: 分</li>
     *                <li>{@link TimeConstants#HOUR}: 小时</li>
     *                <li>{@link TimeConstants#DAY }: 天</li>
     *                </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpan(final long millis0,
                                   final long millis1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * 获取合适型两个时间差
     * <p>time0 和 time1 格式都为 format</p>
     *
     * @param time0     时间字符串 0
     * @param time1     时间字符串 1
     * @param format    时间格式
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final String time0,
                                        final String time1,
                                        final DateFormat format,
                                        final int precision) {
        long delta = string2Millis(time0, format) - string2Millis(time1, format);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param date0     Date 类型时间 0
     * @param date1     Date 类型时间 1
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final Date date0, final Date date1, final int precision) {
        return millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳 1
     * @param millis1   毫秒时间戳 2
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final long millis0,
                                        final long millis1,
                                        final int precision) {
        return millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final String time,
                                        final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(format), time, format, unit);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param date Date 类型时间
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstants#SEC }: 秒</li>
     *             <li>{@link TimeConstants#MIN }: 分</li>
     *             <li>{@link TimeConstants#HOUR}: 小时</li>
     *             <li>{@link TimeConstants#DAY }: 天</li>
     *             </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants.Unit final int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * 获取合适型与当前时间的差
     * <p>time 格式为 format</p>
     *
     * @param time      时间字符串
     * @param format    时间格式
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final String time,
                                             final DateFormat format,
                                             final int precision) {
        return getFitTimeSpan(getNowString(format), time, format, precision);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param date      Date 类型时间
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(getNowDate(), date, precision);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param millis    毫秒时间戳
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * 获取当前 Date
     *
     * @return Date 类型时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>格式为 format</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 获取友好型与当前时间的差
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time, final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param date Date 类型时间
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tc", millis);
        }
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final long timestamp) {
        long currentTime = System.currentTimeMillis();
        // 与现在时间相差秒数
        long timeGap = (currentTime - timestamp) / 1000;
        String timeStr;
        long span;
        if ((span = Math.round((float) timeGap / YEAR_S)) > 0) {
            timeStr = span + "年前";
        } else if ((span = Math.round((float) timeGap / MONTH_S)) > 0) {
            timeStr = span + "个月前";
        } else if ((span = Math.round((float) timeGap / DAY_S)) > 0) {// 1天以上
            timeStr = span + "天前";
        } else if ((span = Math.round((float) timeGap / HOUR_S)) > 0) {// 1小时-24小时
            timeStr = span + "小时前";
        } else if ((span = Math.round((float) timeGap / MINUTE_S)) > 0) {// 1分钟-59分钟
            timeStr = span + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final String time, final DateFormat format) {
        return getFuzzyTimeDescriptionByNow(string2Millis(time, format));
    }

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param date Date 类型时间
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final Date date) {
        return getFuzzyTimeDescriptionByNow(date.getTime());
    }
    /**
     * 判断是否今天
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final String time, final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 判断是否今天
     *
     * @param date Date 类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 判断是否今天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + DAY;
    }

    /**
     * 得到年
     *
     * @param date Date对象
     * @return 年
     */
    public static int getYear(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到月
     *
     * @param date Date对象
     * @return 月
     */
    public static int getMonth(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到日
     *
     * @param date Date对象
     * @return 日
     */
    public static int getDay(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

}
