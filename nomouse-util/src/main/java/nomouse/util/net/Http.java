package nomouse.util.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP函数式调用
 *
 * @author nomouse
 */
public abstract class Http {

    protected Http() {
    }

    protected static Http instance(String url, Method method) {
        Http http = new HttpClient3();
        http.method = method;
        http.url = url;
        return http;
    }

    public static Http get(String url) {
        return instance(url, Method.GET);
    }

    public static Http post(String url) {
        return instance(url, Method.POST);
    }

    public static Http delete(String url) {
        return instance(url, Method.DELETE);
    }

    public static Http put(String url) {
        return instance(url, Method.PUT);
    }

    /**
     * 连接超时 10秒
     */
    public static final int TIMEOUT_CONNECTION = 10000;
    /**
     * 传输超时 10秒
     */
    public static final int TIMEOUT_SOCKET = 10000;

    /**
     * 请求成功的状态
     */
    public static final int STATUS_SUCCESS = 200;

    protected String url;
    protected Method method;

    protected String charset = "UTF-8";
    protected String contentTypeDefault = "application/x-www-form-urlencoded";
    protected String contentTypeJson = "application/json";

    private Map<String, String> headerMap;

    private Map<String, Object> paramMap;

    protected Map<String, String> headerMap() {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<String, String>();
        }
        return headerMap;
    }

    protected Map<String, Object> paramMap() {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<String, Object>();
        }
        return paramMap;
    }

    /**
     * 批量设置请求头
     */
    public Http header(Map<String, String> headerMap) {
        headerMap().putAll(headerMap);
        return this;
    }

    /**
     * 批量设置请求参数
     */
    public Http param(Map<String, Object> param) {
        paramMap().putAll(param);
        return this;
    }

    /**
     * 设置请求头
     */
    public Http header(String key, String value) {
        headerMap().put(key, value);
        return this;
    }

    /**
     * 设置请求参数
     */
    public Http param(String key, Object value) {
        paramMap().put(key, value);
        return this;
    }

    /**
     * 设置请求和响应字符编码，默认是utf-8
     */
    public Http charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 将Map转化为http form请求文件流
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    String paramFormat(Map<String, Object> params, String charset) throws UnsupportedEncodingException {
        StringBuilder buffer = new StringBuilder();

        // 数量
        int size = params.size();
        // &的数量
        int count = 0;
        for (String key : params.keySet()) {
            buffer.append(key).append("=").append(urlEncode(params.get(key).toString(), charset));

            if (count != size - 1) {
                buffer.append("&");
                count = count + 1;
            }

        }
        return buffer.toString();
    }

    /**
     * URL编码(符合FRC1738规范)
     */
    String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding).replace("+", "%20").replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL解码
     */
    String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * 通用请求响应体
     */
    public static class Response {

        protected int statusCode;
        protected String body;

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }

    }

    /**
     * 通用请求方法
     */
    public static enum Method {
        POST(0), GET(1), PUT(2), DELETE(3);

        Method(int value) {
            this.value = value;
        }

        protected int value;

        public int value() {
            return value;
        }
    }

    /**
     * Map参数式请求
     */
    public abstract Response request();

}
