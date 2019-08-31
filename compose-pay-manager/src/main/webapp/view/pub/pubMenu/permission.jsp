<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>绑定权限</title>
    
    <jsp:include page="../../_import.jsp"/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>绑定权限 - ${menu.title}</h5>
                    </div>
                    <div class="ibox-content">
	                    <t:alert message="${message}"/>
	                    
						<form id="edit-form" class="form-horizontal" action="<c:url value="/pubMenu/permissionSave"/>" method="post">
		                    <input type="text" class="hide" name="id" value="<c:out value="${menu.id}"/>">
	                        <table class="table table-bordered">
	                            <thead>
	                                <tr>
	                                    <th>code</th>
	                                    <th>名称</th>
	                                    <th><button type="button" id="batch-add" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i></button></th>
	                                </tr>
	                            </thead>
	                            <tbody id="tr-tpl">
	                            	<c:forEach items="${perList}" var="per">
		                                <tr>
		                                    <td>
		                                    	<input type="text" placeholder="code" class="form-control code" name="perMap['${per.id }'].code" value="<c:out value="${per.code}"/>">
		                                    </td>
		                                    <td>
		                                    	<input type="text" placeholder="名称" class="form-control name" name="perMap[${per.id }].name" value="<c:out value="${per.name}"/>">
		                                    </td>
		                                    <td data-title="操作">
												<button type="button" class="btn btn-xs btn-danger batch-remove"><i class="fa fa-trash"></i></button>
											</td>
		                                </tr>
	                            	</c:forEach>
	                            </tbody>
	                        </table>
	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn btn-white" href="<c:url value="/pubMenu/list"/>">返回</a>
                                </div>
                            </div>
						</form>
                    </div>
                </div>
    		
    		</div>
    	</div>
    </div>
    
    <!-- 克隆的模版 -->
	<table id="tbl-tpl" class="hide">
		<tr>
			<td>
				<input type="text" placeholder="code" class="form-control code" name="perMap[0].code">
			</td>
			<td>
                <input type="text" placeholder="名称" class="form-control name" name="perMap[0].name">
            </td>
            <td data-title="操作">
				<button type="button" class="btn btn-xs btn-danger hide batch-remove"><i class="fa fa-trash"></i></button>
			</td>
		</tr>
	</table>

	<!-- jQuery Validation plugin javascript-->
    <script src="<c:url value="/static/js/plugins/validate/jquery.validate.min.js"/>"></script>
    <script src="<c:url value="/static/js/plugins/validate/messages_zh.min.js"/>"></script>

<script>

//初始行数
var batchTrCount = 0;

//初始化行数
function intiCoutn(){
	batchTrCount = $("#tr-tpl tr").length;
}

//添加行
function addBatch(){
	//最多20行
	if(batchTrCount > 20){
		return;
	}
	batchTrCount += 1;
	var tr = $("#tbl-tpl tr").clone(false);
	tr.find(".batch-remove").removeClass("hide");	//显示移除按钮
	tr.find(".batch-remove").bind("click", removeBatch);	//绑定移除事件
	
	addBatchNameIncrease(tr);	//修改新添加行的name
	$("#tr-tpl").append(tr);	//添加克隆的行
	
	addBatchCheck(tr);		//添加校验
	
}

//移除行
function removeBatch(){
	var tr = $(this).parent().parent();
	removeBatchCheck(tr);
	tr.remove();
}

//移除校验
function removeBatchCheck(tr){
	tr.find("input").rules("remove");
}

//修改新添加行的name
function addBatchNameIncrease(tr){
	$.each(tr.find("input"), function(i,n){
		var cur = $(n);
		var name = cur.attr("name").replace("[0]", "[" + batchTrCount + "]");
		cur.attr("name", name);
	});
}

//添加校验
function addBatchCheck(tr){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$.each(tr.find("input"), function(i,n){
		var cur = $(n);
		if(cur.hasClass("code")){
			cur.rules("add", {
				required:true, 
				messages:{
					required:icon+"必须填写权限code"
				}
			});
		}
		if(cur.hasClass("name")){
			cur.rules("add", {
				required:true, 
				messages:{
					required:icon+"必须填写权限名称"
				}
			});
		}
		
	});
}

$(document).ready(function () {
	//初始化行数
	intiCoutn();
	//添加行
	$("#batch-add").bind("click", addBatch);
	//移除行
	$(".batch-remove").bind("click", removeBatch);
  	//初始化校验
  	$("#edit-form").validate();
}); 
</script>

</body>

</html>
