<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>权限分配</title>
    
    <jsp:include page="../../_import.jsp"/>
	<link href="<c:url value="/static/css/bootstrap-zTree.css"/>" rel="stylesheet" type="text/css" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>权限分配</h5>
                    </div>
                    <div class="ibox-content">
	                    
						<form id="permission-form" class="form-horizontal" action="<c:url value="/pubRole/permissionSave"/>" method="post">
	                        <div class="row">
	                            <div class="col-md-3">
	                                <div class="form-group">
	                                	<label class="col-lg-4 control-label">名称:</label>
                                    	<div class="col-lg-8">
                                    		<span class="form-control no-borders"><c:out value="${role.name}"/></span>
                                    		<input type="hidden" name="id" value="${role.id}"/>
                                    		<input type="hidden" id="strCHeck" name="strCHeck"/>
                                    	</div>
                                	</div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col-md-3">
	                                <ul id="ztree" class="ztree"></ul>
	                            </div>
	                        </div>
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/pubRole/list"/>">返回</a>
                                </div>
                            </div>
						</form>
                    </div>
                </div>
    		
    		</div>
    	</div>
    </div>

    <!-- jQuery Validation plugin javascript-->
    <script src="<c:url value="/static/js/plugins/validate/jquery.validate.min.js"/>"></script>
    <script src="<c:url value="/static/js/plugins/validate/messages_zh.min.js"/>"></script>
    <script src="<c:url value="/static/js/public_validator.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/jquery.ztree.all-3.5.min.js"/>"></script>
    
<script>
	var setting = {
		check:{
			enable:true
		},
		callback:{
			onCheck:function(evn,treeId,treeNode){
			}
		}
	};
	
	//初始化时展开选择的节点
	function foregroundsAll(ztreeObj){
		var nodes = ztreeObj.getCheckedNodes(true);
		for(var i =0;i<nodes.length;i++){
			ztreeObj.expandNode(nodes[i],true,false,false);
		}
	}
	
	$(function(){
		var zNodes = ${treeStr};
	    var ztreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	    
	    //初始化时展开选择的节点	
		foregroundsAll(ztreeObj);
		validateInit(ztreeObj);
		
	});
	
	function validateInit(ztreeObj){
		var form = $("#permission-form");
		form.validate({
    		rules: {
    		},
    		messages: {
    		},
    		submitHandler: function (form) {
    			var checkedNodes = ztreeObj.getCheckedNodes(true);
            	var strCHeck = "";
            	for(var i=0;i<checkedNodes.length;i++){
           			if(i==checkedNodes.length-1){
           				strCHeck = strCHeck+checkedNodes[i].value;
           			}else{
   	        			strCHeck = strCHeck+checkedNodes[i].value+",";
           			}
            	}
            	$("#strCHeck").val(strCHeck);
            	form.submit();
    		}
    	});
	}
</script>
    
</body>

</html>
