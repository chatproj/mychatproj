	var member_id = document.getElementById("member_id");
	var member_name = document.getElementById("member_name");
	var member_email = document.getElementById("member_email");
	var member_pwd = document.getElementById("member_pwd");
	var member_chkpwd = document.getElementById("member_chkpwd");
	var member_phone = document.getElementById("member_phone");
	
	var submit_btn = document.getElementById("submit_btn");
	
	member_id.addEventListener("keyup", function(){
		if(member_id.value == "" || member_id.value.length == 0){
			setErrorMessage("member_id_error", "필수 정보입니다.");		
		}else{
			var pattern = /[^a-zA-Z0-9]/;
			if(pattern.test(member_id.value)){
				setErrorMessage("member_id_error", "영어 또는 숫자를 입력해주세요.");
			}else{
				removeErrorMessage("member_id_error");
			}
		}
	});
	
	member_name.addEventListener("keyup", function(){
		if(member_name.value == "" || member_name.length == 0){
			setErrorMessage("member_name_error", "필수 정보입니다.");
		}else{
			removeErrorMessage("member_name_error");
		}
	})

	
	member_email.addEventListener("keyup", function(){
		var pattern = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+$/;
		if(member_email.value == "" || member_email.value.length == 0){
			setErrorMessage("member_email_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(member_email.value)){
				setErrorMessage("member_email_error", "이메일 형식으로 입력해주세요.")
			}else{
				removeErrorMessage("member_email_error");
			}
		}
	});
	
	member_pwd.addEventListener("keyup", function(){
		var level = passwordLevel(member_pwd.value);
		if(level == 0){
			setErrorMessage("member_pwd_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				setErrorMessage("member_pwd_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("member_pwd_error");
			}
		}
	});
	
	member_chkpwd.addEventListener("keyup", function(){
		var level = passwordLevel(member_chkpwd.value);
		if(level == 0){
			setErrorMessage("member_chkpwd_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				setErrorMessage("member_chkpwd_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("member_chkpwd_error");
			}
		}
	});
	
	member_chkpwd.addEventListener("keyup", function(){
		if(member_pwd.value != member_chkpwd.value){
			setErrorMessage("member_chkpwd_error", "비밀번호가 일치하지 않습니다.");
		}else{
			removeErrorMessage("member_chkpwd_error");
		}
	});
	
	member_phone.addEventListener("keyup", function(){
		var pattern = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
		if(member_phone == "" || member_phone.value.length == 0){
			setErrorMessage("member_phone_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(member_phone.value)){
				setErrorMessage("member_phone_error", "휴대전화 형식으로 입력해주세요.");
			}else{
				removeErrorMessage("member_phone_error");
			}
		}
	});
	
	
	
	member_pwd.addEventListener("keyup", function(){
		document.getElementById("member_pwd_error").className = "error";
		var level = passwordLevel(member_pwd.value);
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
		if(member_id.value == "" || member_id.value.length == 0){
			evt.preventDefault();
			setErrorMessage("member_id_error", "필수 정보입니다.");		
		}else{
			var pattern = /[^a-zA-Z0-9]/;
			if(pattern.test(member_id.value)){
				evt.preventDefault();
				setErrorMessage("member_id_error", "영어 또는 숫자를 입력해주세요.");
			}else{
				removeErrorMessage("member_id_error");
			}
		}
		
		if(member_name.value == "" || member_name.length == 0){
			evt.preventDefault();
			setErrorMessage("member_name_error", "필수 정보입니다.");
		}else{
			removeErrorMessage("member_name_error");
		}
		
		if(member_email.value == "" || member_email.value.length == 0){
			evt.preventDefault();
			setErrorMessage("member_email_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(member_email.value)){
				evt.preventDefault();
				setErrorMessage("member_email_error", "이메일 형식으로 입력해주세요.")
			}else{
				removeErrorMessage("member_email_error");
			}
		}	
		
		var level = passwordLevel(member_pwd.value);
		if(level == 0){
			evt.preventDefault();
			setErrorMessage("member_pwd_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				evt.preventDefault();
				setErrorMessage("member_pwd_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("member_pwd_error");
			}
		}	

		var level = passwordLevel(member_chkpwd.value);
		if(level == 0){
			evt.preventDefault();
			setErrorMessage("member_chkpwd_error", "필수 정보입니다.");
		}else{
			if(level == 1 || level == 2 || level == 3){
				evt.preventDefault();
				setErrorMessage("member_chkpwd_error", "영문,숫자,특수문자를 사용하세요.")
			}else{
				removeErrorMessage("member_chkpwd_error");
			}
		}
		
		var pattern = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
		if(member_phone == "" || member_phone.value.length == 0){
			evt.preventDefault();
			setErrorMessage("member_phone_error", "필수 정보입니다.");
		}else{
			if(!pattern.test(member_phone.value)){
				evt.preventDefault();
				setErrorMessage("member_phone_error", "휴대전화 형식으로 입력해주세요.");
			}else{
				removeErrorMessage("member_phone_error");
			}
		}
		
	});

