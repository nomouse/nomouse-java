package nomouse.spring.api;

import nomouse.core.entity.User;
import nomouse.core.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class UserApi {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Object user() {
        User user = userDao.findOne(1L);
        return user;
    }
}
