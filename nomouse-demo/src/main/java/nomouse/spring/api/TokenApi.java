package nomouse.spring.api;

import nomouse.spring.api.common.Res;
import nomouse.spring.api.dto.Token;
import nomouse.spring.api.common.Req;
import nomouse.spring.api.param.UserParam;
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
public class TokenApi extends BaseApi {

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
