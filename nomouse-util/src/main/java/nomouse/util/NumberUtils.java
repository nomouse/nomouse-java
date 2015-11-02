package nomouse.util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
     * 从0到{@code end}范围内(包含0但不包含{@code end}),随机生成多个不重复的数字,如果{@code size}>={@code end}的话返回所有
     */
    public static Set<Integer> randomSet(int end, int size) {
        int resultSize = Math.min(end, size);
        Set<Integer> set = new HashSet<>(resultSize);

        Random random = new Random();
        while (set.size() < resultSize) {
            set.add(random.nextInt(end));
        }

        return set;
    }

    /**
     * 从0到{@code end}范围内的数字
     */
    public static Integer random(int end) {
        Random random = new Random();
        return random.nextInt(end);
    }

    /**
     * <p>
     * Object转int
     * </p>
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
                    //转化出错不处理
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * Object转long
     * </p>
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
                    //转化出错不处理
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * Object转double
     * </p>
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
                    //转化出错不处理
                }
            }
        }
        return result;
    }
}
