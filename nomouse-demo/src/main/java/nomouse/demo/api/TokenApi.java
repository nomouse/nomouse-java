package nomouse.demo.api;

import nomouse.demo.api.common.Req;
import nomouse.demo.api.common.Res;
import nomouse.demo.api.dto.Token;
import nomouse.demo.api.param.UserParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api")
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
        response.code = (1);

        return response;
    }
}
