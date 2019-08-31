<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://www.springframework.org/tags" %>

<jsp:include page="_set.jsp"/>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    
    <title><c:out value="${_title}"/></title>
    <link rel="icon" href="<c:url value="/static/img/favicon.ico"/>" type="image/x-icon" />
	<!-- 全局js -->
	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
	<!-- 进度条js -->
	<script src="<c:url value="/static/js/plugins/pace/pace.min.js"/>"></script>
	<script src="<c:url value="/static/js/plugins/sweetalert/sweetalert.2.1.0.min.js"/>"></script>
	
	<link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/font-awesome.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/plugins/sweetalert/sweetalert.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/animate.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/style.css"/>" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <jsp:include page="_menu.jsp"/>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
        	<div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
<!--                         <form role="search" class="navbar-form-custom" method="post" action="search_results.html"> -->
<!--                             <div class="form-group"> -->
<!--                                 <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search"> -->
<!--                             </div> -->
<!--                         </form> -->
                    </div>
            <!--         <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                            </a>
                        </li>
                        <li class="hidden-xs">
                            <a href="index_v1.html" class="J_menuItem" data-index="0"><i class="fa fa-cart-arrow-down"></i> 购买</a>
                        </li>
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul> -->
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="<c:url value="/home/home"/>">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="<c:url value="/logout"/>" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" src="<c:url value="/home/home"/>" data-id="<c:url value="/home/home"/>" style="border:0px;width:100%;height:100%" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right"><c:out value="Compose-pay Management"/></div>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>
    
    
<div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
         <form id="pwd-form">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                 <h4 class="modal-title">修改登录密码</h4>
<!--                     <small class="font-bold">这里可以显示副标题。 -->
             </div>
             <div class="modal-body">
				 <div class="form-horizontal">  
               		<div class="form-group">
               			<label class="col-lg-2 control-label">原密码:</label>
                       	<div class="col-lg-8">
                       		<input id="oldPass" type="password" placeholder="原密码" class="form-control" name="oldPass">
                       	</div>
               		</div>
                 </div>
                 <div class="form-horizontal">  
               		<div class="form-group">
               			<label class="col-lg-2 control-label">新密码:</label>
                       	<div class="col-lg-8">
                       		<input id="newPass" type="password" placeholder="新密码" class="form-control" name="newPass">
                       	</div>
               		</div>
                 </div>
                 <div class="form-horizontal">  
               		<div class="form-group">
               			<label class="col-lg-2 control-label">再次输入新密码:</label>
                       	<div class="col-lg-8">
                       		<input id="repeatPass" type="password" placeholder="再次输入新密码" class="form-control" name="repeatPass">
                       	</div>
               		</div>
                 </div>
             </div>

             <div class="modal-footer">
                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                 <button id="pwdbtn" type="button" class="btn btn-primary">保存</button>
             </div>
         </form>
        </div>
    </div>
</div>
    
<script src="<c:url value="/static/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
<script src="<c:url value="/static/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>
<script src="<c:url value="/static/js/hplus.js?v=4.1.0"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/contabs.js"/>"></script>

<script type="text/javascript">
$(document).ready(function(){
	
    //接收验证码   
    $("#code-btn").click(function(){
    	var count = 60;
        var flag  = true;
        if(!check()){
			return;
		}
   	    
   	if(flag){   
	   	//向后台发送处理数据  
	     $(this).attr("disabled","disabled"); 
	     $("#code-btn").val("倒计时" + count + "秒");  
		    var timer = setInterval(function(){  
			    count--;
			    $("#code-btn").val("倒计时" + count + "秒"); 
			    if (count==0) {
				    $("#code-btn").removeAttr("disabled");
				    clearInterval(timer);
				    $("#code-btn").val("获取短信");
			    }
		    },1000);
		    
		    
   	    	var oldPass=$("#oldPass").val();
   	    	var newPass=$("#newPass").val();
   	    	var repeatPass=$("#repeatPass").val();
   	    	
	 		//请求验证码
	   		$.ajax({
	   			//async:false,
	   			url:"<c:url value='/getPwdSmsCode'></c:url>",
	   			data:{"oldPass":oldPass,"newPass":newPass,"repeatPass":repeatPass},
	   			success:function(data){
	   			if(data!=null){
	   				   if(data.status == "success"){
	                   	$('#warningMsg').html(data.message).removeClass("hide");
	                   }else if(data.status == "error"){
	                		show(data.message);
	                	    $("#code-btn").removeAttr("disabled");
					    	clearInterval(timer);

					   	 	$("#code-btn").val("获取短信");
					   	 
	                   }else{

	                	   $("#yanzhengma").val(data.message);
	                   }
	   				}
	   			},
	   			dataType:"json",
	   			type:"post"
	   		});
    	}
 });  
	
	var pwdUrl = '<c:url value="/main/editPwd"/>';
	
	$("#pwdbtn").click(function(){
		
		$.ajax({
			type:'POST',
			url:pwdUrl,
			data: $("#pwd-form").serialize(),
			success: function(data){
				if('success'==data.status){
					window.location='<c:url value="/auth"/>';
				}else {
					  show(data.message);
				}
			}
		});	
	});
	
	function check(){
		if($("#oldPass").val().length == 0){
			show("原密码不能为空");
			return false;
		}else if($("#newPass").val().length == 0){
			show("新密码不能为空");
			return false;
		}else if($("#repeatPass").val().length == 0){
			show("再次输入新密码不能为空");
			return false;
		}else if($("#newPass").val() != $("#repeatPass").val()){
			show("两次输入密码不一致");
			return false;
		}else {
			return true;
		}
	}
	
	function show(message){
		swal(message, {buttons: false,timer: 2000,});
	}
});
</script>

</body>

</html>