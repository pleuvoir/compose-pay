<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>登录日志查询</title>
	
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
						<input type="text" class="form-control" placeholder="用户名" name="username">
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
                        <h5>登录日志查询</h5>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                        <div class="jqGrid_wrapper">
                            <table id="table-loginLog"></table>
                            <div id="pager-loginLog"></div>
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
                selector: '#table-loginLog',
                button: '#table-query',
                form: '#query-form',
                pager: "#pager-loginLog",
                formResetButton: '#table-remove',
                url: '<c:url value="/pubLoginLog/query"/>'
            });

            j.useParamsCache();

        	j.init({
                colNames: ['用户名', 'Agent属性值', 'IP地址', '登录时间', '登录状态', '备注'],
                colModel: [
                    {name: 'username', width: 30, sortable:true},
                    {name: 'agent', width: 110, sortable:false},
                    {name: 'ip', width: 40, sortable:false},
                    {name: 'loginDate', width: 40, sortable:false},
                    {name: 'status', width: 30, sortable:false, formatter:fmtStatus},
                    {name: 'remark', width: 50, sortable:false},
                ],

            });
        	
        	j.bindQuery();
            j.bindReset();
        	
        });
        
        //cellvalue  当前字段的值
        //rowdata 当前行对象
      
        
        function fmtStatus(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '0':
				return '成功';
			case '1':
				return '失败';
			default:
				return emptyString(cellvalue);
			}
        }
        
    
    </script>

</body>

</html>
