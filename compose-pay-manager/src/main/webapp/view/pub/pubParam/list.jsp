<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>参数管理</title>
	
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
						<label class="control-label">编号</label>
						<input type="text" class="form-control" placeholder="编号" name="code">
					</div>
				</div>
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
                        <h5>参数管理</h5>
                        <div class="ibox-tools">
	                        <shiro:hasPermission name="pubParam:add">
	                            <a class="btn btn-primary btn-xs" href="<c:url value="/pubParam/create"/>" title="新增">
	                                <i class="fa fa-plus"></i> 新增
	                            </a>
	                        </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                    
                        <div class="jqGrid_wrapper">
                            <table id="table-param"></table>
                            <div id="pager-param"></div>
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

    <!-- Page-Level Scripts -->
    <script>
   	var j;
        $(document).ready(function () {
        	j = new JqGridControl({
                selector: '#table-param',
                button: '#table-query',
                form: '#query-form',
                pager: "#pager-param",
                formResetButton: '#table-remove',
                url: '<c:url value="/pubParam/query"/>'
			});

            j.useParamsCache();

        	j.init({
                colNames: ['编码', '分组编码', '名称', '小数值', '整数值', '字符值', '布尔值', '类型', '操作'],
                colModel: [
                    {name: 'code', width: 20, sortable:true, key: true},
                    {name: 'groupCode', width: 20, sortable:false},
                    {name: 'name', width: 50, sortable:false},
                    {name: 'decimalVal', width: 20, sortable:false, formatter:fmtNumber},
                    {name: 'intVal', width: 20, sortable:false},
                    {name: 'strVal', width: 80, sortable:false},
                    {name: 'booleanVal', width: 20, sortable:false,formatter:fmtBoolean},
                    {name: 'type', width: 20, sortable:false, formatter:fmtType},
                    {name: 'code', width:150, fixed:true, sortable:false, resize:false, formatter:dataButton}
                ],

            });
        	
        	j.bindQuery();
            j.bindReset();
        	
        });
        
        //cellvalue  当前字段的值
        //rowdata 当前行对象
        function dataButton(cellvalue, options, rowdata){
        	var editUrl = '<c:url value="/pubParam/edit"/>';
        	var showUrl = '<c:url value="/pubParam/show"/>';
        	editUrl = joinUrlParam(editUrl, {code: cellvalue});
        	showUrl = joinUrlParam(showUrl, {code: cellvalue});
        	
        	var rtnStr = "<shiro:hasPermission name="pubParam:list"><a class='btn-xs btn-success' href='"+showUrl+"' title='查看'><i class='fa fa-book'></i></a>&nbsp;</shiro:hasPermission>";
        	if(rowdata.modifyFlag == '1'){
        		rtnStr = rtnStr + "<shiro:hasPermission name="pubParam:edit"><a class='btn-xs btn-info' href='"+editUrl+"' title='编辑'><i class='fa fa-edit'></i></a>&nbsp;</shiro:hasPermission>";
        	}
        	rtnStr = rtnStr + "<shiro:hasPermission name="pubParam:delete"><a class='btn-xs btn-danger' title='删除' onclick='btnDelete(\""+ cellvalue +"\")'><i class='fa fa-trash'></i></a>&nbsp;</shiro:hasPermission>";
        	return rtnStr;
        		 
        }
        
        function fmtType(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '1':
				return '小数';
			case '2':
				return '整数';
			case '3':
				return '字符';
			case '4':
				return '布尔';
			default:
				return emptyString(cellvalue);
			}
        }
        
        function fmtBoolean(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '0':
				return 'false';
			case '1':
				return 'true';
			default:
				return emptyString(cellvalue);
			}
        }
        
        function btnDelete(value){
        	j.delete({
        		url: '<c:url value="/pubParam/delete"/>',
        		data: {code:value}
        	});
        }
    </script>

</body>

</html>
