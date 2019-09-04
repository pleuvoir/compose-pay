<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改支付种类</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link rel="stylesheet" href="<c:url value="/static/css/plugins/iCheck/custom.css"/>">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>修改参数</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="edit-form" class="form-horizontal" action="<c:url value="/payType/update"/>" method="post">
						<input type="hidden" class="form-control" name="id" value="${old.id}" >
							<div class="form-group">
                            	<label class="col-lg-2 control-label">支付种类代码</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="支付种类代码" class="form-control" name="payTypeCode" value="${old.payTypeCode}" readonly="readonly">
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">支付种类名称</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="支付种类名称" class="form-control" name="payTypeName" value="${old.payTypeName}">
                               	</div>
                           	</div>
	                        <div class="form-group">
                            	<label class="col-lg-2 control-label">备注</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="备注" class="form-control" name="remark" value="${old.remark}">
                               	</div>
                           	</div>
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/payType/list"/>">返回</a>
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
	<script src="<c:url value="/static/js/plugins/iCheck/icheck.min.js"/>"></script>
    <script>
    $(document).ready(function () {
    	addValidates();
    	
    	var icon = "<i class='fa fa-times-circle'></i> ";
    	$("#edit-form").validate({
    		rules: {
    			payTypeCode:{
    				required:true,
    				maxlength:4
    			},
    			payTypeName:{
    				required:true,
    				maxlength:32
    			},
    		},
    		messages: {
    			code: {
    				required:icon+'请输入支付种类代码',
    				maxlength: icon+'长度不能超过{0}'
    			},
    			name: {
    				required:icon+'请输入支付种类名称',
    				maxlength: icon+'长度不能超过{0}'
    			},
    		}
    	});
    	
    }); 
    
    </script>

</body>

</html>