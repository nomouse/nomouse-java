<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<form id="loginForm" action="<%=request.getContextPath()%>/login" method="post">
    <div class="control-group">
        <label for="username" class="control-label">名称:</label>

        <div class="controls">
            <input type="text" id="username" name="username" value="${username}" class="input-medium required"/>
        </div>
    </div>
    <div class="control-group">
        <label for="password" class="control-label">密码:</label>

        <div class="controls">
            <input type="password" id="password" name="password" class="input-medium required"/>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/>
                记住我</label>
            <input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/> <a class="btn"
                                                                                         href="${ctx}/register">注册</a>
            <span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>
        </div>
    </div>
</form>
</body>
</html>
