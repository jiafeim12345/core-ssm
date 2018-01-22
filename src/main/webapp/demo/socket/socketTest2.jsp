<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>WebSocket/SockJS Echo Sample (Adapted from Tomcat's echo sample)</title>
<style type="text/css">
#connect-container {
	float: left;
	width: 400px
}

#connect-container div {
	padding: 5px;
}

#console-container {
	float: left;
	margin-left: 15px;
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
<a href="${pageContext.request.contextPath }/test/log4j" >测试日志</a>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>

<script type="text/javascript">
	function conn() {
		var host = window.location.host;
		alert("...")
		var websocket;
		
			websocket = new SockJS("http://" + host
					+ "/sockjs/webSocketIMServer");
		
		websocket.onopen = function(evnt) {
			console.log("websocket连接上");
		};
		websocket.onmessage = function(evnt) {
			messageHandler(evnt.data);
		};
		websocket.onerror = function(evnt) {
			console.log("websocket错误");
		};
		websocket.onclose = function(evnt) {
			console.log("websocket关闭");
		}
	}
</script>
</head>
<body>
	<button onclick="conn()" name="test" title="测试连接" value="test" >测试</button>
</body>
</html>
