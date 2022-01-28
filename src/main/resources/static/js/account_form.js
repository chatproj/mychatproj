window.addEventListener("DOMContentLoaded", function(){

	var uid = document.getElementById("uid");
	var uname = document.getElementById("uname");
	var email = document.getElementById("email");
	var upw = document.getElementById("upw");
	var upw_check = document.getElementById("upw_check");
	var phone_num = document.getElementById("phone_num");
	
	var submit_btn = document.getElementById("submit_btn");
	
	uid.addEventListener("keyup", function(){
		if(uid.value == "" || uid.value.length == 0){
			setErrorMessage("uid_error", "필수 정보입니다.");		
		}else{
			var pattern = /[^a-zA-Z0-9]/;
			if(pattern.test(uid.value)){
				setErrorMessage("uid_error", "영어 또는 숫자를 입력해주세요.");
			}else{
				removeErrorMessage("uid_error");
			}
		}
	});
	
	uname.addEventListener("keyup", function(){
		if(uname.value == "" || uname.length == 0){
			setErrorMessage("uname_error", "필수 정보입니다.");
		}else{
			removeErrorMessage("uname_error");
		}
	})

	
	email.addEventListener("keyup", function(){
		var pattern = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+$/;
		if(email.value == "" || email.value.length == 0){
			setErrorMessage("email_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(email.value)){
				setErrorMessage("email_error", "이메일 형식으로 입력해주세요.")
			}else{
				removeErrorMessage("email_error");
			}
		}
	});
	
	upw.addEventListener("keyup", function(){
		var level = passwordLevel(upw.value);
		if(level == 0){
			setErrorMessage("upw_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				setErrorMessage("upw_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("upw_error");
			}
		}
	});
	
	upw_check.addEventListener("keyup", function(){
		var level = passwordLevel(upw_check.value);
		if(level == 0){
			setErrorMessage("upw_check_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				setErrorMessage("upw_check_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("upw_check_error");
			}
		}
	});
	
	upw_check.addEventListener("keyup", function(){
		if(upw.value != upw_check.value){
			setErrorMessage("upw_check_error", "비밀번호가 일치하지 않습니다.");
		}else{
			removeErrorMessage("upw_check_error");
		}
	});
	
	phone_num.addEventListener("keyup", function(){
		var pattern = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
		if(phone_num == "" || phone_num.value.length == 0){
			setErrorMessage("phone_num_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(phone_num.value)){
				setErrorMessage("phone_num_error", "휴대전화 형식으로 입력해주세요.");
			}else{
				removeErrorMessage("phone_num_error");
			}
		}
	});
	
	
	
	upw.addEventListener("keyup", function(){
		document.getElementById("upw_error").className = "error";
		var level = passwordLevel(upw.value);
	})
	
	function setErrorMessage(id, message){
		document.getElementById(id).innerText = message;
	}
	
	function removeErrorMessage(id){
		document.getElementById(id).innerText = "";
	}
	
	function passwordLevel(str){
		var pattern1 = /[a-zA-Z]/;
		var pattern2 = /[0-9]/;
		var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/;
		
		if(str == "" || str.length == 0){
			return 0;
		}else{
			if(pattern1.test(str) && pattern2.test(str) && pattern3.test(str)) {
				return 4;
			} else if(!pattern1.test(str) && pattern2.test(str) && pattern3.test(str)) {
				return 3;
			} else if(pattern1.test(str) && !pattern2.test(str) && pattern3.test(str)) {
				return 3;
			} else if(pattern1.test(str) && pattern2.test(str) && !pattern3.test(str)) {
				return 3;
			} else if(!pattern1.test(str) && !pattern2.test(str) && pattern3.test(str)) {
				return 2;
			} else if(!pattern1.test(str) && pattern2.test(str) && !pattern3.test(str)) {
				return 2;
			} else if(pattern1.test(str) && !pattern2.test(str) && !pattern3.test(str)) {
				return 2;
			} else {
				return 1; 
			}
		}	
	}
	
	
	
	submit_btn.addEventListener("click", function(evt){
		if(uid.value == "" || uid.value.length == 0){
			evt.preventDefault();
			setErrorMessage("uid_error", "필수 정보입니다.");		
		}else{
			var pattern = /[^a-zA-Z0-9]/;
			if(pattern.test(uid.value)){
				evt.preventDefault();
				setErrorMessage("uid_error", "영어 또는 숫자를 입력해주세요.");
			}else{
				removeErrorMessage("uid_error");
			}
		}
		
		if(uname.value == "" || uname.length == 0){
			evt.preventDefault();
			setErrorMessage("uname_error", "필수 정보입니다.");
		}else{
			removeErrorMessage("uname_error");
		}
		
		if(email.value == "" || email.value.length == 0){
			evt.preventDefault();
			setErrorMessage("email_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(email.value)){
				evt.preventDefault();
				setErrorMessage("email_error", "이메일 형식으로 입력해주세요.")
			}else{
				removeErrorMessage("email_error");
			}
		}	
		
		var level = passwordLevel(upw.value);
		if(level == 0){
			evt.preventDefault();
			setErrorMessage("upw_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				evt.preventDefault();
				setErrorMessage("upw_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("upw_error");
			}
		}	

		var level = passwordLevel(upw_check.value);
		if(level == 0){
			evt.preventDefault();
			setErrorMessage("upw_check_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				evt.preventDefault();
				setErrorMessage("upw_check_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("upw_check_error");
			}
		}
		
		var pattern = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
		if(phone_num == "" || phone_num.value.length == 0){
			evt.preventDefault();
			setErrorMessage("phone_num_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(phone_num.value)){
				evt.preventDefault();
				setErrorMessage("phone_num_error", "휴대전화 형식으로 입력해주세요.");
			}else{
				removeErrorMessage("phone_num_error");
			}
		}
		
	});
	
});
