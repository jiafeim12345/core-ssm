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

    <title>系统设置 - 数据监测平台</title>

    <!-- MetisMenu CSS -->
    <link href="/static/assets/manage/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">


    <!-- Custom CSS -->
    <link href="/static/assets/manage/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/static/assets/manage/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <script type="text/javascript">

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
                        <li class="divider"></li>--%>
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
                            <a href="${ctx}/api/admin/domain"><i class="fa fa-bar-chart-o fa-fw"></i> 域名监测</a>
                        </li>
                        <li>
                            <a href="${ctx}/api/admin/domainConfig" class="active"><i class="fa fa-wrench fa-fw"></i> 系统设置<%--<span class="fa arrow"></span>--%></a>
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
                    <h3 class="page-header">系统设置</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <c:if test="${not empty error}">
                                <div id="error" class="alert alert-danger">${error}</div>
                            </c:if>
                            系统设置
                            <c:if test="${not empty info}">
                                <span style="color: #2aabd2">&nbsp;&nbsp;${info}</span>
                            </c:if>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form role="form" action="${ctx}/api/admin/domainConfig" method="post">
                                        <div class="form-group">
                                            <label class="">刷新频率：秒</label>
                                            <div class="row">
                                                <div class="col-lg-4">
                                                    <input type="number" name="minTime" value="${minTime}"  class="form-control" placeholder="minTime">
                                                </div>
                                                <div class="col-lg-4">
                                                    <input type="number" name="maxTime" value="${maxTime}"  class="form-control" placeholder="maxTime">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label>Cookie</label>
                                            <textarea name="cookie" class="form-control" rows="3"></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-default">提 交</button>
                                        <button type="reset" class="btn btn-default">重 置</button>
                                    </form>
                                </div>

                            </div>
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
