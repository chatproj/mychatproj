	var member_id = document.getElementById("member_id");
	var chatroom_name = document.getElementById("chatroom_name");
	
	var submit_btn = document.getElementById("submit_btn");
	
	member_id.addEventListener("keyup", function(){
		if(member_id.value == "" || member_id.value.length == 0){
			setErrorMessage("member_id_error", "필수 정보입니다.");		
		}else{
			removeErrorMessage("member_id_error");
		}
	});
	
	chatroom_name.addEventListener("keyup", function(){
		if(chatroom_name.value == "" || chatroom_name.value.length == 0){
			setErrorMessage("chatroom_name_error", "채팅룸 이름을 입력해주세요.");
		}else{
			removeErrorMessage("chatroom_name_error");
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
			
		if(chatroom_name.value == "" || chatroom_name.value.length == 0){
			evt.preventDefault();
			setErrorMessage("chatroom_name_error", "채팅룸 이름을 입력해주세요.");
		}else{
			removeErrorMessage("chatroom_name_error");
		}	
	});

