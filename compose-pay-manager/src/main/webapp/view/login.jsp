<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://yrartnoc.com/taglib" prefix="t" %>

<jsp:include page="_set.jsp"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${_title}</title>

  <link rel="stylesheet" href="<c:url value="/static/css/iconfont.css"/>">
  <link rel="stylesheet" href="<c:url value="/static/css/login.css"/>">
  <link rel="stylesheet" href="<c:url value="/static/css/jigsaw.css"/>">
  <link rel="icon" href="<c:url value="/static/img/favicon.ico"/>" type="image/x-icon" />
  <script>
      if (window.top !== window.self) {
          window.top.location = window.location;
      }
  </script>

</head>
<body>
<!--1230   690-->
<div class="main clear">
  <div class="left">
    <div class="platform clear">
      <div class="icon">
        <%-- <img src="<t:concat fileId="${platform.mngLogo}"/>" alt=""> --%>
      </div>
      <div class="name">
        <p class="chinese">Compose-pay Management</p>
        <p class="english">Compose-pay Management</p>
      </div>
    </div>

    <div class="company">
      版权所有：<c:out value="Compose-pay Management"/>
    </div>
  </div>

  <div class="right">
    <div class="form">
      <div class="tittle">
      	欢迎登录
      	<div class='warning <c:if test="${empty message}">hide</c:if>' id='warningMsg'>${message.message}</div>
      </div>
      <form method="post" id="loginForm" action="<c:url value="/login"/>" data-auth-url="<c:url value="/auth"/>">
	      <div class="username-box">
	        <span class="iconfont icon-icon_account"></span><input type="text" id="username" class="username" name="username" placeholder="请输入用户名"  autocomplete="off">
	      </div>
	      <div class="password-box" id="password-box">
	        <span class="iconfont icon-mima"></span><input type="password" id="password" class="password" name="password" placeholder="请输入密码">
	      </div>

	       <div class="password-box">
	         <span class="iconfont icon-yanzhengma"></span><input type="text"  id="kaptcha" class="password" name="kaptcha"  placeholder="请输入验证码" autocomplete="off" maxlength="4"/>
	      	<dev class="row">
	      	<img alt="验证码" id="img-code" src="<c:url value="/code"/>" title="刷新验证码" data-placement="right" style="height:30px;width:30%;margin-bottom:-10px;margin-left: 183px;margin-top: -43px; " >
	      	</dev>
	      </div> 
	      
	      <div class="login">
	        <input id="btnForm" type="submit" value="登录">
	      </div>
      </form>
    </div>
  </div>
</div>

	<!-- 全局js -->
    <script src="<c:url value="/static/js/jquery.min.js?v=2.1.4"/>"></script>
    <script src="<c:url value="/static/js/bootstrap.min.js?v=3.3.6"/>"></script>
    <script src="<c:url value="/static/js/jigsaw.js"/>"></script>
    
    <script type="text/javascript">
    $(document).ready(function(){
        $("#img-code").bind("click", changeImg);
    	
    	$('#loginForm').submit(function(e){
     		e.preventDefault();
     		var kaptcha = $("#kaptcha").val();
     		if(kaptcha == ""){
     			$('#warningMsg').html('验证码不能为空！！').removeClass("hide");
     			return;
    		}
     		var username = $("#username").val();
     		if(username == ""){
     			$('#warningMsg').html('用户名不能为空！！').removeClass("hide");
     			return;
    		}
     		var password = $("#password").val();
     		if(password == ""){
     			$('#warningMsg').html('密码不能为空！！').removeClass("hide");
     			return;
    		}
     		this.submit();
     	});

   	  //  $("body .main .left").css('background-image','url('+ backpath +')');

    })
  

    function changeImg(){
	var imgSrc = $("#img-code");
	var src = imgSrc.attr("src");
	imgSrc.attr("src",chgUrl(src));
	}

	function chgUrl(url) { 
    var timestamp = (new Date()).valueOf();
    if ((url.indexOf("?") >= 0)) { 
    	url  = url.substring(0, url.indexOf("?"));
    } 
    return  url + "?timestamp=" + timestamp;
	}

    
     //接收验证码   
    $("#code-btn").click(function(){
    	var count = 60;
        var flag  = true;
    	var username = $("#username").val();
   	    if(username == ""){
   			$('#warningMsg').html('请输入用户名！！').removeClass("hide");
   			return;
   		}
    	var password = $("#password").val();
   	    if(password == ""){
   			$('#warningMsg').html('请输入密码！！').removeClass("hide");
   			return;
   		}
    	var kaptcha = $("#kaptcha").val();
   	    if(kaptcha == ""){
   			$('#warningMsg').html('请输入验证码！！').removeClass("hide");
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
   	    
	 		//请求验证码
	   		$.ajax({
	   			//async:false,
	   			url:"<c:url value='/getSmsCode'></c:url>",
	   			data:{"realName":username,"password":password,"kaptcha":kaptcha},
	   			success:function(data){
	   			if(data!=null){
	   				   if(data.status == "success"){
	                   	$('#warningMsg').html(data.message).removeClass("hide");
	                   }else if(data.status == "error"){
	                		$('#warningMsg').html(data.message).removeClass("hide");
	                	    $("#code-btn").removeAttr("disabled");
					    	clearInterval(timer);

					   	 	$("#code-btn").val("获取短信");
					   	 	changeImg();
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
   	 
    </script>

</body>
</html>