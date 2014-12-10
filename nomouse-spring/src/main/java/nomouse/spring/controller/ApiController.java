package nomouse.spring.controller;

import nomouse.spring.param.Address;
import nomouse.spring.param.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nomouse on 2014/12/8.
 */
@Controller
@RequestMapping()
public class ApiController {

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getUser(@RequestBody User<Address> body) {

        System.out.println(body);

        Map map = new HashMap();
        map.put("id", 1);
        map.put("name", "1");
        map.put("address", "1");

        return map;
    }

    @RequestMapping(value = "/user2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getUser2(@RequestBody User<Address> body) {

        System.out.println(body);

        Map map = new HashMap();
        map.put("id", 1);
        map.put("name", "1");
        map.put("address", "1");

        return map;
    }
}
