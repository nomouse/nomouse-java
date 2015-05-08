package nomouse.demo.api.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 全局拦截器,进行用户认证和接口访问记录
 */
@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long visitTime = System.currentTimeMillis();
        String servletPath = request.getServletPath();
        String method = request.getMethod();

        String sig = request.getParameter("sig");
        String time = request.getParameter("time");
        Map<String, String[]> map = request.getParameterMap();

        System.out.println(sig);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
