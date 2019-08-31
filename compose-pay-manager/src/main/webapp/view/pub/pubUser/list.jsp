<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>用户管理</title>
	
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
						<label class="control-label">用户名</label>
						<input type="text" class="form-control" placeholder="用户名" name="username"/>
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
                        <h5>用户管理</h5>
                        <div class="ibox-tools">
                        <shiro:hasPermission name="pubUser:add">
                            <a class="btn btn-primary btn-xs" href="<c:url value="/pubUser/create"/>" title="新增">
                                <i class="fa fa-plus"></i> 新增
                            </a>
                        </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                    
                        <div class="jqGrid_wrapper">
                            <table id="table-user"></table>
                            <div id="pager-user"></div>
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

    <!-- Page-Level Scripts -->
    <script>
   	var j;
        $(document).ready(function () {
        	j = new JqGridControl({
                selector: '#table-user',
                button: '#table-query',
                form: '#query-form',
                pager: "#pager-user",
                formResetButton: '#table-remove',
                url: '<c:url value="/pubUser/query"/>'
            });

            j.useParamsCache();

        	j.init({
                colNames: ['用户名','角色', '状态', '创建人', '创建时间', '备注', '操作'],
                colModel: [
                    {name: 'username', width: 60, sortable:false},
                    {name: 'roleNames', width: 80, sortable:false},
                    {name: 'status', width: 40, sortable:false, formatter:fmtStatus},
                    {name: 'createBy', width: 50, sortable:false},
                    {name: 'createTime', width: 70, sortable:false},
                    {name: 'remark', width: 50, sortable:false},
                    {name: 'id', width:150, fixed:true, sortable:false, key: true, resize:false, formatter:dataButton}
                ],

            });
        	
        	j.bindQuery();
            j.bindReset();
        	
        });
        
        //cellvalue  当前字段的值
        //rowdata 当前行对象
        function dataButton(cellvalue, options, rowdata){
        	var editUrl = '<c:url value="/pubUser/edit"/>';
        	editUrl = joinUrlParam(editUrl, {id: cellvalue});
        	
        	var rtnStr = "";
        	if(rowdata.status=='1' || rowdata.status=='2'){
        		rtnStr = rtnStr + "<shiro:hasPermission name="pubUser:edit"><a class='btn-xs btn-info' href='"+editUrl+"' title='编辑'><i class='fa fa-edit'></i></a>&nbsp;</shiro:hasPermission>";
        	}
        	if(rowdata.status=='0' || rowdata.status=='1' || rowdata.status=='2'){
        		rtnStr = rtnStr + "<shiro:hasPermission name="pubUser:delete"><a class='btn-xs btn-danger' title='删除' onclick='btnDelete(\""+ cellvalue +"\")'><i class='fa fa-trash'></i></a>&nbsp;</shiro:hasPermission>";
        	}
        	return rtnStr;
        }
        
        function fmtStatus(cellvalue, options, rowdata){
        	switch (cellvalue) {
        	case '0':
				return '<span class="label label-disable">未生效</span>';
			case '1':
				return '<span class="label label-primary">已生效</span>';
			case '2':
				return '<span class="label label-warning">锁定</span>';
			case '3':
				return '<span class="label label-danger">删除</span>';
			default:
				return emptyString(cellvalue);
			}
        }
        
        function btnDelete(value){
        	j.delete({
        		url: '<c:url value="/pubUser/delete"/>',
        		data: {id:value}
        	});
        }
    </script>

</body>

</html>
