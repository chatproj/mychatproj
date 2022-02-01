<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.springframework.ui.Model"%>
<!doctype html>
<html>
<head>
<!-- css file -->
<%@ include file="./common/title.jsp"%>
</head>
<body>

	<!-- Header -->
	<%@ include file="./common/header.jsp"%>
	
	<!-- form -->
	<div id="main_container">
		<div class="form_container">
			<div class="form">
 			<%
				request.setCharacterEncoding("UTF-8");				
				HashMap<Integer, String> chatList = (HashMap<Integer, String>)request.getAttribute("chatList");
			%> 
				<form method="POST" action="/chatList">
 				<% for(Integer key : chatList.keySet()){ %>
					<div class="chatList">
						<input type="hidden" id="list" name="list" value="<%=key %>">
						<input type="submit" id="list" name="list" value="<%=chatList.get(key) %>" class="submit_btn">
					</div>	
				<% } %>
				</form>
				
				<div class="borderline">
					<div class="chatList_btn">
					<input type="submit" id="create_room" value="방만들기" class="submit_btn" onclick="location.href='/inviteuser'">
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Script -->
	<script src="/js/account_form.js" type="text/javascript" charset="UTF-8"></script>
</body>
</html>