<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增支付产品</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link rel="stylesheet" href="<c:url value="/static/css/plugins/iCheck/custom.css"/>">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>新增支付产品</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="create-form" class="form-horizontal" action="<c:url value="/payProduct/save"/>" method="post">
						
						
						<!-- 插入区域 -->
						
						<div class="row">
						   <div class="col-md-12">
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">支付种类</label>
                               	<div class="col-lg-6">
                              	 	<select class="form-control" name="payTypeCode">
										<option value="">-</option>
										<c:forEach items="${payTypes}" var="payType">
											<option value="${payType.payTypeCode}">${payType.payTypeName} </option>
										</c:forEach>
									</select>
                               	</div>
                           	</div>
                           	</div>
                           	</div>
                           	
                           	<div class="row">
	                       <div class="col-md-12">      
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">支付方式</label>
                               	<div class="col-lg-6">
                              	 	<select class="form-control" name="payWayCode">
										<option value="">-</option>
										<c:forEach items="${payWays}" var="payWay">
											<option value="${payWay.payWayCode}">${payWay.payWayName} </option>
										</c:forEach>
									</select>
                               	</div>
                           	</div>
                           	</div>
                           	</div>
                           	
                           	<div class="row">
                           	<div class="col-md-12">      
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">产品名称</label>
                               	<div class="col-lg-6">
                               		<input type="text" placeholder="产品名称"  class="form-control" name="name">
                               	</div>
                           	</div>
                           	</div>
                           	</div>
                           	
                           	<div class="row">
                           	<div class="col-md-12">    
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">状态</label>
                               	<div class="col-lg-6">
                               			<select class="form-control" name="status">
												<!-- <option value="">-</option> -->
												<option value="0">可用</option>
												<option value="1">不可用</option>
										</select>
                               	</div>
                           	</div>
                           	</div>
                           	</div>
                           	
                           	<div class="row">
                           	<div class="col-md-12">    
		    				<div class="form-group">
                            	<label class="col-lg-2 control-label">备注</label>
                               	<div class="col-lg-6">
                               	 <textarea rows="5" cols="70" class="form-control" placeholder="请输入备注" name="remark"></textarea>	 
                               	</div>
                           	</div>
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
    					required:icon+'请输入',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				payTypeCode:{
    					required:icon+'请输入',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				payWayCode:{
    					required:icon+'请输入',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				name:{
    					required:icon+'请输入',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				status:{
    					required:icon+'请输入',
    					maxlength: icon+'长度不能超过{0}'
    				},
    				remark:{
    					required:icon+'请输入',
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
