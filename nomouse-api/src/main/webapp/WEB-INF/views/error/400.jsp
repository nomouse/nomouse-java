<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>400 - 请求无法识别，请检查传入参数</title>
</head>

<body>
	<h2>400 - 请求无法识别，请检查传入参数</h2>
	<p><a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>
