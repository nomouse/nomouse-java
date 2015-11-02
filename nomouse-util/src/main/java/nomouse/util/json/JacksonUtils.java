package nomouse.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * jackson工具类，配合jackson2.0+使用
 *
 * @author nomouse
 */
public class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    public static String EMPTY_JSON_OBJECT = "{}";

    public static String EMPTY_JSON_ARRAY = "[]";


    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        // 配置为true表示mapper接受只有一个元素的数组的反序列化
        mapper.getDeserializationConfig().with(
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        // 配置为false表示mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
        mapper.getDeserializationConfig().without(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 定义针对日期类型的反序列化时的数据格式

    }

    private JacksonUtils() {
    }

    /**
     * json序列化
     *
     * @param target 对象
     * @return 失败会返回{}或者[]
     */
    public static String toJson(Object target) {
        String result;
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
     * * json反序列化
     *
     * @param source 源字符串
     * @param type   反序列化类型包装
     * @param <T>    反序列化类型
     * @return 反序列化对象或者null
     */
    public static <T> T fromJson(String source, TypeReference<T> type) {
        T result = null;
        if (source == null || "".equals(source)) {
            //return
        } else {
            try {
                result = mapper.readValue(source, type);
            } catch (Exception e) {
                logger.error("Json Error", "Json读取失败" + e);
            }
        }
        return result;
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
        T result = null;
        if (source == null || "".equals(source)) {
            //return
        } else {
            try {
                result = mapper.readValue(source, cls);
            } catch (Exception e) {
                logger.error("Json Error", "Json读取失败" + e);
            }
        }

        return result;
    }

    /**
     * 反序列化复杂Collection如List<Bean>,
     */
    public static <T> T fromJsonToCollection(String source, Class<T> collectionClass, Class<?> elementClasses) {
        if (source == null || source.length() == 0) {
            return null;
        }

        try {
            return (T) mapper.readValue(source, mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses));
        } catch (IOException e) {
            logger.error("parse json string error:" + source, e);
            return null;
        }
    }

    /**
     * 反序列化为Map
     */
    public static <T> T fromJsonToMap(String source, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        if (source == null || source.length() == 0) {
            return null;
        }
        try {
            return (T) mapper.readValue(source, mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass));
        } catch (IOException e) {
            logger.error("parse json string error:" + source, e);
            return null;
        }
    }


    /**
     * map转bean
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> beanClass) {
        try {
            return mapper.convertValue(map, beanClass);
        } catch (Exception e) {
            logger.warn("convertMapToBean error! ", e);
        }
        return null;
    }

    /**
     * bean转map
     *
     * @param bean
     * @param objectClass
     * @return
     */
    public static <T> T beanToOther(Object bean, Class<T> objectClass) {
        try {
            return mapper.convertValue(bean, objectClass);
        } catch (Exception e) {
            logger.warn("beanToOther error! ", e);
        }
        return null;
    }

    /**
     * 输出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 获取ObjectMapper进一步操作
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

}
