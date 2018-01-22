/*
 * websocket通用处理脚本
 */

/**
 * socket工具类
 */
function SocketUtils() {
	this.getSocketConn = function(url) {
		var websocket;
		var host = window.location.host;
		var projectName = getRootPath();
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://" + host + projectName + url);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + host + projectName + url);
		} else {
			websocket = new SockJS("http://" + host + projectName + "/sockjs"
					+ url);
		}
		return websocket;
	}
	
	this.getSocketConn = function(url, host) {
        var websocket;
        var projectName = getRootPath();
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + host + projectName + url);
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://" + host + projectName + url);
        } else {
            websocket = new SockJS("http://" + host + projectName + "/sockjs"
                + url);
        }
        return websocket;
    }

    this.getConnNoProjectName = function(url, host) {
        var websocket;
        var projectName = getRootPath();
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + host + url);
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://" + host + url);
        } else {
            websocket = new SockJS("http://" + host + "/sockjs"
                + url);
        }
        return websocket;
    }

}

// 获取项目根目录
function getRootPath() {
	// 获取当前网址，如： http://localhost:9527/zdss-web/login/login.do
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如：/zdss-web/login/login.do
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);

	// 获取主机地址，如： http://localhost:9527
	var localhostPath = curWwwPath.substring(0, pos);

	// 获取带"/"的项目名，如：/zdss-web
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);

	return projectName;
}

