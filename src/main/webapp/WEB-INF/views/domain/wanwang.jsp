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

        function uuidGenerator() {
            var s = [];
            var hexDigits = "0123456789abcdef";
            for (var i = 0; i < 36; i++) {
                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
            }
            s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
            s[8] = s[13] = s[18] = s[23] = "-";

            var uuid = s.join("");
            return uuid;
        }

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
                var dataJson = JSON.parse(evnt.data);
                if (dataJson['option'] == 'updateMaxTime') {
                    $("#maxTime").text(dataJson['value']);
                    return;
                }

                if (dataJson['option'] == 'updateMinTime') {
                    $("#minTime").text(dataJson['value']);
                    return;
                }

                // 异常处理
                if (dataJson['error'] != null && dataJson['error'] != undefined && dataJson['error'] != '') {
                    $("body").html("<h3>" + dataJson['error'] + "</h3>");
                    return;
                }
                // 更新总数
                var total = dataJson['Total'];
                if (total != null && total != undefined && total != '') {
                    $("#total").text(total);
                }
                $("#prompt").remove();
                // 清空样式
                $("#new_tbody tr").each(function(){
                    $(this).removeClass("danger");
                })
                $.each(dataJson['Rows'], function(i, item){
                    if (item.Domain == undefined) {
                        return;
                    }
                    var uuid = uuidGenerator();
                    $("#new_tbody").prepend("<tr id="+uuid+" class='danger'></tr>");
                    $("#"+uuid).append("<td>"+item.Domain+"</td>");
                    $("#"+uuid).append("<td>"+item.tel+"</td>");
                    $("#"+uuid).append("<td>"+item.EMail+"</td>");
                    $("#"+uuid).append("<td>"+item.RegDate+"</td>");
                    $("#"+uuid).append("<td>"+new Date().toLocaleTimeString()+"</td>");
                })
                alert("发现新域名！");
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
                $("#old_tr_"+i).append("<td>"+item.RegDate+"</td>");
                $("#old_tr_"+i).append("<td>"+ new Date().toLocaleTimeString() +"</td>");
            })
        })
    </script>
</head>

<body>
<h4><span id="error"><a href="${ctx}/admin/wanwang/config">${error}</a></span></h4>
<h4>域名总数：<span id="total"></span>&nbsp;&nbsp;&nbsp;&nbsp;
    <span>刷新频率：<span id="minTime">${minTime}</span>&nbsp;~&nbsp;<span id="maxTime">${maxTime}</span>&nbsp;秒</span></h4>
<table class="table table-hover table-condensed">
    <caption  style="text-align:center"><h2><b>最新域名</b></h2></caption>
    <thead>
        <tr>
            <th width="20%">域名</th>
            <th width="20%">电话</th>
            <th width="20%">邮箱</th>
            <th width="20%">注册时间</th>
            <th width="20%">刷新时间</th>
        </tr>
    </thead>
    <tbody id="new_tbody" style="font-size: 20px">
        <tr id = "prompt">
            <td colspan="5" align="center">暂无数据...</td>
        </tr>
    </tbody>

</table>
<br><br>
<table class="table table-hover table-condensed">
    <caption style="text-align:center"><h2><b>最近50条域名</b></h2></caption>
    <thead>
        <tr>
            <th width="20%">域名</th>
            <th width="20%">电话</th>
            <th width="20%">邮箱</th>
            <th width="20%">注册时间</th>
            <th width="20%">刷新时间</th>
        </tr>
    </thead>
    <tbody id="old_tbody" style="font-size: 20px">
    </tbody>
</table>
</body>
</html>
