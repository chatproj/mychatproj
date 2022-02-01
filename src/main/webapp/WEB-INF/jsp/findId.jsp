<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<!-- css file -->
<%@ include file="./common/title.jsp"%>
</head>
<body>

	<!-- Header -->
	<%@ include file="./common/header.jsp"%>

	<div id="main_container">
		<div class="form_container">
			<div class="form">
				<form method="POST" action="/findId">
					<div class="input-box">
						<div class="inputlabel">이름</div>
						<input type="text" name="member_name" id="uname">
						<div id="uname_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">이메일</div>
						<input type="email" name="member_email" id="email">
						<div id="email_error" class="error"></div>
					</div>

					<input type="submit" id="submit_btn" value="아이디 찾기"
						class="submit_btn">

				</form>
			</div>
		</div>
	</div>

	<!-- Script -->
	<script src="/js/account_form.js" type="text/javascript" charset="UTF-8"></script>
</body>
</html>