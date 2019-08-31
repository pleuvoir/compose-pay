<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>菜单排序</title>
    
    <jsp:include page="../../_import.jsp"/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
    	<div class="row">
    		<div class="col-lg-12">
    			<div class="ibox">
                    <div class="ibox-title">
                        <h5>拖动菜单排序</h5>
                    </div>
                    <div class="ibox-content">
	                    <div class="row">
	                    	<div class="col-lg-6">
		                        <div class="dd" id="nestable">
		                            <ol class="dd-list">
		                            	<c:if test="${not empty menuView.children}">
		                            		<%-- 循环一级菜单 --%>
		                            		<c:forEach items="${menuView.children}" var="level1MenuView">
		                            			<%-- 判断有没有菜单 --%>
		                            			<c:if test="${not empty level1MenuView.menu}">
		                            				<c:set var="level1Menu" value="${level1MenuView.menu}"/>
		                            				<%-- 判断有没有子集 --%>
		                            				<c:choose>
		                            					<%-- 没有子集的一级菜单 --%>
		                            					<c:when test="${empty level1MenuView.children}">
		                            						<li class="dd-item" data-id="${level1Menu.id}">
							                                    <div class="dd-handle">
							                                    	<span class="pull-right"> ${level1Menu.id} </span>
		                                                			<i class="${level1Menu.icon}"></i>
		                                                			${level1Menu.title} <c:if test="${level1Menu.isShow=='0'}">(已隐藏)</c:if>
							                                    </div>
							                                </li>
		                            					</c:when>
		                            					<%-- 有子集的一级菜单 --%>
		                            					<c:otherwise>
		                            						<li class="dd-item" data-id="${level1Menu.id}">
		                            							<div class="dd-handle">
		                            								<span class="pull-right"> ${level1Menu.id} </span>
		                                                			<i class="${level1Menu.icon}"></i>
		                                                			${level1Menu.title} <c:if test="${level1Menu.isShow=='0'}">(已隐藏)</c:if>
		                            							</div>
		                            							<ol class="dd-list">
		                            								<%-- 循环二级菜单 --%>
		                            								<c:forEach items="${level1MenuView.children}" var="level2MenuView">
		                            									<%-- 判断有没有菜单 --%>
		                            									<c:if test="${not empty level2MenuView.menu}">
		                            										<c:set var="level2Menu" value="${level2MenuView.menu}"/>
		                            										<%-- 判断有没有子集 --%>
		                            										<c:choose>
		                            											<%-- 没有子集的二级菜单 --%>
		                            											<c:when test="${empty level2MenuView.children}">
		                            												<li class="dd-item" data-id="${level2Menu.id}">
											                                            <div class="dd-handle">
											                                            	<span class="pull-right"> ${level2Menu.id} </span>
											                                            	<i class="${level2Menu.icon}"></i>
		                                                									${level2Menu.title} <c:if test="${level2Menu.isShow=='0'}">(已隐藏)</c:if>
											                                            </div>
											                                        </li>
		                            											</c:when>
		                            											<%-- 有子集的二级菜单 --%>
		                            											<c:otherwise>
		                            												<li class="dd-item" data-id="${level2Menu.id}">
		                            													<div class="dd-handle">
		                            														<span class="pull-right"> ${level2Menu.id} </span>
		                            														<i class="${level2Menu.icon}"></i>
		                                                									${level2Menu.title} <c:if test="${level2Menu.isShow=='0'}">(已隐藏)</c:if>
		                            													</div>
		                                    											<ol class="dd-list">
		                                    												<%-- 循环三级菜单--%>
		                                    												<c:forEach items="${level2MenuView.children}" var="level3MenuView">
		                                    													<%-- 判断有没有菜单 --%>
		                                    													<c:if test="${not empty level3MenuView.menu}">
		                                    														<c:set var="level3Menu" value="${level3MenuView.menu}"/>
		                                    														<li class="dd-item" data-id="${level3Menu.id}">
															                                            <div class="dd-handle">
															                                            	<span class="pull-right"> ${level3Menu.id} </span>
															                                            	<i class="${level3Menu.icon}"></i>
		                                                													${level3Menu.title} <c:if test="${level3Menu.isShow=='0'}">(已隐藏)</c:if>
															                                            </div>
															                                        </li>
		                                    													</c:if>
		                                    												</c:forEach>
		                                    											</ol>
		                            												</li>
		                            											</c:otherwise>
		                            										</c:choose>
		                            									</c:if>
		                            								</c:forEach>
		                            							</ol>
		                            						</li>
		                            					</c:otherwise>
		                            				</c:choose>
		                            			</c:if>
		                            		</c:forEach>
		                            	</c:if>
		                            </ol>
		                        </div>
		                        
		                        
		                        
	                    	</div>
	                    </div> 
	                    <div class="row">
	                    	<div class="col-lg-12">
	                    		<form id="permission-form" class="form-horizontal" action="<c:url value="/pubMenu/sortSave"/>" method="post">
	                    			<textarea id="nestable-output" name="jsonStr" rows="15" class="form-control hide" readonly="readonly"></textarea>
	                        
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
    	</div>
    </div>

    <!-- Nestable List -->
    <script src="<c:url value="/static/js/plugins/nestable/jquery.nestable.js"/>"></script>
<script>
	
	$(function(){
		
		var updateOutput = function (e) {
            var list = e.length ? e : $(e.target),
                output = list.data('output');
            if (window.JSON) {
                output.val(window.JSON.stringify(list.nestable('serialize'))); //, null, 2));
            } else {
                output.val('浏览器不支持');
            }
        };
        
		$('#nestable').nestable({
            group: 1
        }).on('change', updateOutput);
		
		$('.dd').nestable('collapseAll');
		
		updateOutput($('#nestable').data('output', $('#nestable-output')));
		
	});
	
</script>
    
</body>

</html>
