<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增PayProduct</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link rel="stylesheet" href="<c:url value="/static/css/plugins/iCheck/custom.css"/>">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>新增payProduct</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="create-form" class="form-horizontal" action="<c:url value="/payProduct/save"/>" method="post">
						
						
						<!-- 插入区域 -->
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">payTypeCode</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="payTypeCode"  class="form-control" name="payTypeCode">
                               	</div>
                           	</div>
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">payWayCode</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="payWayCode"  class="form-control" name="payWayCode">
                               	</div>
                           	</div>
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">name</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="name"  class="form-control" name="name">
                               	</div>
                           	</div>
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">status</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="status"  class="form-control" name="status">
                               	</div>
                           	</div>
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">remark</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="remark"  class="form-control" name="remark">
                               	</div>
                           	</div>
					
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/payProduct/list"/>">返回</a>
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
    	
    	var icon = "<i class='fa fa-times-circle'></i> ";
    	$("#create-form").validate({
    		rules: {
    			
    					id:{
    						required: true,
    						maxlength:32
    					},
    					payTypeCode:{
    						required: true,
    						maxlength:4
    					},
    					payWayCode:{
    						required: true,
    						maxlength:3
    					},
    					name:{
    						required: true,
    						maxlength:32
    					},
    					status:{
    						required: true,
    						maxlength:2
    					},
    					remark:{
    						required:false, 
    						maxlength:255
    					},
    		},
    		messages: {
    		
    				id:{
    					required:icon+'请输入id',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				payTypeCode:{
    					required:icon+'请输入payTypeCode',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				payWayCode:{
    					required:icon+'请输入payWayCode',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				name:{
    					required:icon+'请输入name',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				status:{
    					required:icon+'请输入status',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				remark:{
    					required:icon+'请输入remark',
    					maxlength: icon+'长度不能超过{0}'
    				},
    		}
    	});
    	
    	$('.modify-flag').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    	
    }); 
    
    </script>

</body>

</html>
