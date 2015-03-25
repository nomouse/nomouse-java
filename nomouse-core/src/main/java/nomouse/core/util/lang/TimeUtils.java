package nomouse.core.util.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author nomouse
 */
public class TimeUtils {

    /**
     * 日期格式精简模式（20100909090909）
     */
    public static final SimpleDateFormat DATETIME_SIMPLIFY = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    /**
     * 日期格式到秒-（2010/9/9 9:9:9）
     */
    public static final SimpleDateFormat DATETIME_TO_SECOND = new SimpleDateFormat(
            "yyyy/M/d H:m:s");

    /**
     * 日期格式到秒-（2010/9/9 09:09:09）
     */
    public static final SimpleDateFormat DATETIME_TO_SECOND2 = new SimpleDateFormat(
            "yyyy/M/d HH:mm:ss");

    /**
     * 日期格式到分-（2010/09/09 09:09）
     */
    public static final SimpleDateFormat DATETIME_TO_MINUTE = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

    /**
     * 日期格式月日-（9月9日）
     */
    public static final SimpleDateFormat DATE_MONTH_DAY = new SimpleDateFormat(
            "M月d日");

    /**
     * 日期格式年月日-（9月9日）
     */
    public static final SimpleDateFormat DATE_YEAR_MONTH_DAY = new SimpleDateFormat(
            "yyyy年M月d日");

    /**
     * 日期格式到日期-（2010/09/09）
     */
    public static final SimpleDateFormat DATE_TO_DAY = new SimpleDateFormat(
            "yyyy-MM-dd");

    /**
     * 时间格式到分（9:09）
     */
    public static final SimpleDateFormat TIME_TO_MINITE = new SimpleDateFormat(
            "h:mm");

    // 60秒
    private final static int MINUTE_SECONDS = 60;
    // 60分钟
    private final static int HOUR_SECONDS = 3600;
    // 24小时
    private final static int DAY_SECONDS = 86400;
    // 2天
    private final static int TWO_DAY_SECONDS = 86400 * 2;
    // 3天
    private final static int THREE_DAY_SECONDS = 86400 * 3;

    // 一周
    // private final static int WEEK_SECONDS = 86400 * 7;

    // 昨天
    private final static String ONE_DAY_BEFORE = "昨天";

    // 昨天
    private final static String TWO_DAY_BEFORE = "前天";

    // 凌晨
    private final static String DAWN = "凌晨";

    // 晚上
    private final static String NIGHT = "晚上";

    // 早上
    private final static String AM = "早上";

    // 下午
    private final static String PM = "下午";

    // 未知时间
    private final static String UNKNOWN = "";

    // 星期几
    private final static String[] WEEKDAYS = {"星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六"};

    /**
     * 将字符串转换时间格式，如果转换失败返回null
     *
     * @param sdf
     * @param time
     * @return
     */
    public static Date parseDate(SimpleDateFormat sdf, String time) {
        Date result = null;
        if (StringUtils.isNotEmpty(time)) {
            try {
                result = sdf.parse(time);
            } catch (ParseException e) {
            }
        }
        return result;
    }

    /**
     * 给定截止日期，返回剩余期限
     *
     * @param nowtime 当前时间
     * @param endtime 任务截止时间
     * @return
     */
    public static String getRemainDays(Date nowtime, long endtime) {

        String result;
        // 当前时间
        long nowTime = nowtime.getTime();

        // 时间差
        long difference = (nowTime - endtime) / 1000;

        if (difference >= 0) {
            result = "任务已过期";
        } else {
            long dbNum = -(difference / DAY_SECONDS);
            if (dbNum != 0) {
                result = "还剩" + (dbNum) + "天到期";
            } else {
                result = "今天到期";
            }

        }
        return result;
    }

    /**
     * 获取距离现在的时间
     *
     * @param date
     * @return
     */
    public static String getTimeRemain(Date date) {
        return getTimeRemain(date == null ? 0 : date.getTime());
    }

    /**
     * 获取距离现在的时间
     *
     * @param time
     * @return
     */
    public static String getTimeRemain(long time) {

        String result;
        if (time == 0) {
            result = UNKNOWN;
        } else {
            // 当前时间
            long currentTime = new Date().getTime();

            // 时间差
            long difference = (currentTime - time) / 1000;

            if (difference < 0) {
                result = UNKNOWN;
            } else if (difference < MINUTE_SECONDS) {
                result = "刚刚";
            } else if (difference < HOUR_SECONDS) {
                result = (difference / MINUTE_SECONDS) + "分钟前";
            } else if (difference < DAY_SECONDS) {
                result = (difference / HOUR_SECONDS) + "小时前";
            } else if (difference < THREE_DAY_SECONDS) {

                long dbNum = difference / DAY_SECONDS;
                // if (dbNum == 1) {
                // result = "昨天";
                // } else if (dbNum == 2) {
                // result = "前天";
                // } else {
                // result = dbNum + "天前";
                // }
                if (dbNum > 0) {
                    result = dbNum + "天前";
                } else {
                    result = UNKNOWN;
                }
            } else if (difference >= THREE_DAY_SECONDS) {
                result = DATE_TO_DAY.format(new Date(time));
            } else {
                result = UNKNOWN;
            }
        }

        return result;
    }

    public static String getTimeFreindly(Date date) {
        return getTimeFreindly(date == null ? 0 : date.getTime());
    }

