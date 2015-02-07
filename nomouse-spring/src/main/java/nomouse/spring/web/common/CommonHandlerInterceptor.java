package nomouse.spring.web.common;

import nomouse.lang.StringUtils;
import nomouse.spring.web.param.Req;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截器,进行用户认证和接口访问记录
 */
@Component
public class CommonHandlerInterceptor extends HandlerInterceptorAdapter {

    public static final String API_IDENTIFY = "Lianxi";
    public static final String API_TOKEN_GET = "/token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();

        String relativePath = request.getRequestURI();
        String absolutePath = request.getRequestURL().toString();

        //放出默认
        if (StringUtils.equals(servletPath, "/")) {
            return super.preHandle(request, response, handler);
        }

        String userAgent = request.getHeader("User-Agent");
        Req req = checkReq(servletPath, userAgent);
        if (req == null) {
            return false;
        } else {
            request.setAttribute("req", req);
            return super.preHandle(request, response, handler);
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * headers {"User-Agent" "Nomouse;version;token;os;model;sim;net"}
     * headers {"User-Agent" "Nomouse;1.0;dsfsfsfsdfsd446546546;Android 2.1;HTC Desire S Build/GRI40;LT;2G"}
     *
     * @param api
     * @param source
     * @return
     */
    private Req checkReq(String api, String source) throws Exception {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        String[] tempArray = source.split(";");
        if (tempArray.length < 7) {
            return null;
        }

        if (!StringUtils.equals(API_IDENTIFY, tempArray[0])) {
            return null;
        }

        //TODO 登录
        Req req = new Req(api, tempArray[1], tempArray[2], tempArray[3], tempArray[4], tempArray[5], tempArray[6]);
        if (StringUtils.equals(req.getApi(), API_TOKEN_GET)) {

        }

        if (StringUtils.isEmpty(req.getToken())) {
            //TODO throws Exception
        } else {
            //TODO user token

        }

        return req;
    }
}
