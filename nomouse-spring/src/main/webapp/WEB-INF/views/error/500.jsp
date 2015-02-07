<%@ page contentType="application/json;charset=UTF-8" isErrorPage="true" %>
        <%@ page import="com.lianxi.core.utils.JsonMapper,
                         com.lianxi.queen.common.CommonException,
                         com.lianxi.queen.common.CommonStatus,
                         com.lianxi.queen.web.dto.Res,
                         org.slf4j.Logger,
                         org.slf4j.LoggerFactory,
                         org.springframework.http.HttpStatus" %>
        <%
            Res res;
            if(exception instanceof CommonException){
                CommonException commonException = (CommonException)exception;
                res = new Res(commonException.getCommonStatus());
            }else{
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                //记录日志
                Logger logger = LoggerFactory.getLogger("HTTP ERROR");
                logger.error(exception.getMessage(), exception);
                res = new Res(CommonStatus.FAILED);
            }
            out.print(JsonMapper.nonDefaultMapper().toJson(res));
        %>