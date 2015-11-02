package nomouse.util.http;

import nomouse.util.StringUtils;
import nomouse.util.codec.EncodeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nomouse.util.http.Http.HttpMethod.*;

/**
 * HTTP funtional
 *
 * @author nomouse
 */
public class Http {

    private Logger logger = LoggerFactory.getLogger(Http.class);
    /**
     * 连接超时 10秒
     */
    private static final int TIMEOUT_CONNECTION = 10000;
    /**
     * 传输超时 10秒
     */
    private static final int TIMEOUT_SOCKET = 10000;

    private String url;
    private HttpMethod method;

    private String charset = "UTF-8";
    private String contentTypeDefault = "application/x-www-form-urlencoded";
    private String contentTypeJson = "application/json";
    private HashMap<String, String> headers = new HashMap<>();

    private HttpClientBuilder httpClientBuilder;
    private RequestConfig requestConfig;

    private Http() {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_CONNECTION)
                .setSocketTimeout(TIMEOUT_SOCKET).build();
        httpClientBuilder = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig);
    }

    public static Http get(String url) {
        Http result = new Http();
        result.url = url;
        result.method = GET;
        return result;
    }

    public static Http post(String url) {
        Http result = new Http();
        result.url = url;
        result.method = POST;

        return result;
    }

    public static Http delete(String url) {
        Http result = new Http();
        result.url = url;
        result.method = DELETE;
        return result;
    }

    public static Http put(String url) {
        Http result = new Http();
        result.url = url;
        result.method = PUT;
        return result;
    }

    public Http header(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public Http charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Map参数请求
     *
     * @param params
     * @return
     */
    public HttpResponse request(Map<String, Object> params) {

        HttpResponse result = new HttpResponse();
        CloseableHttpClient httpClient = httpClientBuilder.build();

        try {
            String body = formatFormParams(params, this.charset);
            HttpRestful request;

            logger.error(method + " " + this.url);

            switch (method) {
                case PUT:
                case POST:
                    request = new HttpRestful(this.method, this.url);
                    StringEntity stringEntity = new StringEntity(body, ContentType.create(contentTypeDefault, this.charset));
                    request.setEntity(stringEntity);

                    logger.error(body);
                    break;
                case GET:
                case DELETE:
                default:
                    if (StringUtils.isNotEmpty(body)) {
                        this.url = this.url + "?" + body;
                    }
                    request = new HttpRestful(this.method, this.url);
                    break;
            }

            for (String key : headers.keySet()) {
                request.setHeader(key, headers.get(key));
            }

            CloseableHttpResponse response = httpClient.execute(request);
            result.statusCode = response.getStatusLine().getStatusCode();

            if (result.statusCode == HttpURLConnection.HTTP_OK) {

                HttpEntity entity = response.getEntity();
                // 请求成功
                String encoding = charset;

                InputStream is;
                ByteArrayOutputStream out;

                // 此时才将内存中的数据发送到服务端
                is = entity.getContent();

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

            logger.error(result.getStatusCode() + " " + result.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * List参数请求
     *
     * @param params
     * @return
     */
    public HttpResponse request(List<NameValuePair> params) {

        HttpResponse result = new HttpResponse();
        CloseableHttpClient httpClient = httpClientBuilder.build();

        try {
            String body = URLEncodedUtils.format(params, this.charset);
            HttpRestful request;

            logger.error(this.method + " " + this.url);

            switch (this.method) {
                case PUT:
                case POST:
                    request = new HttpRestful(this.method, this.url);
                    UrlEncodedFormEntity stringEntity = new UrlEncodedFormEntity(params, this.charset);
//                    StringEntity stringEntity = new StringEntity(body, ContentType.create(contentTypeDefault, charset));
                    request.setEntity(stringEntity);

                    logger.error(body);
                    break;
                case GET:
                case DELETE:
                default:
                    if (StringUtils.isNotEmpty(body)) {
                        this.url = this.url + "?" + body;
                    }
                    this.url = this.url + "?" + body;
                    request = new HttpRestful(this.method, this.url);
                    break;
            }

            for (String key : headers.keySet()) {
                request.setHeader(key, headers.get(key));
            }

            CloseableHttpResponse response = httpClient.execute(request);
            result.statusCode = response.getStatusLine().getStatusCode();

            if (result.statusCode == HttpURLConnection.HTTP_OK) {

                HttpEntity entity = response.getEntity();
                // 请求成功
                String encoding = charset;

                InputStream is;
                ByteArrayOutputStream out;

                // 此时才将内存中的数据发送到服务端
                is = entity.getContent();

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

            logger.error(result.getStatusCode() + " " + result.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 以json string为请求体
     *
     * @param body
     * @return
     */
    public HttpResponse requestJson(String body) {

        HttpResponse result = new HttpResponse();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        try {
            HttpRestful request = new HttpRestful(this.method, this.url);

            logger.error(method + " " + this.url);

            StringEntity stringEntity = new StringEntity(body, ContentType.create(contentTypeJson, charset));
            request.setEntity(stringEntity);

            logger.error(body);

            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Accept", "application/json");
            for (String key : headers.keySet()) {
                request.setHeader(key, headers.get(key));
            }

            CloseableHttpResponse response = httpClient.execute(request);
            result.statusCode = response.getStatusLine().getStatusCode();

            if (result.statusCode == HttpURLConnection.HTTP_OK) {

                HttpEntity entity = response.getEntity();
                // 请求成功
                String encoding = charset;

                InputStream is;
                ByteArrayOutputStream out;

                // 此时才将内存中的数据发送到服务端
                is = entity.getContent();

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
            logger.error(result.getStatusCode() + " " + result.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将Map转化为http form请求文件流
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String formatFormParams(List<NameValuePair> params, String charset)
            throws UnsupportedEncodingException {
        StringBuilder buffer = new StringBuilder();

        int size = params.size();
        for (int i = 0; i < size; i++) {
            NameValuePair pair = params.get(i);
            buffer.append(pair.getName()).append("=")
                    .append(EncodeUtils.urlEncode(pair.getValue().toString(), charset));

            if (i != size - 1) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * 将Map转化为http form请求文件流
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String formatFormParams(Map<String, Object> params, String charset)
            throws UnsupportedEncodingException {
        StringBuilder buffer = new StringBuilder();

        int size = params.size();
        Object[] keys = params.keySet().toArray();
        for (int i = 0; i < size; i++) {
            buffer.append(keys[i]).append("=")
                    .append(EncodeUtils.urlEncode(params.get(keys[i]).toString(), charset));

            if (i != size - 1) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }


    public static class HttpResponse {
        private int statusCode;
        private String body;

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }

    }

    public static enum HttpMethod {
        POST(0),
        GET(1),
        PUT(2),
        DELETE(3);

        HttpMethod(int value) {
            this.value = value;
        }

        private int value;

        public int value() {
            return value;
        }
    }

    @NotThreadSafe
    public class HttpRestful extends HttpEntityEnclosingRequestBase {

        private HttpMethod method;

        public HttpRestful(final HttpMethod method, final String uri) {
            super();
            this.method = method;
            setURI(URI.create(uri));
        }

        @Override
        public String getMethod() {
            return this.method.name();
        }
    }
}
