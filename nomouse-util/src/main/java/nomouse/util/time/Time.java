package nomouse.util.time;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间的函数式封装
 *
 * @author nomouse
 */
public class Time {

    private Calendar calendar;

    private Time(long millis) {
        calendar = calendarThreadLocal.get();
        calendar.setTimeInMillis(millis);
    }

    /**
     * 初始化时间函数，默认当前时间
     */
    public static Time init() {
        return init(System.currentTimeMillis());
    }

    /**
     * 初始化时间函数，设置为转入时间
     */
    public static Time init(long millis) {
        return new Time(millis);
    }

    /**
     * 初始化时间函数，设置为转入时间
     */
    public static Time init(Date date) {
        return new Time(date.getTime());
    }

    /**
     * 输出unix时间
     * 
     * @return 1970:01:01 00:00:00至今的毫秒数
     */
    public long time() {
        return calendar.getTimeInMillis();
    }

    /**
     * 输出日期，受时区影响
     * 
     * @return 输出日期，受时区影响
     */
    public Date date() {
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 输出时间戳
     * 
     * @return 输出时间戳
     */
    public Timestamp timestamp() {
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 将当前时间更改为当天开始时间(0点0分0秒000毫秒)
     */
    public Time dayBegin() {
        setTime(0, 0, 0, 0);
        return this;
    }

    /**
     * 将当前时间更改为当天结束时间(23点59分59秒999毫秒)
     */
    public Time dayEnd() {
        setTime(23, 59, 59, 999);
        return this;
    }

    /**
     * 将当前时间按日期增加(负数为减少)
     */
    public Time dayAdd(int days) {
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return this;
    }

    /**
     * 将当前时间按小时增加(负数为减少)
     */
    public Time HourAdd(int hours) {
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return this;
    }

    @SuppressWarnings("unused")
    private long set(int year, int month, int day, int hourOfDay, int minute, int second, int millisecond) {
        calendar.set(year, month, day, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTimeInMillis();
    }

    @SuppressWarnings("unused")
    private long setDate(int year, int month, int day) {
        calendar.set(year, month - 1, day);
        return calendar.getTimeInMillis();
    }

    private long setTime(int hourOfDay, int minute, int second, int millisecond) {
        calendar.set(HOUR_OF_DAY, hourOfDay);
        calendar.set(MINUTE, minute);
        calendar.set(SECOND, second);
        calendar.set(MILLISECOND, millisecond);
        return calendar.getTimeInMillis();
    }

    // Calendar有线程安全问题，使用ThreadLocal来确保保证线程安全
    private static ThreadLocal<Calendar> calendarThreadLocal = new DefaultCalendarThreadLocal();

    public static final class DefaultCalendarThreadLocal extends ThreadLocal<Calendar> {

        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    }
}
