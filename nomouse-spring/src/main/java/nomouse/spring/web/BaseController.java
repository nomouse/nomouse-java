package nomouse.spring.web;

import nomouse.spring.web.param.Req;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by nomouse on 2015/1/15.
 */
@Controller
public abstract class BaseController {

    @ModelAttribute("req")
    public Req getReq(HttpServletRequest request) {
        return (Req) request.getAttribute("req");
    }
}
