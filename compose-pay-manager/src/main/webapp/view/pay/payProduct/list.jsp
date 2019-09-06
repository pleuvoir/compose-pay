<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>支付产品管理</title>
	
	<jsp:include page="../../_import.jsp"/>
	<link href="<c:url value="/static/css/plugins/jqgrid/ui.jqgrid.css?0820"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/plugins/sweetalert/sweetalert.css"/>" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<form id="query-form">
    		
    			<!-- 查询条件区域 -->
    			 <div class="col-md-2">
					<div class="form-group">
						<label class="control-label">支付种类代码</label>
						<input type="text" class="form-control"  placeholder="支付种类代码"  name="payTypeCode">
					</div>
				</div>
    			 <div class="col-md-2">
					<div class="form-group">
						<label class="control-label">支付方式代码</label>
						<input type="text" class="form-control"  placeholder="支付方式代码"  name="payWayCode">
					</div>
				</div>
    			 <div class="col-md-2">
					<div class="form-group">
						<label class="control-label">支付产品名称</label>
						<input type="text" class="form-control"  placeholder="支付产品名称"  name="name">
					</div>
				</div>
    			 <div class="col-md-2">
					<div class="form-group">
						<label class="control-label">状态</label>
							<select class="form-control" name="status">
								<option value="">-</option>
								<option value="1">有效</option>
								<option value="2">无效</option>
							</select>
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
                        <h5>支付产品管理</h5>
                        <div class="ibox-tools">
	                        <shiro:hasPermission name="payType:add">
	                            <a class="btn btn-primary btn-xs" href="<c:url value="/payProduct/create"/>" title="新增">
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
                url: '<c:url value="/payProduct/query"/>'
			});

            j.useParamsCache();

        	j.init({
        		colNames:  [
        					
        						'支付种类代码',
        					
        						'支付方式代码',
        					
        						'支付产品名称',
        					
        						'状态',
        					
        						'备注',
        					  '操作'],
                colModel: [
                    {name: 'payTypeCode', width: 50, sortable:false},
                    {name: 'payWayCode', width: 50, sortable:false},
                    {name: 'name', width: 50, sortable:false},
                    {name: 'status', width: 50, sortable:false,formatter:formatStatus},
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
        	var editUrl = '<c:url value="/payProduct/edit"/>';
        	editUrl = joinUrlParam(editUrl, {id: cellvalue});
        	
        	var rtnStr = "<shiro:hasPermission name="payProduct:edit"><a class='btn-xs btn-info' href='"+editUrl+"' title='编辑'><i class='fa fa-edit'></i></a>&nbsp;</shiro:hasPermission>";
        	rtnStr = rtnStr + "<shiro:hasPermission name="payProduct:delete"><a class='btn-xs btn-danger' title='删除' onclick='btnDelete(\""+ cellvalue +"\")'><i class='fa fa-trash'></i></a>&nbsp;</shiro:hasPermission>";
        	return rtnStr;
        }
        
        
        function btnDelete(value){
        	j.delete({
        		url: '<c:url value="/payProduct/delete"/>',
        		data: {id:value}
        	});
        }
        
        function formatStatus(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '1':
				return '有效';
			case '2':
				return '无效';
			default:
				return emptyString(cellvalue);
			}
        }
    </script>

</body>

</html>
