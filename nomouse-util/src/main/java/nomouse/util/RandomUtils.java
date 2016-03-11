package nomouse.util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by nomouse on 16/2/19.
 */
public class RandomUtils {

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
}
