package nomouse.java.web.account.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "/account/login";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
//        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
//        return "account/login";
//    }
}
