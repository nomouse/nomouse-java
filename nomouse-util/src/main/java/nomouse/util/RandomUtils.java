package nomouse.util;

import java.math.BigDecimal;
import java.util.*;

/**
 * 随机工具类
 *
 * @author nomouse
 */
public class RandomUtils {

    /**
     * 四舍五入返回double类型
     *
     * @param source 原数据
     * @param digit  保留位数
     * @return 返回值
     */
    public static double roundDouble(double source, int digit) {
        BigDecimal b = new BigDecimal(source);
        double result = b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 从0到{@code end}范围内(包含0但不包含{@code end}),随机生成多个不重复的数字,如果{@code size}>={@code end}的话返回所有
     */
    public static Set<Integer> randomSet(int end, int size) {
        int resultSize = Math.min(end, size);
        Set<Integer> set = new HashSet<Integer>(resultSize);

        Random random = new Random();
        while (set.size() < resultSize) {
            set.add(random.nextInt(end));
        }

        return set;
    }

    /**
     * 从一个列表中随机取出n个不重复的元素返回
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<?> randomFromList(final List<?> list, final int size) {

        if (list == null) {
            return new ArrayList();
        }

        int poolSize = list.size();
        int resultSize = Math.min(poolSize, size);
        List result = new ArrayList(resultSize);

        Set<Integer> resultIndexSet = randomSet(poolSize, resultSize);
        for (Integer index : resultIndexSet) {
            result.add(list.get(index));
        }

        return result;
    }
}
