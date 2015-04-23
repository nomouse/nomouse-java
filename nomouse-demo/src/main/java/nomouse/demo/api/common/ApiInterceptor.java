package nomouse.demo.api.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局拦截器,进行用户认证和接口访问记录
 */
@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    public static final String API_IDENTIFY = "Lianxi";
    public static final String PATH_TOKEN = "/token/get";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long visitTime = System.currentTimeMillis();

        String servletPath = request.getServletPath();
        String method = request.getMethod();

//        String contextPath = request.getContextPath();
//        String relativePath = request.getRequestURI();
//        String absolutePath = request.getRequestURL().toString();

        String userAgent = request.getHeader("x-token");
        Req req = new Req();

        request.setAttribute("req", req);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        Req req = (Req) request.getAttribute("req");
        super.postHandle(request, response, handler, modelAndView);
    }

}
