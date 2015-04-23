package nomouse.demo.api;

import nomouse.demo.api.common.ApiException;
import nomouse.demo.api.common.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.Set;

import static nomouse.demo.api.common.ApiCode.*;

@Controller
public abstract class BaseApi {

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    /**
     * 获取公用请求参数
     *
     * @return
     */
    @ModelAttribute("req")
    Req getReq() {
        return (Req) RequestContextHolder.getRequestAttributes().
                getAttribute("req", RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 使用validator校验参数,不符合注解要求的抛错
     *
     * @param t
     * @param <T>
     */
    <T> void assertParam(T t) {
        Set<ConstraintViolation<T>> result = validatorFactoryBean.getValidator().validate(t);
        if (result == null || result.size() == 0) {
        } else {
            Iterator<ConstraintViolation<T>> iterator = result.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<T> error = iterator.next();
                throw new ApiException(PARAM_ERROR, error.getPropertyPath() + ":" + error.getMessage());
            }
        }
    }
}
