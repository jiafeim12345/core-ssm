<%--
  Created by IntelliJ IDEA.
  User: ZZWang
  Date: 2018/1/22
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>万网域名实时更新</title>
</head>
<script type="text/javascript">
</script>
<body>
    <h4><span id="total">${info}</span></h4>
    <form id="info-form" action="${ctx}/api/admin/domainConfig" method="post" >
        <table class="table table-hover table-condensed">
            <caption  style="text-align:center"><h2><b>cookie更新</b></h2></caption>
            <tbody id="new_tbody" style="font-size: 20px">
                <tr>
                    <td align="center"><textarea name="cookie" cols="100" rows="14" placeholder="请输入cookie"></textarea></td>
                </tr>
                <tr>
                    <td align="center">设定查询时间：<input type="number" name="minTime" value="${minTime}" />~<input type="number" name="maxTime" value="${maxTime}" />秒</td>
                </tr>
                <tr>
                    <td align="center"><input type="submit" value="提  交" style="width: 20%" ></input></td>
                </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
