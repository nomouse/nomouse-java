package nomouse.util.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * HTTP函数式调用
 */
public class Http {

    /**
     * 连接超时 10秒
     */
    private static final int TIMEOUT_CONNECTION = 10000;
    /**
     * 传输超时 10秒
     */
    private static final int TIMEOUT_SOCKET = 10000;

    private String url;
    private String method;
    private String charset = "UTF-8";
    private String body;
    private HashMap<String, Object> headers = new HashMap<String, Object>();

    private Http() {
        headers.put("Connection", "keep-alive");
    }

    public static Http newHttp(String url) {
        Http result = new Http();
        result.url = url;
        result.method = "GET";
        return result;
    }

    public Http headers(HashMap<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HttpResponse post(HashMap<String, Object> params) {
        this.body = getRequestBody(params);
        this.method = "POST";
        return request();
    }

    public HttpResponse postJson(String body) {
        headers.put("Content-Type", "application/json;charset=UTF-8");
        this.body = body;
        this.method = "POST";
        return request();
    }

    public HttpResponse get(HashMap<String, Object> params) {
        this.body = getRequestBody(params);
        this.method = "GET";
        return request();
    }

    private HttpResponse request() {
        HttpResponse result = new HttpResponse();
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            // conn.setChunkedStreamingMode(0);

            conn.setRequestMethod(method);
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);

            conn.setUseCaches(false);
            // Headers
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key).toString());
            }

            // 构造DataOutputStream流，将要提交到服务端的数据写入内存中，暂时不发送
            DataOutputStream requestOut = new DataOutputStream(
                    conn.getOutputStream());
            requestOut.write(body.getBytes());
            requestOut.flush();
            requestOut.close();

            // 请求状态码
            result.code = conn.getResponseCode();
            if (result.code == HttpURLConnection.HTTP_OK) {
                // 请求成功
                String encoding = conn.getContentEncoding();
                InputStream is = null;
                ByteArrayOutputStream out = null;

                // 此时才将内存中的数据发送到服务端
                is = conn.getInputStream();

                // 写入内存
                out = new ByteArrayOutputStream();
                int read = -1;
                while ((read = is.read()) != -1) {
                    out.write(read);
                }
                byte[] data = out.toByteArray();

                out.close();

                // 编码
                if (encoding != null) {
                    result.body = new String(data, encoding);
                } else {
                    result.body = new String(data);
                }
            }

        } catch (IOException e) {
        }

        return result;
    }

    private String getRequestBody(HashMap<String, Object> params) {
        StringBuilder buffer = new StringBuilder();
        Object[] keys = params.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            buffer.append(keys[i]).append("=")
                    .append(encodeURL(params.get(keys[i]).toString()));
            if (i != keys.length - 1) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * URL编码 (符合FRC1738规范)
     *
     * @param input 待编码的字符串
     * @return 编码后的字符串
     */
    private String encodeURL(String input) {
        String result = "";

        try {
            result = URLEncoder.encode(input, charset).replace("+", "%20")
                    .replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
        }

        return result;
    }

    public static class HttpResponse {
        private int code;
        private String body;

        public int getCode() {
            return code;
        }

        public String getBody() {
            return body;
        }

    }

}
