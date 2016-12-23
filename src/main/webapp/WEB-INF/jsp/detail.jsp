<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 引入jstl -->
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>秒杀详情页面</title>
      <%@ include file="common/head.jsp" %>  
   </head>
   <body>
        <!-- 页面显示部分 -->
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                	<h1>${seckill.name}</h1>
                </div>
                <div class="panel-body">
                    <h2 class="text-danger text-center">
                    	<!-- 显示time图标 -->
                    	<span class="glyphicon glyphicon-time"></span>
                    	<!-- 展示倒计时 -->
                    	<span class="glyphicon" id="seckil-box"></span>
                    </h2>
                </div>
            </div>
        </div>
        
        
        <!-- 登录弹出层，输出电话 -->
     	<div id="KillPhoneModal" class="modal fade">
     		<div class="modal-dialog">
     			<div class="modal-content">
                    <div class="modal-header">
     			        <h3 class="modal-title text-center">
     			            <span class="glyphicon glyphicon-phone"></span> 
     			        </h3>
     			    </div>
     			    <div class="modal-body">
     			        <div class="row">
     			            <div class="col-xs-8 col-xs-offset-2">
     			                <input type="text" name="KillPhone" id="KillPhoneKey"
     			                    placeholder="填写手机号码" class="form-control">
     			            </div>
     			        </div>
     			    </div>
     			    <div class="modal-footer">
     			        <!--验证信息-->
     			        <span id="KillPhoneMessage" class="glyphicon"></span>
     			        <button type="button" id="KillPhoneBtn" class="btn btn-success">
     			            <span class="glyphicon glyphicon-phone"></span>
     			            Submit
     			        </button>
     			    </div>
     			</div>
     		</div>
     	</div>    
   </body>
   <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
   <script	src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
   <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
   <script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
   <!-- 开始编写交互逻辑 -->
   <script src="/resources/js/seckill.js" type="text/javascript"></script>
   <script type="text/javascript">
   		// JQuery的初始化函数
   		$(function(){
   			// 使用json形式把参数传递给js
   			seckill.detail.init({
   	   			seckillId : ${seckill.seckillId},
   	   			startTime : ${seckill.startTime.time},
   	   			endTime : ${seckill.endTime.time}
   	   	   	});
   		});
   </script>
</html>