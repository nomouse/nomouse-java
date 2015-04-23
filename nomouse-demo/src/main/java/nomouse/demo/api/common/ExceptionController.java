package nomouse.demo.api.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理全局异常
 * Created by nomouse on 2014/12/26.
 */

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception exception) {

        Res response = new Res();
        response.setCode(0);
        response.setMsg("失败");

        return response;
    }


}
