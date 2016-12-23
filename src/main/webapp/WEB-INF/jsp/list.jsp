<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 引入jstl -->
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
   <head>
      <title>秒杀列表页面</title>
      <%@include file="common/head.jsp" %> 
   </head>
   <body>
        <!-- 页面显示部分 -->
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2>秒杀列表</h2>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>名称</th>
                                <th>库存</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>创建时间</th>
                                <th>详情页面</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach var="sk" items="${list}">
	                            <tr>
	                            	<td>${sk.name}</td>
	                            	<td>${sk.number}</td>
	                            	<td>
	                            		<fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            	</td>
	                            	<td>
	                            		<fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            	</td>
	                            	<td>
	                            		<fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            	</td>
	                            	<td>
										<a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">link</a>
									</td>
	                            </tr>
	                         </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
     
      
      
   <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
   <script	src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
   </body>
</html>