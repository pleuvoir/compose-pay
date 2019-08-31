<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>角色管理</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link href="<c:url value="/static/css/plugins/jqgrid/ui.jqgrid.css?0820"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/plugins/sweetalert/sweetalert.css"/>" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<form id="query-form">
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">名称</label>
						<input type="text" class="form-control" placeholder="名称" name="name"/>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label"></label>
						<div class="query-btn">
							<button id="table-query" class="btn btn-success btn-sm" type="button"><i class="fa fa-search"></i>查询</button>
							<button id="table-remove" class="btn btn-warning btn-sm" type="button"><i class="fa fa-remove"></i>重置</button>
						</div>
					</div>
				</div>
			</form>
    	</div>
    
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox ">
                    <div class="ibox-title">
                        <h5>角色管理</h5>
                        <div class="ibox-tools">
	                        <shiro:hasPermission name="pubRole:add">
	                            <a class="btn btn-primary btn-xs" href="<c:url value="/pubRole/create"/>" title="新增">
	                                <i class="fa fa-plus"></i> 新增
	                            </a>
	                        </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                    
                        <div class="jqGrid_wrapper">
                            <table id="table-role"></table>
                            <div id="pager-role"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jqGrid -->
    <script src="<c:url value="/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"/>"></script>
	<script src="<c:url value="/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"/>"></script>
	<script src="<c:url value="/static/js/public_table.js"/>"></script>
	<script src="<c:url value="/static/js/sessionStorageUtil.js"/>"></script>
    <script src="<c:url value="/static/js/plugins/sweetalert/sweetalert.2.1.0.min.js"/>"></script>
    <script>
   	var j;
        $(document).ready(function () {
        	j = new JqGridControl({
                selector: '#table-role',
                button: '#table-query',
                form: '#query-form',
                pager: "#pager-role",
                formResetButton: '#table-remove',
                url: '<c:url value="/pubRole/query"/>'
			});

            j.useParamsCache({
                //注意：每个页面cacheId不可相同
                cacheId: 'role-list',
                autoQuery: true
            });

        	j.init({
                colNames: ['名称', '创建人', '创建时间', '备注', '操作'],
                colModel: [
                    {name: 'name', width: 40, sortable:false},
                    {name: 'createBy', width: 40, sortable:false},
                    {name: 'createTime', width: 40, sortable:false},
                    {name: 'remark', width: 50, sortable:false},
                    {name: 'id', width:150, fixed:true, sortable:false, resize:false, formatter:dataButton}
                ],

            });
        	
        	j.bindQuery();
            j.bindReset();
        	
        });
        
        //cellvalue  当前字段的值
        //rowdata 当前行对象
        function dataButton(cellvalue, options, rowdata){
        	var editUrl = '<c:url value="/pubRole/edit"/>';
        	var permissionUrl = '<c:url value="/pubRole/permission"/>';
        	editUrl = joinUrlParam(editUrl, {id: cellvalue});
        	permissionUrl = joinUrlParam(permissionUrl, {id: cellvalue});
        	return "<shiro:hasPermission name="pubRole:edit"><a class='btn-xs btn-info' href='"+editUrl+"' title='编辑'><i class='fa fa-edit'></i></a>&nbsp;</shiro:hasPermission>"
        		 + "<shiro:hasPermission name="pubRole:permission"><a class='btn-xs btn-success' href='"+permissionUrl+"' title='权限分配'><i class='fa fa-user'></i></a>&nbsp;</shiro:hasPermission>"
        		 + "<shiro:hasPermission name="pubRole:delete"><a class='btn-xs btn-danger' title='删除' onclick='btnDelete(\""+ cellvalue +"\")'><i class='fa fa-trash'></i></a>&nbsp;</shiro:hasPermission>";
        }
        
        function btnDelete(value){
        	j.delete({
        		url: '<c:url value="/pubRole/delete"/>',
        		data: {id:value}
        	});
        }
    </script>

</body>

</html>
