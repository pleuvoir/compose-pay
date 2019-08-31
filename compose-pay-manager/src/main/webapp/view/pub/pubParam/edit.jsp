<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改参数</title>
	
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
	                    
						<form id="edit-form" class="form-horizontal" action="<c:url value="/pubParam/update"/>" method="post">
							<div class="form-group">
                            	<label class="col-lg-2 control-label">编码</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="编码" class="form-control" name="code" value="${params.code}" readonly="readonly">
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">名称</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="名称" class="form-control" readonly="readonly" name="name" value="${params.name}">
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">分组编码</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="分组编码" class="form-control" readonly="readonly" name="groupCode" value="${params.groupCode}">
                               	</div>
                           	</div>
                           	<div class="form-group hide">
                            	<label class="col-lg-2 control-label">类型1</label>
                               	<div class="col-lg-8">
                               		<label class="radio-inline i-checks type-check"> <input type="radio" value="1" name="type" autocomplete="off" ${params.type=='1' ? 'checked' : ''}> <i></i> 小数</label>
	                               	<label class="radio-inline i-checks type-check"> <input type="radio" value="2" name="type" autocomplete="off" ${params.type=='2' ? 'checked' : ''}> <i></i> 整数</label>
	                               	<label class="radio-inline i-checks type-check"> <input type="radio" value="3" name="type" autocomplete="off" ${params.type=='3' ? 'checked' : ''}> <i></i> 字符</label>
	                               	<label class="radio-inline i-checks type-check"> <input type="radio" value="4" name="type" autocomplete="off" ${params.type=='4' ? 'checked' : ''}> <i></i> 布尔</label>
                               	</div>
                           	</div>
                           	<div class="form-group param-val ${params.type=='1' ? '' : 'hide'}" id="val-decimal">
                            	<label class="col-lg-2 control-label">小数值</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="小数值" class="form-control" name="decimalVal" value="${params.decimalVal}">
                               	</div>
                           	</div>
                           	<div class="form-group param-val ${params.type=='2' ? '' : 'hide'}" id="val-int">
                            	<label class="col-lg-2 control-label">整数值</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="整数值" class="form-control" name="intVal" value="${params.intVal}">
                               	</div>
                           	</div>
                           	<div class="form-group param-val ${params.type=='3' ? '' : 'hide'}" id="val-str">
                            	<label class="col-lg-2 control-label">字符值</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="字符值" class="form-control" name="strVal" value="${params.strVal}">
                               	</div>
                           	</div>
                           	<div class="form-group param-val ${params.type=='4' ? '' : 'hide'}" id="val-boolean">
                            	<label class="col-lg-2 control-label">布尔值</label>
                               	<div class="col-lg-8">
                               		<select name="booleanVal" class="form-control">
                               			<option value="" <c:if test="${empty params.type}">selected="selected"</c:if>></option>
                               			<option value="0" <c:if test="${params.booleanVal eq '0'}">selected="selected"</c:if>>false</option>
                               			<option value="1" <c:if test="${params.booleanVal eq '1'}">selected="selected"</c:if>>true</option>
                               		</select>
                               	</div>
                           	</div>
                           	<div class="form-group hide">
                            	<label class="col-lg-2 control-label">能否修改</label>
                               	<div class="col-lg-8">
                               		<label class="radio-inline i-checks modify-flag"> <input type="radio" value="1" name="modifyFlag" autocomplete="off" ${params.modifyFlag=='1' ? 'checked' : ''}> <i></i> 允许</label>
	                               	<label class="radio-inline i-checks modify-flag"> <input type="radio" value="2" name="modifyFlag" autocomplete="off" ${params.modifyFlag=='2' ? 'checked' : ''}> <i></i> 不允许</label>
                               	</div>
                           	</div>
	                        <div class="form-group">
                            	<label class="col-lg-2 control-label">备注</label>
                               	<div class="col-lg-8">
                               		<input type="text" placeholder="备注" class="form-control" name="remark" value="${params.remark}">
                               	</div>
                           	</div>
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/pubParam/list"/>">返回</a>
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
    			code:{
    				required:true,
    				maxlength:20
    			},
    			name:{
    				required:true,
    				maxlength:50
    			},
    			decimalVal:{
    				decimal:true,
    				maxlength:18,
    			},
    			intVal:{
    				digits:true,
    				maxlength:10,
    			},
    			strVal:{
    				maxlength:255,
    			},
    			booleanVal:{
    				required:true
    			},
    			type:'required',
    			modifyFlag:'required'
    		},
    		messages: {
    			code: {
    				required:icon+'请输入编码',
    				maxlength: icon+'长度不能超过{0}'
    			},
    			name: {
    				required:icon+'请输入名称',
    				maxlength: icon+'长度不能超过{0}'
    			},
    			decimalVal: {
    				maxlength: icon+'长度不能超过{0}'
    			},
    			intVal: {
    				digits: icon+'必须是整数',
    				maxlength: icon+'长度不能超过{0}'
    			},
    			strVal: {
    				maxlength: icon+'长度不能超过{0}'
    			},
    			booleanVal: {
    				required:icon+'请选择布尔值'
    			},
    			type: icon+'请选择类型',
    			modifyFlag: icon+'请选择能否修改'
    		}
    	});
    	
    	$('.modify-flag').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    	
    	initTypeCheck();
    }); 
    
    function initTypeCheck(){
    	var typeCheck = $('.type-check').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    	typeCheck.on('ifChecked', function(evn){
    		var type = $(this).val();
    		$('.param-val').addClass('hide');
    		switch (type) {
			case '1':
				$('#val-decimal').removeClass('hide');
				break;
			case '2':
				$('#val-int').removeClass('hide');		
				break;
			case '3':
				$('#val-str').removeClass('hide');
				break;
			case '4':
				$('#val-boolean').removeClass('hide');
				break;
			default:
				break;
			}
    	});
    }
    
    function addValidates(){
    	$.validator.addMethod("decimal", function(value, element) {   
    	    var decimal = /^(\-)?\d{0,12}(\.\d{0,6})?$/;
    	    return this.optional(element) || (decimal.test(value));
    	}, "整数位最长12，小数位最长6");
    }
    </script>

</body>

</html>
