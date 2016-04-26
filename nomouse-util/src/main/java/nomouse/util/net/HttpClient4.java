package nomouse.util.net;

import nomouse.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * 基于apache http client4的封装
 *
 * @author nomouse
 */
public class HttpClient4 extends Http {

    private HttpClientBuilder builder = HttpClientBuilder.create();

    @Override
    public Response request() {
        CloseableHttpClient httpClient = builder.build();
        Http.Response res = new Response();
        try {
            String requestBody = paramFormat(paramMap(), charset);


            HttpRestful httpRequest;
            switch (method) {
                case PUT:
                case POST:
                    httpRequest = new HttpRestful(method, url);
                    HttpEntity httpEntity = new StringEntity(requestBody);
                    httpRequest.setEntity(httpEntity);
                    break;
                case DELETE:
                case GET:
                default:
                    if (StringUtils.isNotEmpty(requestBody)) {
                        this.url = this.url + "?" + requestBody;
                    }
                    this.url = this.url + "?" + requestBody;
                    httpRequest = new HttpRestful(this.method, this.url);
                    break;
            }


            for (String key : headerMap().keySet()) {
                httpRequest.setHeader(key, headerMap().get(key));
            }

            CloseableHttpResponse response = httpClient.execute(httpRequest);
            res.statusCode = response.getStatusLine().getStatusCode();
            Http.Response result = new Http.Response();
            if (res.statusCode == 200) {
                // 请求成功
                HttpEntity entity = response.getEntity();

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
                if (charset != null) {
                    result.body = new String(data, charset);
                } else {
                    result.body = new String(data);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpUtils.HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @NotThreadSafe
    public class HttpRestful extends HttpEntityEnclosingRequestBase {

        private Http.Method method;

        public HttpRestful(final Http.Method method, final String uri) {
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
