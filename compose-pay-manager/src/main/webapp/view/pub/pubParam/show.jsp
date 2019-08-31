<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看参数</title>
	
	<jsp:include page="../../_import.jsp"/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>查看参数</h5>
                    </div>
                    <div class="ibox-content">
	                    
						<form class="form-horizontal">
							<div class="form-group">
                            	<label class="col-lg-2 control-label">编码</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><c:out value="${params.code}"/></span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">名称</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><c:out value="${params.name}"/></span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">分组编码</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><c:out value="${params.groupCode}"/></span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">小数值</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><fmt:formatNumber value="${params.decimalVal}" pattern="#.00"/></span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                           		<label class="col-lg-2 control-label">整数值</label>
                              	<div class="col-lg-8">
                              		<span class="form-control no-borders"><c:out value="${params.intVal}"/></span>
                              	</div>
                          	</div>
                          	<div class="form-group">
                            	<label class="col-lg-2 control-label">字符值</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><c:out value="${params.strVal}"/></span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">布尔值</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders">
                               		<c:choose>
	                               				<c:when test="${params.booleanVal eq '0'}">false</c:when>
	                               				<c:when test="${params.booleanVal eq '1'}">true</c:when>
	                               	</c:choose>
                               		</span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                            	<label class="col-lg-2 control-label">类型</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders">
                               			<span class="label label-info">
	                               			<c:choose>
	                               				<c:when test="${params.type=='1'}">小数</c:when>
	                               				<c:when test="${params.type=='2'}">整数</c:when>
	                               				<c:when test="${params.type=='3'}">字符</c:when>
	                               				<c:when test="${params.type=='4'}">布尔</c:when>
	                               			</c:choose>
                               			</span>
                               		</span>
                               	</div>
                           	</div>
                           	<div class="form-group">
                           		<label class="col-lg-2 control-label">能否修改</label>
                              	<div class="col-lg-8">
                              		<span class="form-control no-borders">
                              			<c:choose>
                              				<c:when test="${params.modifyFlag=='1'}"><span class="label label-primary">允许</span></c:when>
                              				<c:when test="${params.modifyFlag=='2'}"><span class="label label-warning">不允许</span></c:when>
                              			</c:choose>
                              		</span>
                              	</div>
                          	</div>
                          	<div class="form-group">
                            	<label class="col-lg-2 control-label">备注</label>
                               	<div class="col-lg-8">
                               		<span class="form-control no-borders"><c:out value="${params.remark}"/></span>
                               	</div>
                           	</div>
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a class="btn btn-white" href="<c:url value="/pubParam/list"/>">返回</a>
                                </div>
                            </div>
						</form>
                    </div>
                </div>
    		
    		</div>
    	</div>
    </div>

</body>

</html>
