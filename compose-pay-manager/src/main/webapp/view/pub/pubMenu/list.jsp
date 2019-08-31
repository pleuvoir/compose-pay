<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>菜单管理</title>
	
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
						<label class="control-label">菜单id</label>
						<input type="text" class="form-control" placeholder="菜单id" name="id"/>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">菜单名称</label>
						<input type="text" class="form-control" placeholder="菜单名称" name="title"/>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">父级菜单id</label>
						<input type="text" class="form-control" placeholder="父级菜单id" name="parentId"/>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">菜单等级</label>
						<select  class="form-control" name="node">
							<option value="">-</option>
							<option value="1">一级菜单</option>
							<option value="2">二级菜单</option>
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
                        <h5>菜单管理</h5>
                        <div class="ibox-tools">
	                        <shiro:hasPermission name="pubMenu:add">
	                            <a class="btn btn-primary btn-xs" href="<c:url value="/pubMenu/create"/>" title="新增">
	                                <i class="fa fa-plus"></i> 新增
	                            </a>
	                        </shiro:hasPermission>
	                        <shiro:hasPermission name="pubMenu:sort">
	                            <a class="btn btn-success btn-xs" href="<c:url value="/pubMenu/sort"/>" title="排序">
	                                <i class="fa fa-sort"></i> 排序
	                            </a>
	                        </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<t:alert message="${message}"/>
                    
                        <div class="jqGrid_wrapper">
                            <table id="table-menu"></table>
                            <div id="pager-menu"></div>
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
				selector: '#table-menu',
				button: '#table-query',
				form: '#query-form',
				pager: "#pager-menu",
				formResetButton: '#table-remove',
				url: '<c:url value="/pubMenu/query"/>'
			});

			j.useParamsCache();

        	j.init({
                colNames: ['菜单id', '菜单名称', '父级菜单id', '父级菜单名称', '排序', '图标', '访问路径', '是否显示', '操作'],
                colModel: [
                    {name: 'id', width: 40, sortable:true},
                    {name: 'title', width: 40, sortable:false},
                    {name: 'parentId', width: 40, sortable:false},
                    {name: 'parentTitle', width: 40, sortable:false},
                    {name: 'sort', width: 10, sortable:true},
                    {name: 'icon', width: 20, sortable:false, formatter:fmtIcon},
                    {name: 'path', width: 80, sortable:false},
                    {name: 'isShow', width: 20, sortable:false, formatter:fmtIsShow},
                    {name: 'id', width:150, fixed:true, sortable:false, resize:false, formatter:dataButton}
                ],

            });
        	
        	j.bindQuery();
			j.bindReset();
        });
        
        //cellvalue  当前字段的值
        //rowdata 当前行对象
        function dataButton(cellvalue, options, rowdata){
        	var editUrl = '<c:url value="/pubMenu/edit"/>';
        	editUrl = joinUrlParam(editUrl, {id: cellvalue});
        	var rtnStr = "<shiro:hasPermission name="pubMenu:edit"><a class='btn-xs btn-info' href='"+editUrl+"' title='编辑'><i class='fa fa-edit'></i></a>&nbsp;</shiro:hasPermission>";
        	
        	var permissionUrl = '<c:url value="/pubMenu/permission"/>';
        	permissionUrl = joinUrlParam(permissionUrl, {id: cellvalue});
        	if(rowdata.hasChild == 'N'){
        		rtnStr = rtnStr + "<shiro:hasPermission name="pubMenu:permission"><a class='btn-xs btn-success' href='"+permissionUrl+"' title='绑定权限'><i class='fa fa-gg'></i></a>&nbsp;</shiro:hasPermission>";
        	}

			rtnStr = rtnStr + "<shiro:hasPermission name="pubMenu:delete"><a class='btn-xs btn-danger' title='删除' onclick='btnDelete(\""+ cellvalue +"\")'><i class='fa fa-trash'></i></a>&nbsp;</shiro:hasPermission>";
        	return rtnStr;
        }
        
        function fmtIcon(cellvalue, options, rowdata){
        	return "<i class='"+cellvalue+"'></i>";
        }
        
        function fmtIsShow(cellvalue, options, rowdata){
        	switch (cellvalue) {
			case '1':
				return '<span class="label label-primary">显示</span>';
			case '0':
				return '<span class="label label-warning">不显示</span>';
			default:
				return emptyString(cellvalue);
			}
        }
        
        function btnDelete(value){
        	j.delete({
        		url: '<c:url value="/pubMenu/delete"/>',
        		data: {id:value}
        	});
        }
        
    </script>

</body>

</html>
