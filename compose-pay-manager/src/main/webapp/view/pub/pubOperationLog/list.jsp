<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>操作日志</title>
	
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
						<label class="control-label">状态</label>
						<select class="form-control" name="status">
							<option value="">-</option>
							<option value="1">成功</option>
							<option value="2">失败</option>
						</select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">用户名</label>
						<input type="text" class="form-control" placeholder="用户名" name="username"/>
					</div>
				</div>
			
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">创建起始时间</label>
						<input type="text" class="form-control datepicker" placeholder="起始时间" name="startTime" readonly="readonly"/>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">创建结束时间</label>
						<input type="text" class="form-control datepicker" placeholder="结束时间" name="endTime" readonly="readonly"/>
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
                        <h5>操作日志</h5>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                        <div class="jqGrid_wrapper">
                            <table id="table-operationLog"></table>
                            <div id="pager-operationLog"></div>
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
    <script src="<c:url value="/static/js/plugins/layer/layer.js"/>"></script>
	<script src="<c:url value="/static/js/plugins/laydate/laydate.js"/>"></script>
    <script>
   	var j;
        $(document).ready(function () {
        	j = new JqGridControl({
                selector: '#table-operationLog',
                button: '#table-query',
                form: '#query-form',
                pager: "#pager-operationLog",
                formResetButton: '#table-remove',
                url: '<c:url value="/pubOperationLog/query"/>'
			});

            j.useParamsCache();

        	j.init({
                colNames: ['用户名','IP','菜单','权限', '类名', '方法名','状态','耗时（毫秒）','创建时间','操作'],
                colModel: [
                    {name: 'username', width: 10, sortable:false},
                    {name: 'ip', width: 10, sortable:false},
                    {name: 'menuName', width: 10, sortable:false},
                    {name: 'permissionName', width: 10, sortable:false},
                    {name: 'controller', width: 30, sortable:false},
                    {name: 'method', width: 10, sortable:false},
                    {name: 'status', width: 5, sortable:false, formatter:fmtStatus},
                    {name: 'elapsedTime', width: 10, sortable:false},
                    {name: 'createTime', width: 15, sortable:false},
                    {name: 'id', width:50, fixed:true, sortable:false, key: true, resize:false, formatter:dataButton}
                ],

            });
        	
        	j.bindQuery();
            j.bindResetWithCallback([{
                elementName: 'startTime',
                deviation: -30,
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
			},{
                elementName: 'endTime',
                deviation: 1,
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
			}])

        });
        
        function dataButton(cellvalue, options, rowdata){
        	return "<a class='btn-xs btn-success' href='javascript:;' onclick='showRemark(\""+cellvalue+"\")' title='备注'><i class='fa fa-comment'></i></a>&nbsp;";
        }
        
        function showRemark(id){
        	var url = '<c:url value="/pubOperationLog/remark"/>';
        	$.get(url,{id:id},function(msg){
        		if(msg){
        			var remark = msg.data ? msg.data : '无';
        			
        			layer.open({
        				title:'备注',
        				content: '<pre class="pre-scrollable"><code>'+remark+'</code></pre>',
        				btn:'关闭',
        				area:'886px'
        			});
        		}
        	},'json');
        }
        
        function fmtStatus(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '1':
				return '<span class="label label-primary">成功</span>';
			case '2':
				return '<span class="label label-danger">失败</span>';
			default:
				return emptyString(cellvalue);
			}
        }
    </script>
</body>
</html>