    /**
     * 获得时间提示
     *
     * @param time
     * @return
     */
    public static String getTimeFreindly(long time) {
        String result;
        if (time == 0) {
            result = UNKNOWN;
        } else {
            Date targetDate = new Date(time);

            // 今天0点的时间
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            long firstTimeOfToday = c.getTimeInMillis();

            // 时间差
            long difference = (firstTimeOfToday - time) / 1000;

            if (difference <= 0) {
                // 今天
                result = getTimeName(time) + TIME_TO_MINITE.format(targetDate);
            } else if (difference < DAY_SECONDS) {
                // 昨天
                result = ONE_DAY_BEFORE + getTimeName(time);
            } else if (difference < TWO_DAY_SECONDS) {
                // 前天
                result = TWO_DAY_BEFORE + getTimeName(time);
            } else {
                // 本周第一天0点的时间
                c.set(Calendar.DAY_OF_WEEK, 1);
                long firstTimeOfWeek = c.getTimeInMillis();

                difference = (firstTimeOfWeek - time) / 1000;

                if (difference <= 0) {
                    // 本周
                    result = getWeekday(time) + " " + getTimeName(time);
                } else {
                    // 本年第一天0点的时间
                    c.set(Calendar.DAY_OF_YEAR, 1);
                    long firstTimeOfYear = c.getTimeInMillis();

                    difference = (firstTimeOfYear - time) / 1000;

                    if (difference <= 0) {
                        // 在本年内
                        result = DATE_MONTH_DAY.format(targetDate);
                    } else {
                        // 不在本年内
                        result = DATE_YEAR_MONTH_DAY.format(targetDate);
                    }
                }
            }
        }

        return result;
    }

    public static String getTimeForChat(Date date) {
        return getTimeForChat(date == null ? 0 : date.getTime());
    }

    /**
     * 获取聊天时间
     *
     * @param time
     * @return
     */
    public static String getTimeForChat(long time) {

        String result;
        if (time == 0) {
            result = UNKNOWN;
        } else {
            Date targetDate = new Date(time);

            // 今天0点的时间
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            long firstTimeOfToday = c.getTimeInMillis();

            // 时间差
            long difference = (firstTimeOfToday - time) / 1000;

            if (difference <= 0) {
                // 今天
                result = getTimeName(time) + TIME_TO_MINITE.format(targetDate);
            } else if (difference < DAY_SECONDS) {
                // 昨天
                result = ONE_DAY_BEFORE + getTimeName(time)
                        + TIME_TO_MINITE.format(targetDate);
            } else if (difference < TWO_DAY_SECONDS) {
                // 前天
                result = TWO_DAY_BEFORE + getTimeName(time)
                        + TIME_TO_MINITE.format(targetDate);
            } else {
                // 本周第一天0点的时间
                c.set(Calendar.DAY_OF_WEEK, 1);
                long firstTimeOfWeek = c.getTimeInMillis();

                difference = (firstTimeOfWeek - time) / 1000;

                if (difference <= 0) {
                    // 本周
                    result = getWeekday(time) + " " + getTimeName(time)
                            + TIME_TO_MINITE.format(targetDate);
                } else {
                    // 本年第一天0点的时间
                    c.set(Calendar.DAY_OF_YEAR, 1);
                    long firstTimeOfYear = c.getTimeInMillis();

                    difference = (firstTimeOfYear - time) / 1000;

                    if (difference <= 0) {
                        // 在本年内
                        result = DATE_MONTH_DAY.format(targetDate)
                                + getTimeName(time)
                                + TIME_TO_MINITE.format(targetDate);
                    } else {
                        // 不在本年内
                        result = DATE_YEAR_MONTH_DAY.format(targetDate)
                                + getTimeName(time)
                                + TIME_TO_MINITE.format(targetDate);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 获取星期几
     *
     * @param time
     * @return
     */
    private static String getWeekday(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return WEEKDAYS[w];
    }

    /**
     * 获取当前时间段
     *
     * @param time
     * @return
     */
    private static String getTimeName(long time) {
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay >= 0 && hourOfDay < 6) {
            result = DAWN;
        } else if (hourOfDay >= 6 && hourOfDay < 12) {
            result = AM;
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            result = PM;
        } else if (hourOfDay >= 18 && hourOfDay < 0) {
            result = NIGHT;
        }
        return result;
    }

    public static String getRemainHours(long time) {
        if (time <= 0)
            return "";

        String result = "";
        long hour = 0;
        long minite = 0;
        hour = time / HOUR_SECONDS;

        long remainsecond = time;

        if (hour > 0) {
            remainsecond = time - hour * HOUR_SECONDS;
            result += hour + "小时";
        }
        if (remainsecond > 0) {
            minite = remainsecond / MINUTE_SECONDS;
            result += minite + "分钟";
            remainsecond = remainsecond - minite * MINUTE_SECONDS;
        }

        if (remainsecond > 0) {
            result += remainsecond + "秒";
        }

        return result;
    }

    public static void main(String[] args) {
        // Date now = new Date();
        // long nowTime = now.getTime();
        //
        // System.out.println("当前时间：" + DATETIME_TO_MINUTE.format(now));
        // System.out.println("当前：" + getTimeForNewsFeed(nowTime));
        // System.out.println("昨天此时："
        // + getTimeForNewsFeed(nowTime - DAY_SECONDS * 1000));
        // System.out.println("12小时前："
        // + getTimeForNewsFeed(nowTime - DAY_SECONDS / 2 * 1000));
        // System.out.println("前天此时："
        // + getTimeForNewsFeed(nowTime - DAY_SECONDS * 2 * 1000));
        // System.out.println("三天前此时："
        // + getTimeForNewsFeed(nowTime - DAY_SECONDS * 3 * 1000));
        // System.out.println("一周前此时："
        // + getTimeForNewsFeed(nowTime - DAY_SECONDS * 7 * 1000));

        System.out.println(getRemainHours(60));

    }
}
