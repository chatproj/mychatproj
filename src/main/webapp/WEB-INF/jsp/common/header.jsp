<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@page import="com.example.chatproj.chatproj.domain.User"%> --%>
<head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<section id="header_container">
	<div id="header_form_container">
		<div class="header_form">
			<%
				String session_id = (String) session.getAttribute("session_id");
			%>
			
			<%
				if(session_id != null){
			%>
			<div class="section">
				<div class="sessionStatus"><%=session_id %></div>
				
				<div id="header_menu_btn" class="header_menu_btn">
					<img class="menuiconimg" src="/memberimg/menu_icon.png">
					<div id="header_slideToggle" class="header_slideToggle">
						<div id="header_menu_list" class="header_menu_list">
							<form method="POST" action="signout">
								<input type="submit" id="signout" value="로그아웃" class="signout">
							</form>
						</div>
						<div id="header_menu_list" class="header_menu_list">						
							<form method="POST" action="/deletemember">
								<input type="submit" id="deleteuser" value="회원탈퇴" class="deleteuser">
							</form>
						</div>
						<div id="header_menu_list" class="header_menu_list">						
							<input type="button" onclick="location.href='/modifymember'" id="modifyuser" value="회원정보수정" class="modifyuser">
						</div>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
</section>

	<!-- Script -->
	
<script type="text/javascript">
	$(document).ready(function(){
		$("#header_menu_btn").click(function(){
			$("#header_slideToggle").animate({width: 'toggle'}, 400);
		})
	})
</script>

</head>