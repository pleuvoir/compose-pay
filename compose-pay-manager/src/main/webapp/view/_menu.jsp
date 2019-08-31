<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<ul class="nav" id="side-menu">
    <li class="nav-header">
        <div class="dropdown profile-element">
            <span><img alt="image" class="img-circle" src="<c:url value="/static/img/p2223876126.jpg"/>" style="width: 70px;height:70px;"/></span>
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <span class="clear">
               <span class="block m-t-xs"><strong class="font-bold">${username }</strong></span>
              <%--   <span class="text-muted text-xs block">${roleName }<b class="caret"></b></span> --%>
                <span class="text-muted text-xs block">${roleName }</span>
                </span>
            </a>
          <%--   <ul class="dropdown-menu animated fadeInRight m-t-xs">
                <li><a href="<c:url value="/logout"/>">安全退出</a>
                </li>
            </ul> --%>
        </div>
        <div class="logo-element">${username }
        </div>
    </li>
    
   	<c:if test="${not empty menuView && not empty menuView.children}">
   		<%-- 循环一级菜单 --%>
   		<c:forEach items="${menuView.children}" var="level1MenuView">
   			<%-- 判断有没有菜单 --%>
   			<c:if test="${not empty level1MenuView.menu}">
   				<c:set var="level1Menu" value="${level1MenuView.menu}"/>
   				<%-- 判断有没有子集 --%>
   				<c:choose>
   					<%-- 没有子集的一级菜单 --%>
   					<c:when test="${empty level1MenuView.children}">
   						<li><a class="J_menuItem" href="<c:url value="${level1Menu.path}"/>"><i class="${level1Menu.icon}"></i> <span class="nav-label">${level1Menu.title}</span></a></li>
   					</c:when>
   					<%-- 有子集的一级菜单 --%>
   					<c:otherwise>
   						<li>
	   						<a href="<c:url value="${level1Menu.path}"/>"><i class="${level1Menu.icon}"></i> <span class="nav-label">${level1Menu.title}</span><span class="fa arrow"></span></a>
	   						<ul class="nav nav-second-level">
	   							<%-- 循环二级菜单 --%>
	   							<c:forEach items="${level1MenuView.children}" var="level2MenuView">
	   								<%-- 判断有没有菜单 --%>
	   								<c:if test="${not empty level2MenuView.menu}">
	   									<c:set var="level2Menu" value="${level2MenuView.menu}"/>
	   									<%-- 判断有没有子集 --%>
	   									<c:choose>
	   										<%-- 没有子集的二级菜单 --%>
	   										<c:when test="${empty level2MenuView.children}">
	   											<li><a class="J_menuItem" href="<c:url value="${level2Menu.path}"/>">${level2Menu.title}</a></li>
	   										</c:when>
	   										<%-- 有子集的二级菜单 --%>
	   										<c:otherwise>
	   											<li>
	   												<a href="<c:url value="${level2Menu.path}"/>">${level2Menu.title} <span class="fa arrow"></span></a>
	   												<ul class="nav nav-third-level">
	   													<%-- 循环三级菜单--%>
	   													<c:forEach items="${level2MenuView.children}" var="level3MenuView">
	   														<%-- 判断有没有菜单 --%>
	   														<c:if test="${not empty level3MenuView.menu}">
	   															<c:set var="level3Menu" value="${level3MenuView.menu}"/>
	   															<li><a class="J_menuItem" href="<c:url value="${level3Menu.path}"/>">${level3Menu.title}</a></li>
	   														</c:if>
	   													</c:forEach>
	   												</ul>
	   											</li>
	   										</c:otherwise>
	   									</c:choose>
	   								</c:if>
	   							</c:forEach>
	   						</ul>
   						</li>
   					</c:otherwise>
   				</c:choose>
   			</c:if>
   		</c:forEach>
   	</c:if>
    
</ul>