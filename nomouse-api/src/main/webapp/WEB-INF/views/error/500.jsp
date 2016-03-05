<%@ page contentType="application/json;charset=UTF-8" isErrorPage="true" %>
        <%@ page import="nomouse.java.web.common.api.ApiCode"%>
        <%@ page import="nomouse.java.web.common.api.ApiException"%>
        <%@ page import="nomouse.java.web.common.Res"%>
        <%@ page import="nomouse.util.json.JacksonUtils"%>
        <%@ page import="org.slf4j.Logger"%>
        <%@ page import="org.slf4j.LoggerFactory"%>
        <%
            Res res;
            if(exception instanceof ApiException){
                ApiException e = (ApiException)exception;
                res = new Res(e.getCode());
            }else{
                response.setStatus(ApiCode.INTERNAL_SERVER_ERROR);
                //记录日志
                Logger logger = LoggerFactory.getLogger("HTTP ERROR");
                logger.error(exception.getMessage(), exception);
                res = new Res(ApiCode.BAD_REQUEST);
            }
            out.print(JacksonUtils.toJson(res));
        %>