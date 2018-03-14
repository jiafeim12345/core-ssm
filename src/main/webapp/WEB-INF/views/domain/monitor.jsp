<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../../taglibs.jsp"%>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>域名监测 - 数据监测平台</title>

    <!-- MetisMenu CSS -->
    <link href="/static/assets/manage/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">


    <!-- Custom CSS -->
    <link href="/static/assets/manage/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/static/assets/manage/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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

        function isNull(s) {
            if (s != null && s != "" && s != "undefined") {
                return true;
            } else {
                return false;
            }
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
            websocket.onerror = function(evnt) {
                $("#info").text("消息推送异常！")
            }
            websocket.onclose = function(evnt) {}
            websocket.onmessage = function(evnt) {
                var jsonData = JSON.parse(evnt.data);

                // 出现异常
                if (jsonData != '200') {
                    $("#info").text(jsonData.code.message)
                    return;
                }
                if (!isNull(jsonData.option) && jsonData.option == "config") {
                    // 更新查询时间
                    if (!isNull(jsonData.result.maxTime)) {
                        $("#maxTime").text(jsonData.result.maxTime);
                    }
                    if (!isNull(jsonData.result.minTime)) {
                        $("#minTime").text(jsonData.result.minTime);
                    }
                    return;
                }

                // 更新总数
                var total = jsonData.result.total;
                if (total != null && total != undefined && total != '') {
                    $("#total").text(total);
                }
                $("#prompt_tr").remove();
                // 清空样式
                $("#old_tbody tr").each(function() {
                    $(this).removeClass("danger");
                })
                $.each(jsonData.result.rows, function(i, item){
                    if (item.Domain == undefined) {
                        return;
                    }
                    var uuid = uuidGenerator();
                    $("#old_tbody").prepend("<tr id="+uuid+" class='danger'></tr>");
                    $("#"+uuid).append("<td>"+item.Domain+"</td>");
                    $("#"+uuid).append("<td>"+item.tel+"</td>");
                    $("#"+uuid).append("<td>"+item.EMail+"</td>");
                    $("#"+uuid).append("<td>"+item.RegDate+"</td>");
                    $("#"+uuid).append("<td>"+new Date().toLocaleTimeString()+"</td>");
                    $("#"+uuid).append("<td>"+ "<button type=\"button\" class=\"btn btn-info\"  onclick=\"whois(\'"+item.Domain+"\')\">Whois</button>" +"</td>");
                })
                alert("发现新域名！");
            };
        }

        $(function($){
            socketConn();
            init();

        })

        // whois 域名解析
        function whois(domainName) {
            layer.open({
                type: 2,
                title: 'Whois信息：' + domainName,
                maxmin: true,
                zIndex: 10,
                shadeClose: true, //点击遮罩关闭层
                area : ['600px' , '400px'],
                content: '${ctx}/admin/whois?domainName=' +domainName
            });
        }

        // 初始化
        function init() {
            $.ajax({
                type : "GET",
                url : "${ctx}/admin/domain/init",
                dataType : "json",
                success : function(jsonData) {
                    if (jsonData.code != '200') {
                        $("#prompt_td").text(jsonData.message);
                        return;
                    }
                    // 刷新域名和总数
                    $("#total").text(jsonData.result.total);
                    $.each(jsonData.result.rows, function(i,item) {
                        $("#prompt_tr").remove();
                        $("#old_tbody").append("<tr id='old_tr_"+i+"'></tr>");
                        $("#old_tr_"+i).append("<td>"+item.Domain+"</td>");
                        $("#old_tr_"+i).append("<td>"+item.tel+"</td>");
                        $("#old_tr_"+i).append("<td>"+item.EMail+"</td>");
                        $("#old_tr_"+i).append("<td>"+item.RegDate+"</td>");
                        $("#old_tr_"+i).append("<td>"+ new Date().toLocaleTimeString() +"</td>");
                        $("#old_tr_"+i).append("<td>"+ "<button type=\"button\" class=\"btn btn-info\"  onclick=\"whois(\'"+item.Domain+"\')\">Whois</button>" +"</td>");
                    })
                },
                error : function(e) {
                    $("#prompt_td").text(jsonData.message);
                    return;
                }

            });

        }

    </script>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Domain 数据监测平台</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <%--<li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        --%>
                        <li><a href="${ctx}/api/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li>
                        <li>
                            <a href="${ctx}/admin/domain" class="active"><i class="fa fa-bar-chart-o fa-fw"></i> 域名监测</a>
                        </li>
                        <li>
                            <a href="${ctx}/admin/domainConfig"><i class="fa fa-wrench fa-fw"></i> 系统设置<%--<span class="fa arrow"></span>--%></a>
                            <%--<ul class="nav nav-second-level">
                                <li>
                                    <a href="flot.html">Flot Charts</a>
                                </li>
                                <li>
                                    <a href="morris.html">Morris.js Charts</a>
                                </li>
                            </ul>--%>
                        </li>

                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">域名监测</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">

                        <div class="panel-heading">

                            <!-- 消息提示 -->
                            <div>
                            域名总数：<span id="total"></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                     <span>刷新频率：<span id="minTime">${minTime}</span>&nbsp;~&nbsp;
                                     <span id="maxTime">${maxTime}</span>&nbsp;秒</span>
                                  <br>
                                     <span id="info" style="color: red"></span>
                            </div>

                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th width="16%">域名</th>
                                        <th width="16%">电话</th>
                                        <th width="16%">邮箱</th>
                                        <th width="16%">注册时间</th>
                                        <th width="16%">刷新时间</th>
                                        <th width="16%">操作</th>
                                    </tr>
                                </thead>
                                <tbody id="old_tbody">
                                    <tr id = "prompt_tr">
                                        <td colspan="6" align="center" id="prompt_td">正在初始化 ...</td>
                                    </tr>
                                    <%--<tr>
                                        <td>Trident</td>
                                        <td>Internet Explorer 4.0</td>
                                        <td>Win 95+</td>
                                        <td class="center">4</td>
                                        <td class="center">X</td>
                                        <td class="center">X</td>
                                    </tr>--%>
                                </tbody>
                            </table>    
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


    <!-- Metis Menu Plugin JavaScript -->
    <script src="/static/assets/manage/vendor/metisMenu/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->

    <!-- Custom Theme JavaScript -->
    <script src="/static/assets/manage/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
</body>

</html>
