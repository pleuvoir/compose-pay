<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改${dataModel.name}</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link rel="stylesheet" href="<c:url value="/static/css/plugins/iCheck/custom.css"/>">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>修改${dataModel.name}</h5>
                    </div>
                    <div class="ibox-content">
                        <t:alert message="${r"${message}"}"/>
	                    
						<form id="edit-form" class="form-horizontal" action="<c:url value="/${dataModel.name?uncap_first}/update"/>" method="post">
						<input type="hidden" class="form-control" name="id" value="${r"${old.id}"}" >


                        <!-- 修改区域 -->
                        <#list dataModel.metaData.columnExtendList as columnExtend>
                            <#if "${columnExtend.field}" != "id" >
                                <div class="form-group">
                                    <label class="col-lg-2 control-label">${columnExtend.field}</label>
                                    <div class="col-lg-8">
                                        <input type="text" value="${r"${old."}${columnExtend.field}}"  class="form-control" name="${columnExtend.field}">
                                    </div>
                                </div>
                            </#if>
                        </#list>


	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/${dataModel.name?uncap_first}/list"/>">返回</a>
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
        $("#edit-form").validate({
            rules: {

        <#list dataModel.metaData.columnExtendList as columnExtend>
        ${columnExtend.field}:{
            required:<#if "${columnExtend.isNullable}" == "true">false,</#if> <#if "${columnExtend.isNullable}" == "false">true,</#if>
                maxlength:${columnExtend.columnDisplaySize}
        },
        </#list>
    },
        messages: {

            <#list dataModel.metaData.columnExtendList as columnExtend>
            ${columnExtend.field}:{
                required:icon+'请输入${columnExtend.field}',
                    maxlength: icon+'长度不能超过{0}'
            },
            </#list>
        }
    });
    	
    }); 
    
    </script>

</body>

</html>
