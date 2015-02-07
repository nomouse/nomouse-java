package nomouse.spring.web;

import nomouse.spring.web.dto.Res;
import nomouse.spring.web.dto.Token;
import nomouse.spring.web.param.Req;
import nomouse.spring.web.param.UserParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by nomouse on 2014/12/8.
 */
@Controller
@RequestMapping()
public class TokenController extends BaseController {

    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object get(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<Token> response = new Res<>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }
}
