package nomouse.demo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nomouse.demo.api.common.Res;
import nomouse.demo.api.dto.Token;
import nomouse.demo.api.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

/**
 * Created by nomouse on 2014/12/8.
 */
@Controller
@RequestMapping()
public class CustomApi {

    @Autowired
    private Validator validator;

    @RequestMapping(value = "/token1", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object tokenGet(@RequestBody String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserParam userParam = objectMapper.readValue(json, UserParam.class);

        Set<ConstraintViolation<UserParam>> set = validator.validate(userParam);

        System.out.println(set.size());

        Res<Token> response = new Res<Token>();
        Token token = new Token();
        response.setCode(1);
        response.setMsg("成功");
        response.setResult(token);

        return response;
    }

}
