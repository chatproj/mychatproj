<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<!-- css file -->
<%@ include file="./common/title.jsp"%>
<script type="text/javascript">
	window.addEventListener("DOMContentLoaded", function(){
		<%
			if(request.getParameter("message") != null && request.getParameter("message").equals("FAILURE_fail")){
		%>
				setErrorMessage("member_pwd_error", "비밀번호를 확인해주세요.");
		<%
			}
		%>
		
		<%
			if(request.getParameter("message") != null && request.getParameter("message").equals("FAILURE_notfound")){
		%>
				setErrorMessage("member_pwd_error", "존재하지 않는 사용자입니다.");
		<%		
			}
		%>
		
		function setErrorMessage(id, message){
			document.getElementById(id).innerText = message;
		}
	});
</script>
</head>
<body>

	<!-- Header -->
	<%@ include file="./common/header.jsp"%>

	<div id="main_container">
		<div class="form_container">
			<div class="form">
				<form method="POST" action="/signin">
					<div class="input-box">
						<div class="inputlabel">아이디</div>
						<input type="text" name="member_id" id="member_id">
						<div id="member_id_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">비밀번호</div>
						<input type="password" name="member_pwd" id="member_pwd">
						<div id="member_pwd_error" class="error"></div>
					</div>

					<input type="submit" id="submit_btn" value="Log in"
						class="submit_btn">
						
					<div class="nouser">아이디가 없으신가요? <a href="/signup">회원가입</a></div>
					<div class="finduser">
						<a href="/findId">아이디 찾기</a>
						<a href="/findpw">패스워드 찾기</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Script -->
<!-- 	<script src="/js/signup.js" type="text/javascript" charset="UTF-8"></script> -->
</body>
</html>