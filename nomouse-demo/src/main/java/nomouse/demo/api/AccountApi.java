package nomouse.demo.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import nomouse.demo.api.common.Res;
import nomouse.demo.api.dto.Ticket;
import nomouse.demo.api.dto.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Api(value = "/v1/account", description = "账户相关")
@Controller
@RequestMapping(value = "/v1/account")
public class AccountApi extends BaseApi {

    @ApiOperation("获取一次性票据")
    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    @ResponseBody
    public Res<Ticket> ticket(
            @ApiParam(value = "时间戳") @RequestParam String timestamp,
            @RequestParam String sig) {

        System.out.println(sig);

        Ticket ticket = new Ticket();
        ticket.ticket = UUID.randomUUID().toString();

        Res<Ticket> response = new Res<>(ticket);

        return response;
    }

    @ApiOperation("获取token")
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public Res<Token> token(
            @ApiParam(value = "用户名") @RequestParam String username,
            @ApiParam(value = "密码") @RequestParam String password,
            @ApiParam(value = "票据") @RequestParam String ticket) {
        Token token = new Token();
        token.token = UUID.randomUUID().toString();

        Res<Token> response = new Res<>(token);

        return response;
    }
}
