<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../taglibs.jsp"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>登录 - 数据监测平台</title>

        <!-- CSS -->
        <link rel="stylesheet" href="/static/assets/login/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="/static/assets/login/css/form-elements.css">
        <link rel="stylesheet" href="/static/assets/login/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <%--<link rel="shortcut icon" href="/static/assets/login/ico/favicon.png">--%>
        <%--<link rel="apple-touch-icon-precomposed" sizes="144x144" href="/static/assets/login/ico/apple-touch-icon-144-precomposed.png">--%>
        <%--<link rel="apple-touch-icon-precomposed" sizes="114x114" href="/static/assets/login/ico/apple-touch-icon-114-precomposed.png">--%>
        <%--<link rel="apple-touch-icon-precomposed" sizes="72x72" href="/static/assets/login/ico/apple-touch-icon-72-precomposed.png">--%>
        <%--<link rel="apple-touch-icon-precomposed" href="/static/assets/login/ico/apple-touch-icon-57-precomposed.png">--%>

    </head>

    <body>

		<!-- Top menu -->
		<nav class="navbar navbar-inverse navbar-no-bg" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<%--<a class="navbar-brand" href="index.html">Bootstrap Registration Form Template</a>--%>
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="top-navbar-1">
					<ul class="nav navbar-nav navbar-right">
						<li>
							<span class="li-text">
								Here are some friendly
							</span> 
							<a href="#"><strong>links </strong></a>
							<span class="li-social">
								<a href="#"><i class="fa fa-facebook"></i></a> 
								<a href="#"><i class="fa fa-twitter"></i></a> 
								<a href="#"><i class="fa fa-envelope"></i></a> 
								<a href="#"><i class="fa fa-skype"></i></a>
							</span>
						</li>
					</ul>
				</div>
			</div>
		</nav>

        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-7 text">
                            <h1><strong>Domain </strong>数据监测平台</h1>
                            <div class="description">
                            	<p>
	                            	Domain Data Monitoring Plateform
	                            	<%--Download it on <a href="#"><strong>AZMIND</strong></a>, customize and use it as you like!--%>
                            	</p>
                            </div>
                        </div>
                        <div class="col-sm-5 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Login now</h3>
                            		<p>
										Fill the username and password below :<br>
										<b style="color: red">${message}</b>
										<%--<c:choose>
										    <c:when test="${empty message}">

										    </c:when>
											<c:otherwise>
												<b style="color: red">${message}</b>
											</c:otherwise>
									   </c:choose>--%>
									</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-pencil"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form role="form" action="${ctx}/login/authenticate" method="post" class="registration-form">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Username...</label>
			                        	<input type="text" name="username" placeholder="Username..." class="form-first-name form-control" id="form-first-name">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-last-name">Password...</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-last-name form-control" id="form-last-name">
			                        </div>
			                        <!-- class="form-group">
			                        	<label class="sr-only" for="form-email">Email</label>
			                        	<input type="text" name="form-email" placeholder="Email..." class="form-email form-control" id="form-email">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-about-yourself">About yourself</label>
			                        	<textarea name="form-about-yourself" placeholder="About yourself..." 
			                        				class="form-about-yourself form-control" id="form-about-yourself"></textarea>
			                        </div> -->
									<div class="form-group">
										<label class="sr-only">submit button</label>
										<button type="submit" class="btn">Login !!!</button>
									</div>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="copyrights">Collect from <a href="http://www.cssmoban.com/"  title="网站模板">网站模板</a></div>
        </div>


        <!-- Javascript -->
        <script src="/static/assets/login/js/jquery.backstretch.min.js"></script>
        <script src="/static/assets/login/js/retina-1.1.0.min.js"></script>
        <script src="/static/assets/login/js/scripts.js"></script>
        
        <!--[if lt IE 10]>
            <script src="/static/assets/login/js/placeholder.js"></script>
        <![endif]-->

    </body>

</html>