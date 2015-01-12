package nomouse.spring.controller;

import nomouse.spring.controller.dto.AccessToken;
import nomouse.spring.controller.dto.Res;
import nomouse.spring.controller.param.Req;
import nomouse.spring.controller.param.UserParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by nomouse on 2014/12/8.
 */
@Controller
@RequestMapping()
public class ApiController {

    @ModelAttribute("req")
    public Req getReq(HttpServletRequest request) {
        return (Req) request.getAttribute("req");
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object tokenGet(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<AccessToken> response = new Res<AccessToken>();
        AccessToken accessToken = new AccessToken();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(accessToken);

        return response;
    }

}
