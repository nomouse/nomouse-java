package nomouse.spring.context;

import nomouse.lang.StringUtils;
import nomouse.spring.web.param.Req;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截器,进行用户认证和接口访问记录
 */
public class SpringHandlerInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String api = request.getContextPath();
        String userAgent = request.getHeader("User-Agent");

        if (StringUtils.isEmpty(userAgent)) {
            return false;
        } else {
            String[] reqArray = userAgent.split(";");
            String token = reqArray[0];

            Req req = new Req();
            request.setAttribute("req", req);

            return super.preHandle(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
