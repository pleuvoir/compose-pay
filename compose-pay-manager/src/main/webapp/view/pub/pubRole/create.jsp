<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增角色</title>
	
	<jsp:include page="../../_import.jsp"/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>新增角色</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="create-form" class="form-horizontal" action="<c:url value="/pubRole/save"/>" method="post">
	                        <div class="row">
	                            <div class="col-md-3">
	                                <div class="form-group">
	                                	<label class="col-lg-4 control-label">名称</label>
                                    	<div class="col-lg-8">
                                    		<input type="text" placeholder="名称" class="form-control" name="name">
                                    	</div>
                                	</div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col-md-6">
	                                <div class="form-group">
	                                	<label class="col-lg-2 control-label">备注</label>
                                    	<div class="col-lg-10">
                                    		<input type="text" placeholder="备注" class="form-control" name="remark">
                                    	</div>
                                	</div>
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

    <script>
    $(document).ready(function () {
    	var icon = "<i class='fa fa-times-circle'></i> ";
    	$("#create-form").validate({
    		rules: {
    			name:'required'
    		},
    		messages: {
    			name: icon+'请输入名称'
    		}
    	});
    }); 
    </script>

</body>

</html>
