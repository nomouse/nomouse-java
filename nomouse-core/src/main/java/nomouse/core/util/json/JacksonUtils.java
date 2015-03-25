package nomouse.core.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nomouse.core.util.lang.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * jackson工具类，配合jackson使用
 *
 * @author nomouse
 */
public class JacksonUtils {

    public static String EMPTY_JSON_OBJECT = "{}";

    public static String EMPTY_JSON_ARRAY = "[]";

    private static final Logger logger = LoggerFactory
            .getLogger("JacksonUtils");

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        // 配置为true表示mapper接受只有一个元素的数组的反序列化
        mapper.getDeserializationConfig().with(
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        // 配置为false表示mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
        mapper.getDeserializationConfig().without(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 定义针对日期类型的反序列化时的数据格式
        mapper.getDeserializationConfig().with(TimeUtils.DATETIME_TO_SECOND);

        mapper.setDateFormat(TimeUtils.DATETIME_TO_SECOND);

    }

    public JacksonUtils() {
    }

    /**
     * json序列化
     *
     * @param target 对象
     * @return 失败会返回{}或者[]
     */
    public static String toJson(Object target) {
        String result = EMPTY_JSON_OBJECT;
        try {
            result = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            logger.error("Json Error", "Json序列化失败" + e);
            if (target instanceof Collection<?>
                    || target instanceof Iterator<?>
                    || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            } else {
                result = EMPTY_JSON_OBJECT;
            }

        }
        return result;
    }

    /**
     * json反序列化
     *
     * @param type 源字符串
     * @param type 反序列化bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String source, TypeReference<T> type) {
        T result = null;
        if (source == null || "".equals(source)) {
        }

        try {
            result = (T) mapper.readValue(source, type);
        } catch (Exception e) {
            logger.error("Json Error", "Json读取失败" + e);
        }

        return result;
    }

    public static <T> T fromJson(String source, Class<T> cls) {
        T result = null;
        if (source == null || "".equals(source)) {
        }
        try {
            result = (T) mapper.readValue(source, cls);
        } catch (Exception e) {
            logger.error("Json Error", "Json读取失败" + e);
        }

        return result;
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

}
