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
@RequestMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TokenController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object get(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<Token> response = new Res<Token>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object post(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<Token> response = new Res<Token>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<Token> response = new Res<Token>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Object put(@Valid @RequestBody UserParam userParam, @ModelAttribute("req") Req req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        System.out.println(userParam);

        Res<Token> response = new Res<Token>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }

}
