<%--
  Created by IntelliJ IDEA.
  User: ZZWang
  Date: 2018/1/22
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../taglibs.jsp"%>

<html>
<body>
    <table class="table table-hover" >
        <tbody id="" style="font-size: 20px" align="center">
            <tr>
                <td align="right" width="35%"><b>所有者：</b></td>
                <td align="left">${whoisInfo.RegistrantName}</td>
            </tr>
            <tr>
                <td align="right" width="35%"><b>联系邮箱：</b></td>
                <td align="left">${whoisInfo.RegistrantEmail}</td>
            </tr>
            <tr>
                <td align="right" width="35%"><b>注册商：</b></td>
                <td align="left">${whoisInfo.Registrar}</td>
            </tr>
            <tr>
                <td align="right" width="35%"><b>注册日期：</b></td>
                <td align="left">${whoisInfo.RegistrationDate}</td>
            </tr>
            <tr>
                <td align="right" width="35%"><b>到期日期：</b></td>
                <td align="left">${whoisInfo.ExpirationDate}</td>
            </tr>
        </tbody>
    </table>
</body>
</html>

