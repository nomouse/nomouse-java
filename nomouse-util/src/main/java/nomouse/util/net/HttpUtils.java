package nomouse.util.net;

import nomouse.util.json.JacksonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 简单封装HttpClient
 *
 * @author nomouse
 */
public class HttpUtils {

    /**
     * 请求错误，服务异常
     */
    public static final int REQUEST_ERROR_SERVER = -19;

    /**
     * 请求错误，网络错误
     */
    public static final int REQUEST_ERROR_NET = -18;

    /**
     * 连接超时 10秒
     */
    private static final int TIMEOUT_CONNECTION = 10000;
    /**
     * 传输超时 10秒
     */
    private static final int TIMEOUT_SOCKET = 10000;

    /**
     * 编码方式
     */
    private static final String CONTENT_CHARSET = "UTF-8";


    /**
     * @param url  服务器地址
     * @param in   文件流
     * @param name 服务端获取的文件命名
     * @return
     */
    public static String uploadMultipart(String url, InputStream in, String name, String filename) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(
                HttpMultipartMode.BROWSER_COMPATIBLE);

        HttpResponse response = null;
        try {
            multipartEntityBuilder.addBinaryBody(name, in, ContentType.MULTIPART_FORM_DATA, filename);
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httppost.setEntity(httpEntity);
            response = client.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httppost.abort();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String uploadMultipart(String url, File file, String name) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpResponse response = null;
        try {
            multipartEntityBuilder.addBinaryBody(name, file);
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httppost.setEntity(httpEntity);
            response = client.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String uploadMultipart(String url, Object param, File file, String fileName, String paramName) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpResponse response = null;
        try {

            multipartEntityBuilder.addBinaryBody(fileName, file);
            multipartEntityBuilder.addTextBody(paramName, JacksonUtils.toJson(param),
                    ContentType.APPLICATION_JSON);
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httppost.setEntity(httpEntity);
            response = client.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String uploadMultipart(String url, Object param, Map<String, File> fileMap, String paramName) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpResponse response = null;
        try {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                multipartEntityBuilder.addBinaryBody(entry.getKey(), entry.getValue());
            }
            multipartEntityBuilder.addTextBody(paramName, JacksonUtils.toJson(param),
                    ContentType.APPLICATION_JSON);
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httppost.setEntity(httpEntity);
            response = client.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String url, List<NameValuePair> parameters, String charset) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        HttpResponse response;
        try {
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(parameters, charset);
            httppost.setEntity(reqEntity);
            response = client.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent(), charset);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static String post(String url, List<NameValuePair> parameters) {
        return post(url, parameters, "UTF-8");
    }

    public static InputStream downloadFiles(String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpget);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toBufferedInputStream(resEntity.getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String get(String url) {
        return get(url, "utf-8");
    }

    public static String get(String url, String charset) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        try {

            response = client.execute(httpget);
            HttpEntity resEntity = response.getEntity();
            return IOUtils.toString(resEntity.getContent());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws java.io.IOException
     */
    public static String postHttpRequest2(String url,
                                          HashMap<String, Object> params) throws HttpException {

        String responseBody = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            // conn.setChunkedStreamingMode(0);

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);

            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "keep-alive");

            // 构造DataOutputStream流，将要提交到服务端的数据写入内存中，暂时不发送
            DataOutputStream requestOut = new DataOutputStream(
                    conn.getOutputStream());
            String requestBody = getParams(params);
            requestOut.writeBytes(requestBody);
            requestOut.flush();
            requestOut.close();

            // 请求失败，跳出
            int res = conn.getResponseCode();
            if (res != HttpURLConnection.HTTP_OK) {
                throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                        + "] failed:" + res);
            }

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
                responseBody = new String(data, encoding);
            } else {
                responseBody = new String(data);
            }
        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                    + "] failed:" + e.getMessage());
        }

        return responseBody;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws java.io.IOException
     */
    public static String postHttpRequestJson2(String url, byte[] params)
            throws HttpException {

        String responseBody = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            // conn.setChunkedStreamingMode(0);

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);

            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");

            // 构造DataOutputStream流，将要提交到服务端的数据写入内存中，暂时不发送
            DataOutputStream requestOut = new DataOutputStream(
                    conn.getOutputStream());
            requestOut.write(params);
            requestOut.flush();
            requestOut.close();

            // 请求失败，跳出
            int res = conn.getResponseCode();
            if (res != HttpURLConnection.HTTP_OK) {
                throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                        + "] failed:" + res);
            }

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
                responseBody = new String(data, encoding);
            } else {
                responseBody = new String(data);
            }
        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                    + "] failed:" + e.getMessage());
        }

        return responseBody;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws java.io.IOException
     */
    @SuppressWarnings({"resource"})
    public static String postHttpRequest(String url,
                                         HashMap<String, Object> params) throws HttpException {

        String result = null;

        HttpParams parameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parameters,
                TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(parameters, TIMEOUT_SOCKET);

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(
                parameters);

        final HttpPost post = new HttpPost(url);

        HttpResponse httpResponse;
        int statusCode = HttpURLConnection.HTTP_OK;
        try {

            LinkedList<BasicNameValuePair> postParams = new LinkedList<BasicNameValuePair>();
            for (String key : params.keySet()) {
                String value = params.get(key).toString();
                BasicNameValuePair pair = new BasicNameValuePair(key, value);
                postParams.add(pair);
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);

            post.setEntity(entity);

            httpResponse = httpClient.execute(post);

            statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpURLConnection.HTTP_OK) {
                result = EntityUtils.toString(httpResponse.getEntity(),
                        CONTENT_CHARSET);
                throw new HttpException(REQUEST_ERROR_SERVER, getErrorInfo(url,
                        params, statusCode, result));
            }
            result = EntityUtils.toString(httpResponse.getEntity(),
                    CONTENT_CHARSET);

        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, getErrorInfo(url,
                    params, statusCode, result));
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        }

        return result;

    }

    /**
     * 发送post请求,参数以json格式发送
     *
     * @param url
     * @param params
     * @return
     * @throws com.lianxi.core.util.http.HttpUtils.HttpException
     */
    public static String postHttpRequestJson2(String url,
                                              HashMap<String, Object> params) throws HttpException {

        String responseBody = null;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            // conn.setChunkedStreamingMode(0);

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);

            conn.setUseCaches(false);
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");
            conn.setRequestProperty("Connection", "keep-alive");

            conn.connect();

            DataOutputStream requestOut = new DataOutputStream(
                    conn.getOutputStream());
            String requestBody = JacksonUtils.toJson(params);
            requestOut.writeBytes(requestBody);
            requestOut.flush();
            requestOut.close();

            // 请求失败，跳出
            int res = conn.getResponseCode();
            if (res != HttpURLConnection.HTTP_OK) {
                throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                        + "] failed:" + res);
            }

            // 请求成功
            String encoding = conn.getContentEncoding();
            InputStream is = null;
            ByteArrayOutputStream out = null;

            // 发送请求数据
            is = conn.getInputStream();

            // 写入内存
            out = new ByteArrayOutputStream();
            int read = -1;
            while ((read = is.read()) != -1) {
                out.write(read);
            }
            byte[] data = out.toByteArray();

            out.close();

            // 转换编码
            if (encoding != null) {
                responseBody = new String(data, encoding);
            } else {
                responseBody = new String(data);
            }

        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                    + "] failed:" + e.getMessage());
        }

        return responseBody;

    }

    public static void postHttps() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpGet get = new HttpGet();
            get.setURI(new URI("https://test.com"));
            httpClient.execute(get);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"resource"})
    public static String postHttpRequestJson(String url,
                                             HashMap<String, Object> params) throws HttpException {

        String result = null;

        HttpParams parameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parameters,
                TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(parameters, TIMEOUT_SOCKET);

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(
                parameters);

        final HttpPost post = new HttpPost(url);

        HttpResponse httpResponse;
        int statusCode = HttpURLConnection.HTTP_OK;
        try {
            String requestJson = JacksonUtils.toJson(params);
            StringEntity entity = new StringEntity(requestJson, CONTENT_CHARSET);
            entity.setContentType("application/json;charset=UTF-8");
            post.setEntity(entity);

            httpResponse = httpClient.execute(post);

            statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpURLConnection.HTTP_OK) {

                result = EntityUtils.toString(httpResponse.getEntity(),
                        CONTENT_CHARSET);
                throw new HttpException(REQUEST_ERROR_SERVER, getErrorInfo(url,
                        params, statusCode, result));
            }
            result = EntityUtils.toString(httpResponse.getEntity(),
                    CONTENT_CHARSET);

        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, getErrorInfo(url,
                    params, statusCode, result));
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        }

        return result;

    }

    public static String postFileRequest2(String url, File file)
            throws HttpException {

        String responseBody = null;

        String BOUNDARY = "*****";
        String HYPHENS = "--";
        String CRLF = "\r\n";
        String fileName = "image.jpg";
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            // conn.setChunkedStreamingMode(0);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Charset", CONTENT_CHARSET);
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            // 构造DataOutputStream流
            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            ds.writeBytes(HYPHENS + BOUNDARY + CRLF);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + fileName + "\"" + CRLF);
            ds.writeBytes(CRLF);

            // 构造要上传文件的FileInputStream流
            FileInputStream fis = new FileInputStream(file);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            // 从文件读取数据至缓冲区
            while ((length = fis.read(buffer)) != -1) {
                // 将资料写入DataOutputStream中
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(CRLF);
            ds.writeBytes(HYPHENS + BOUNDARY + HYPHENS + CRLF);
            // 关闭流
            fis.close();
            ds.flush();

            // 请求失败，跳出
            int res = conn.getResponseCode();
            if (res != HttpURLConnection.HTTP_OK) {
                throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                        + "] failed:" + res);
            }

            // 请求成功
            String encoding = conn.getContentEncoding();
            InputStream is = null;
            ByteArrayOutputStream out = null;

            // 此处才发送流
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
                responseBody = new String(data, encoding);
            } else {
                responseBody = new String(data);
            }
        } catch (IOException e) {
            throw new HttpException(REQUEST_ERROR_SERVER, "Request [" + url
                    + "] failed:" + e.getMessage());
        }

        return responseBody;
    }

    /**
     * 判断一个url链接是否可用
     *
     * @param url
     * @return
     */
    @SuppressWarnings({"resource"})
    public static boolean isUrlAvalible(String url) {
        boolean result = false;

        HttpParams parameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parameters,
                TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(parameters, TIMEOUT_SOCKET);
        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(
                parameters);

        HttpResponse httpResponse;
        HttpGet httpRequest = new HttpGet(url);
        try {
            httpResponse = httpClient.execute(httpRequest);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                result = true;
            }
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 构造参数
     *
     * @param params
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getParams(HashMap<String, Object> params)
            throws UnsupportedEncodingException {
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

    private static String getErrorInfo(String url,
                                       HashMap<String, Object> params, int statusCode, String result) {
        StringBuilder sb = new StringBuilder("Request:");
        sb.append("\n url=" + url);
        sb.append("\n params=" + params);
        sb.append("\n statusCode=" + statusCode);
        sb.append("\n result=" + result);
        return sb.toString();

    }

    /**
     * URL编码 (符合FRC1738规范)
     *
     * @param input 待编码的字符串
     * @return 编码后的字符串
     */
    public static String encodeURL(String input)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(input, CONTENT_CHARSET).replace("+", "%20")
                .replace("*", "%2A");

    }

    public static class HttpException extends RuntimeException {
        // 序列化UID
        private static final long serialVersionUID = 8243127099991355146L;
        // 错误码
        private int code;

        /**
         * 构造异常
         *
         * @param code 异常状态码
         * @param msg  异常讯息
         */
        public HttpException(int code, String msg) {
            super(msg);
            this.code = code;
        }

        /**
         * 构造异常
         *
         * @param code 异常状态码
         * @param ex   异常来源
         */
        public HttpException(int code, Exception ex) {
            super(ex);
            this.code = code;
        }

        /**
         * @return 异常状态码。
         */
        public int getErrorCode() {
            return code;
        }

    }
}
