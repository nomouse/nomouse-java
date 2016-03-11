package nomouse.util;

/**
 * 数字辅助类
 *
 * @author nomouse
 */
public class NumberUtils {


    /**
     * 等同于NumberUtils.toInt(value,0)
     */
    public static int toInt(Object value) {
        return toInt(value, 0);
    }

    /**
     * <p>
     * Object转int，如果传入值为{@code null}或者非int类型则返回默认值
     * </p>
     * <pre>
     * NumberUtils.toInt(Null) = 0
     * NumberUtils.toInt("") = 0
     * NumberUtils.toInt("df") = 0
     * NumberUtils.toInt("123") = 123
     * </pre>
     *
     * @param value        传入对象，可以为{@code null}
     * @param defaultValue 默认值
     * @return 返回值
     */
    public static int toInt(Object value, int defaultValue) {
        int result;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).intValue();
            } else {
                try {
                    result = Integer.parseInt(value.toString());
                } catch (Exception e) {
                    //转化出错不处理
                    result = defaultValue;
                }
            }
        } else {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 等同于NumberUtils.toLong(value,0)
     */
    public static long toLong(Object value) {
        return toLong(value, 0);
    }

    /**
     * <p>
     * Object转long，，如果传入值为{@code null}或者非long类型返回默认值
     * </p>
     * <pre>
     * NumberUtils.toLong(Null) = 0
     * NumberUtils.toLong("") = 0
     * NumberUtils.toLong("df") = 0
     * NumberUtils.toLong("123111111111") = 123
     * </pre>
     *
     * @param value        传入对象，可以为{@code null}
     * @param defaultValue 默认值
     * @return 返回值
     */
    public static long toLong(Object value, long defaultValue) {
        long result;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).longValue();
            } else {
                try {
                    result = Long.parseLong(value.toString());
                } catch (Exception e) {
                    //转化出错不处理
                    result = defaultValue;
                }
            }
        } else {
            result = defaultValue;
        }
        return result;
    }


    /**
     * 等同于NumberUtils.toDouble(value,0)
     */
    public static double toDouble(Object value) {
        return toDouble(value, 0);
    }

    /**
     * <p>
     * Object转double，如果传入值为{@code null}或者非double类型返回默认值
     * </p>
     * <pre>
     * NumberUtils.toDouble(Null) = 0.0
     * NumberUtils.toDouble("") = 0.0
     * NumberUtils.toDouble("df") = 0.0
     * NumberUtils.toDouble("123") = 123.0
     * </pre>
     *
     * @param value        传入对象，可以为{@code null}
     * @param defaultValue 默认值
     * @return 返回值
     */
    public static double toDouble(Object value, double defaultValue) {
        double result;
        if (value != null) {
            if (value instanceof Number) {
                result = ((Number) value).doubleValue();
            } else {
                try {
                    result = Double.parseDouble(value.toString());
                } catch (Exception e) {
                    //转化出错不处理
                    result = defaultValue;
                }
            }
        } else {
            result = defaultValue;
        }
        return result;
    }
}
