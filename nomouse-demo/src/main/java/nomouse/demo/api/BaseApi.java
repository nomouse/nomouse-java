package nomouse.demo.api;

import nomouse.demo.api.common.Req;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public abstract class BaseApi {

    @ModelAttribute("req")
    public Req getReq(HttpServletRequest request) {
        return (Req) request.getAttribute("req");
    }
}
