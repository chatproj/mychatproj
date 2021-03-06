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
<script type="text/javascript">
	window.addEventListener("DOMContentLoaded", function(){
		<%
			if(request.getParameter("message") != null && request.getParameter("message").equals("FAILURE_my")){
		%>
				alert("같은 ID 입니다.");
		<%
			}else if(request.getParameter("message") != null && request.getParameter("message").equals("FAILURE_noid")){
		%>
				alert("존재하지 않는 ID 입니다.");
		<%
			}
		%>
	});
</script>
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
				<form method="POST" action="/Joinchatroom">
 				<% for(Integer key : chatList.keySet()){ %>
					<div class="chatList">
	<%-- 					<input type="text" id="list" name="chatroom_no" value="<%=key %>"> --%>
						<input type="submit" id="list" name="list" value="<%=key %>.<%=chatList.get(key) %>" class="submit_btn">
					</div>	
				<% } %>
				</form>
				
				<div class="borderline">
					<div class="chatList_btn">
					<button id="create_chatroom" class="submit_btn" onclick="openinvite()">방만들기</button>
					</div>
				</div>
				
				<dialog id="invite" class="invite">
					<form method="POST" action="/invitemember">
						<div class="invite-box">
							<div class="inputlabel">방제목</div>
							<input type="text" name="chatroom_name" id="chatroom_name" value="">
							<div id="chatroom_name_error" class="error"></div>
						</div>
						
						<div class="invite-box">
							<div class="inputlabel">아이디</div>
							<input type="text" name="member_id" id="member_id">
							<div id="member_id_error" class="error"></div>
						</div>
						
						<input type="submit" id="submit_btn" value="방만들기"
							class="submit_btn">
	
					</form>
				</dialog>
				
			</div>
		</div>
	</div>

	<!-- Script -->
	<script type="text/javascript">
		var invite = document.getElementById('invite');
		function openinvite() {
			if(typeof invite.showModal === "function") {
				invite.showModal();
			}else{
				alert("The <dialog> API is not supported by this browser");
			}
		}
	</script>
	<script src="/js/chatList.js" type="text/javascript" charset="UTF-8"></script>
</body>
</html>