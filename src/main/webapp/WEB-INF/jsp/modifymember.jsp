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
			if(request.getParameter("message") != null && request.getParameter("message").equals("duplicateId")){
		%>
				alert("이미 존재하는 아이디입니다.");
		<%
			}else if(request.getParameter("message") != null && request.getParameter("message").equals("duplicateEmail")){
		%>
				alert("동일한 이메일입니다.");
		<%
			}
		%>
		
		<%
			int    member_no                             =   (Integer) request.getAttribute("member_no");
			String member_id                             =   (String) request.getAttribute("member_id");
			String member_name                           =   (String) request.getAttribute("member_name");
			String member_email                          =   (String) request.getAttribute("member_email");
			String member_phone                          =   (String) request.getAttribute("member_phone");
			String member_profileimg_filename            =   (String) request.getAttribute("member_profileimg_filename");
			String member_profileimg_original_filename   =   (String) request.getAttribute("member_profileimg_original_filename");
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
	<!-- form -->
	<div id="main_container">
		<div class="form_container">
			<div class="form">
				<form method="POST" action="/modify" enctype="multipart/form-data">
					<input type="hidden" name="member_no" value="<%=member_no %>">
					<div class="input-box">
						<div class="inputlabel">아이디</div>
						<input type="text" name="member_id" id="member_id" maxlength="15" value="<%=member_id %>" readonly>
						<div id="member_id_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">이름</div>
						<input type="text" name="member_name" id="member_name" maxlength="10" value="<%=member_name %>">
						<div id="member_name_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">이메일</div>
						<input type="email" name="member_email" id="member_email" maxlength="30" value="<%=member_email %>">
						<div id="member_email_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">비밀번호</div>
						<input type="password" name="member_pwd" id="member_pwd" maxlength="20">
						<div id="member_pwd_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">비밀번호 재확인</div>
						<input type="password" name="member_chkpwd" id="member_chkpwd" maxlength="20">
						<div id="member_chkpwd_error" class="error"></div>
					</div>

					<div class="input-box">
						<div class="inputlabel">휴대전화</div>
						<input type="text" name="member_phone" id="member_phone" maxlength="20" value="<%=member_phone %>">
						<div id="member_phone_error" class="error"></div>
					</div>
					
					<canvas id="imagecanvas" ></canvas>
					
					<div class="input-box">
						<div class="inputlabel">프로필이미지</div>
						<input type="file" name="memberimg" id="userimg" maxlength="40" value="<%=member_profileimg_original_filename %>>">
					</div>

					<input type="submit" id="submit_btn" value="회원정보변경"
						class="submit_btn">

				</form>
			</div>
		</div>
	</div>

	<!-- Script -->
	<script type="text/javascript">
	    const canvas = document.getElementById('imagecanvas');
	    const context = canvas.getContext('2d');
	    
	    var origin_userimg = new Image();
	    origin_userimg.src = "memberimg/<%=member_profileimg_filename %>";
	    origin_userimg.addEventListener("load", () => {
       		context.drawImage(origin_userimg, 0, 0, 300, 150);
	    });
	    
	    const fileChange = document.getElementById('userimg');
	    fileChange.addEventListener('change', function (event) {
	        let reader = new FileReader();
	        reader.onload = function (e){ 
	            userimg = new Image();   
	            userimg.src = e.target.result
	            userimg.onload = function(){
	            	context.drawImage(userimg, 0, 0, 300, 150);
	            	context.restore()
	            }
	        };    
	        reader.readAsDataURL(event.target.files[0])
	    });
    </script>
	<script src="/js/signup.js" type="text/javascript" charset="UTF-8"></script>
</body>
</html>