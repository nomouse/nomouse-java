package nomouse.util;

import java.text.DecimalFormat;

/**
 * 数字辅助类
 *
 * @author nomouse
 */
public class NumberUtils {

    /**
     * 数字格式，用来生成6位验证码，首位可以为0
     */
    public static final DecimalFormat CODE_FORMAT = new DecimalFormat("000000");


    /**
     * <p>
     * Object转int
     * </p>
     * <p/>
     * <pre>
     * NumberUtils.parseInt(Null) = 0
     * NumberUtils.parseInt("") = 0
     * NumberUtils.parseInt("df") = 0
     * NumberUtils.parseInt("123") = 123
     * </pre>
     *
     * @param value
     * @return
     */
    public static int parseInt(Object value) {
        int result = 0;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).intValue();
            } else {
                try {
                    result = Integer.parseInt(value.toString());
                } catch (Exception e) {
                    // 转化出错不处理
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * Object转long
     * </p>
     * <p/>
     * <pre>
     * NumberUtils.parseInt(Null) = 0
     * NumberUtils.parseInt("") = 0
     * NumberUtils.parseInt("df") = 0
     * NumberUtils.parseInt("123") = 123
     * </pre>
     *
     * @param value
     * @return
     */
    public static long parseLong(Object value) {
        long result = 0;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).longValue();
            } else {
                try {
                    result = Long.parseLong(value.toString());
                } catch (Exception e) {
                    // 转化出错不处理
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * Object转double
     * </p>
     * <p/>
     * <pre>
     * NumberUtils.parseInt(Null) = 0.0
     * NumberUtils.parseInt("") = 0.0
     * NumberUtils.parseInt("df") = 0.0
     * NumberUtils.parseInt("123") = 123.0
     * </pre>
     *
     * @param value
     * @return
     */
    public static double parseDouble(Object value) {
        double result = 0;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).doubleValue();
            } else {
                try {
                    result = Double.parseDouble(value.toString());
                } catch (Exception e) {
                    // 转化出错不处理
                }
            }
        }
        return result;
    }
}
