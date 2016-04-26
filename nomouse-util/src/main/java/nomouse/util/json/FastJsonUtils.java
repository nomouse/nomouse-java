package nomouse.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * fastjson工具类，配合fastjson1.+使用
 *
 * @author nomouse
 */
public class FastJsonUtils {

    /**
     * json序列化
     *
     * @param target 对象
     * @return 失败会返回{}或者[]
     */
    public static String toJson(Object target) {
        return JSON.toJSONString(target);
    }

    /**
     * * json反序列化
     *
     * @param source 源字符串
     * @param type   反序列化类型包装
     * @param <T>    反序列化类型
     * @return 反序列化对象或者null
     */
    public static <T> T fromJson(String source, JsonUtils.TypeReference<T> type) {
        return JSON.parseObject(source, type.getType(), Feature.AllowComment);
    }

    /**
     * * json反序列化
     *
     * @param source 源字符串
     * @param cls    反序列化类
     * @param <T>    反序列化类型
     * @return 反序列化对象或者null
     */
    public static <T> T fromJson(String source, Class<T> cls) {
        return JSON.parseObject(source, cls, Feature.AllowComment);
    }

    private FastJsonUtils() {
    }

}
