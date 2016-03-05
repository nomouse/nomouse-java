package nomouse.java.web.common.api;

import nomouse.java.web.common.Req;
import nomouse.java.web.common.Res;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 处理全局业务异常CommonException
 */

@ControllerAdvice
class ApiExceptionHandler {


    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public Object handleException(ApiException exception) {

        Req req = (Req) RequestContextHolder.getRequestAttributes().
                getAttribute("req", RequestAttributes.SCOPE_REQUEST);

        Res response;
        if (exception != null) {
            response = new Res(exception.getCode(), exception.getMsg());
        } else {
            response = new Res(ApiCode.INTERNAL_SERVER_ERROR, ApiCode.INTERNAL_SERVER_ERROR_MSG);
        }

        return response;
    }


}
