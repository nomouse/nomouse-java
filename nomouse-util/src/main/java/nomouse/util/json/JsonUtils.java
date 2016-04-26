package nomouse.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * json通用操作封装
 *
 * @author nomouse
 */
public class JsonUtils {

    /**
     * json序列化
     *
     * @param target 对象
     * @return 失败会返回{}或者[]
     */
    public static String toJson(Object target) {
        return JacksonUtils.toJson(target);
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
        return JacksonUtils.fromJson(source, type);
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
        return JacksonUtils.fromJson(source, cls);
    }

    /**
     * 此处随不同实现而更改
     *
     * @param <T>
     */
    public static abstract class TypeReference<T> extends com.fasterxml.jackson.core.type.TypeReference<T> {
        protected final Type type;

        protected TypeReference() {
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof Class<?>) {
                throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
            }
            type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return type;
        }
    }


    public static String EMPTY_JSON_OBJECT = "{}";

    public static String EMPTY_JSON_ARRAY = "[]";
}
