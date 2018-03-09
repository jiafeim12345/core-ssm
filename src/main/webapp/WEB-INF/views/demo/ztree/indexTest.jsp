<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ztree</title>
<script type="text/javascript" src="${ctx}/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/zTreeStyle/zTreeStyle.css" type="text/css">
<SCRIPT type="text/javascript">
	/* var zNodes = [ {
		id : 1,
		pId : 0,
		name : "随意勾选 1",
		open : true
	}, {
		id : 11,
		pId : 1,
		name : "随意勾选 1-1",
		open : true
	}, {
		id : 111,
		pId : 11,
		name : "随意勾选 1-1-1"
	}, {
		id : 112,
		pId : 11,
		name : "随意勾选 1-1-2"
	}, {
		id : 12,
		pId : 1,
		name : "随意勾选 1-2",
		open : true
	}, {
		id : 121,
		pId : 12,
		name : "随意勾选 1-2-1"
	}, {
		id : 122,
		pId : 12,
		name : "随意勾选 1-2-2"
	}, {
		id : 2,
		pId : 0,
		name : "随意勾选 2",
		checked : true,
		open : true
	}, {
		id : 21,
		pId : 2,
		name : "随意勾选 2-1"
	}, {
		id : 22,
		pId : 2,
		name : "随意勾选 2-2",
		open : true
	}, {
		id : 221,
		pId : 22,
		name : "随意勾选 2-2-1",
		checked : true
	}, {
		id : 222,
		pId : 22,
		name : "随意勾选 2-2-2"
	}, {
		id : 23,
		pId : 2,
		name : "随意勾选 2-3"
	} ]; */

	$(function($) {
		var setting = {
			check : {
				enable : true,
				chkStyle : "checkbox"
			},
			data : {
				simpleData : {
					enable : false
				},
				key : {
					name : "no",
					children : "children"
				}
			},
		};

		var treeNodes = new Array();
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/menu/testZtree",
			async : false,
			dataType : "json",
			success : function(jsonData) {
				/* console.log(JSON.stringify(jsonData)); */

				$.each(jsonData, function(i, n) {
					treeNodes[i] = n;
					/* alert(JSON.stringify(treeNodes[i])) */
				});

			},
			error : function(e) {
				alert("json error");
			}

		});

		var ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
		setCheck();
		ztreeObj.expandAll(true);
	});

	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = {
			"Y" : "ps",
			"N" : "ps"
		};
		zTree.setting.check.chkboxType = type;
	}
</SCRIPT>
</head>

<body>
	<h1>菜单树</h1>
	<div class="content_wrap">
		<div class="zTreeDemoBackground left">
			<ul id="treeDemo" class="ztree"></ul>
		</div>

	</div>
</body>
</html>