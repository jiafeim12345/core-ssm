<%--
  Created by IntelliJ IDEA.
  User: ZZWang
  Date: 2018/1/22
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>万网域名实时更新</title>

    <script type="text/javascript">

        // 建立socket连接
        function socketConn() {
            var websocket;
            var host = window.location.host;
            var connUrl = "/webSocketServer";
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://" + host + connUrl);
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket("ws://" + host + connUrl);
            } else {
                websocket = new SockJS("http://" + host + "/sockjs"
                    + connUrl);
            }

            websocket.onopen = function(evnt) {}
            websocket.onerror = function(evnt) {}
            websocket.onclose = function(evnt) {}
            websocket.onmessage = function(evnt) {
                alert(evnt.data)
            };
        }

        $(function($){
            socketConn();
            var oldDomainMap = ${oldDomainMap};
            $("#total").text(oldDomainMap['Total']);
            $.each(oldDomainMap['Rows'],function(i,item){
                $("#old_tbody").append("<tr id='old_tr_"+i+"'></tr>");
                $("#old_tr_"+i).append("<td>"+item.Domain+"</td>");
                $("#old_tr_"+i).append("<td>"+item.tel+"</td>");
                $("#old_tr_"+i).append("<td>"+item.EMail+"</td>");
                $("#old_tr_"+i).append("<td>"+item.Registrar+"</td>");
                $("#old_tr_"+i).append("<td>"+item.RegDate+"</td>");
            })
        })
    </script>
</head>

<body>
<h3>域名总数：<span id="total"></span></h3>
<table class="table table-hover">
    <caption  style="text-align:center"><h2><b>最新域名</b></h2></caption>
    <thead>
        <tr>
            <th>域名</th>
            <th>电话</th>
            <th>注册商</th>
            <th>注册时间</th>
        </tr>
    </thead>
    <tbody id="new_tbody" style="font-size: 20px">
        <tr>
            <td colspan="4" align="center">暂无数据...</td>
        </tr>
    </tbody>

</table>
<br><br>
<table class="table table-hover ">
    <caption style="text-align:center"><h2><b>最近50条域名</b></h2></caption>
    <thead>
        <tr>
            <th>域名</th>
            <th>电话</th>
            <th>邮箱</th>
            <th>注册商</th>
            <th>注册时间</th>
        </tr>
    </thead>
    <tbody id="old_tbody" style="font-size: 20px">
    </tbody>
</table>
</body>
</html>
