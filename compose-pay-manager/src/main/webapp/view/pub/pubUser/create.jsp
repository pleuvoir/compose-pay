<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增参数</title>
    
    <jsp:include page="../../_import.jsp"/>
	<link href="<c:url value="/static/css/plugins/chosen/chosen.css"/>" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>新增用户</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="create-form" class="form-horizontal" action="<c:url value="/pubUser/save"/>" method="post">
	                        <div class="row">
	                            <div class="col-md-3">
	                                <div class="form-group">
	                                	<label class="col-lg-4 control-label">用户名</label>
                                    	<div class="col-lg-8">
                                    		<input type="text" placeholder="用户名" class="form-control" id="username" name="username">
                                    	</div>
                                	</div>
	                            </div>
	                            <div class="col-md-6">
	                                <div class="form-group">
	                                	<label class="col-lg-2 control-label">角色</label>
                                    	<div class="col-lg-10">
                                    		<select id="roleSelect" name="roles" data-placeholder="选择角色" class="chosen-select" multiple style="width:350px;" tabindex="4">
                                    			<c:forEach items="${roles }" var="role">
                                    				<option value="${role.id }" hassubinfo="true">${role.name }</option>
                                    			</c:forEach>
                                    		</select>
                                    	</div>
                                	</div>
	                            </div>
	                        </div>
	                        <div class="row">
	                        	<div class="col-md-3">
	                                <div class="form-group">
	                                	<label class="col-lg-4 control-label">登录密码</label>
                                    	<div class="col-lg-8">
                                    		<input id="password" type="password" placeholder="登录密码" class="form-control" name="password">
                                    	</div>
                                	</div>
	                            </div>
	                            <div class="col-md-3">
	                                <div class="form-group">
	                                	<label class="col-lg-4 control-label">再次输入登录密码</label>
                                    	<div class="col-lg-8">
                                    		<input type="password" placeholder="再次输入登录密码" class="form-control" name="password_repeat">
                                    	</div>
                                	</div>
	                            </div>
	                        </div>
	                   <!--      <div class="row">
	                            <div class="col-md-6">
	                                <div class="form-group">
	                                	<label class="col-lg-2 control-label">手机号码</label>
                                    	<div class="col-lg-10">
                                    		<input type="text" placeholder="请输入手机号码" class="form-control" name="phone">
                                    	</div>
                                	</div>
	                            </div>
	                        </div> -->
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
                                    <a class="btn btn-white" href="<c:url value="/pubUser/list"/>">返回</a>
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
    <!-- Chosen -->
    <script src="<c:url value="/static/js/plugins/chosen/chosen.jquery.js"/>"></script>

<script>

//初始化chosen
var config = {
    '.chosen-select': {},
    '.chosen-select-deselect': {
        allow_single_deselect: true
    },
    '.chosen-select-no-single': {
        disable_search_threshold: 10
    },
    '.chosen-select-no-results': {
        no_results_text: 'Oops, nothing found!'
    },
    '.chosen-select-width': {
        width: "95%"
    }
}
for (var selector in config) {
    $(selector).chosen(config[selector]);
}


$(document).ready(function () {
	

	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#create-form").validate({
		rules: {
			username:{
				required:true,
				remote:{
					type:"POST",  
                    url:"<c:url value="/pubUser/isExis"/>", //请求地址
                    data:{  
                        username:function(){ return $("#username").val(); }  
                    }  
				},
				maxlength:30
			},
			password:{
				required:true,
				maxlength:80
			},
		/* 	phone:{
				required:true,
				maxlength:11,
				digits:true
			}, */
			roles:'required',
			password_repeat:{
				required: true,
				equalTo: "#password"
			},
			remark:{
				maxlength:255
			}
		},
		messages: {
			username:{
				required: icon+'请输入用户名',
				remote:'已存在该用户名',
				maxlength:'用户名最多输入30个字符'
			},
			password: {
				required:icon+'请输入登录密码',
				maxlength:'登录密码最多输入80个字符'
			},
			roles: icon+'请选择角色',
			password_repeat: {
				required:icon+'请再次输入登录密码',
				equalTo:icon+'再次输入密码不一致'
			},
		/* 	phone: {
				required:icon+'请输入手机号码',
				maxlength:icon+'不能超过11位',
				digits:icon+'只能输入整数'
			}, */
			remark:{
				maxlength:'备注最多输入255个字符'
			}
		},
		ignore: ':hidden:not(#roleSelect)'
	});
}); 
</script>

</body>

</html>
