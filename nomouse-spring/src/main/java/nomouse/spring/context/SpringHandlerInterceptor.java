package nomouse.spring.context;

import nomouse.spring.controller.param.Req;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nomouse on 2014/12/9.
 */
public class SpringHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getContextPath());
        request.getHeaderNames();

        Req req = new Req();
        request.setAttribute("req", req);

        return super.preHandle(request, response, handler);

    }
}
