	var member_id = document.getElementById("member_id");
	var member_pwd = document.getElementById("member_pwd");
	
	var submit_btn = document.getElementById("submit_btn");
	
	member_id.addEventListener("keyup", function(){
		if(member_id.value == "" || member_id.value.length == 0){
			setErrorMessage("member_id_error", "필수 정보입니다.");		
		}else{
			removeErrorMessage("member_id_error");
		}
	});
	
	member_pwd.addEventListener("keyup", function(){
		if(member_pwd.value == "" || member_pwd.value.length == 0){
			setErrorMessage("member_pwd_error", "필수 정보입니다.");
		}else{
			removeErrorMessage("member_pwd_error");
		}
	});
	
	function setErrorMessage(id, message){
		document.getElementById(id).innerText = message;
	}
	
	function removeErrorMessage(id){
		document.getElementById(id).innerText = "";
	}
	
	submit_btn.addEventListener("click", function(evt){
		if(member_id.value == "" || member_id.value.length == 0){
			evt.preventDefault();
			setErrorMessage("member_id_error", "필수 정보입니다.");		
		}else{
			removeErrorMessage("member_id_error");
		}
			
		if(member_pwd.value == "" || member_pwd.value.length == 0){
			evt.preventDefault();
			setErrorMessage("member_pwd_error", "비밀번호를 입력해주세요.");
		}else{
			removeErrorMessage("member_pwd_error");
		}	
	});

