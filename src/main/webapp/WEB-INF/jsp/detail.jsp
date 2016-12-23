<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>秒杀列表页面</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- 引入 Bootstrap -->
      <link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"rel="stylesheet"> 
   </head>
   <body>
        <!-- 页面显示部分 -->
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                	<h1>${seckill.name}</h1>
                </div>
                <div class="panel-body">
                    <h2 class="text-danger">
                    	<!-- 显示time图标 -->
                    	<span class="glyphicon glyphicon-box"></span>
                    	<!-- 展示倒计时 -->
                    	<span class="glyphicon" id="seckil-box"></span>
                    </h2>
                </div>
            </div>
        </div>
        
        
        <!-- 登录弹出层，输出电话 -->
     	<div id="KillPhoneModal" class="modal fade">
     		<div class="modal-dialog">
     			<div class="model-content">
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
   <script src="https://code.jquery.com/jquery.js"></script>
   <script src="js/bootstrap.min.js"></script>
   <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
   <script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
   <!-- 开始编写交互逻辑 -->
   <script src="/resources/js/seckill.js" type="text/javascript"></script>
   <script type="text/javascript">
   		// JQuery的初始化函数
   		$(function(){
   			// 使用json形式把参数传递给js
   			seckill.detail.init({
   				seckillId : ${seckill.seckillId},
   				startTime : ${seckill.startTime.time},//毫秒
   				endTime : ${seckill.endTime.time}
   			});
   		});
   </script>
</html>