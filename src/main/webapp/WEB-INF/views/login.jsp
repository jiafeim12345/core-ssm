<%--
  Created by IntelliJ IDEA.
  User: ZZWang
  Date: 2018/1/22
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录-数据监测平台</title>
</head>
<script type="text/javascript">
</script>
<body>
<div>${SPRING_SECURITY_LAST_EXCEPTION}</div>
    <form id="info-form" action="${ctx}/login/authenticate" method="post" >
        <table class="table table-hover table-condensed">
            <caption  style="text-align:center"><h2><b>登录</b></h2></caption>
            <tbody style="font-size: 20px">
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" name="username" cols="100" rows="14" /></td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input type="text" name="password" cols="100" rows="14" /></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="提交">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
