<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%@page import="com.example.mychatproj.model.Chatroom_Member"%>
<%@page import="com.example.mychatproj.model.Chatlog"%>
<%@page import="com.example.mychatproj.model.Chat_filelist"%>
<!DOCTYPE html>
<html>
<head>
<!-- css file -->
<%@ include file="./common/title.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
	<%
			ArrayList<Chatroom_Member> mychatroominfo =  (ArrayList) request.getAttribute("mychatroominfo");
			ArrayList<Chatroom_Member> memberlistAll  =  (ArrayList) request.getAttribute("memberlistAll"); 
			
			ArrayList<Chatlog> chatlog                =  (ArrayList) request.getAttribute("chatlog");
			
			int file_listtotalcount                   =  (Integer) request.getAttribute("file_listtotalcount");
			int count                                 =  (Integer) request.getAttribute("count");
			ArrayList<Chat_filelist> chat_filelist    =  (ArrayList) request.getAttribute("chat_filelist");
			
	%>
<body>
	<!-- Header -->
	<%@ include file="./common/header.jsp"%>
	<div id="main_container1">
		<div class="form_container1">
			<div class="form1">	
				<div class="chatheader">
				
				<div class="chatroom_name"><%=mychatroominfo.get(0).getChatroom().getChatroom_name() %></div>
					<div id="menu_btn" class="menu_btn">
						<img class="menuiconimg" src="/memberimg/menu_icon.png">
						<div id="slideToggle" class="slideToggle">
						
							<div class="chatroomuserlist">
								<div class="chatuserlist">
									<div class="userimg"><img class="img_inner" src='memberimg/<%=mychatroominfo.get(0).getMember_profileimg().getMember_profileimg_filename() %>'></div>
									<div class="member_name"><a class="username_txt"><%=mychatroominfo.get(0).getMember().getMember_id() %></a></div>
								</div>
							</div>
						<%
							for(int i = 0; i < memberlistAll.size(); i++){
						%>							
							<div class="chatroomuserlist">
							    <div class="chatuserlist">	
									<div class="userimg"><img class="img_inner" src='/memberimg/<%=memberlistAll.get(i).getMember_profileimg().getMember_profileimg_filename() %>'></div>
									<div class="member_name"><a class="username_txt"><%=memberlistAll.get(i).getMember().getMember_id() %></a></div>
								</div>
							</div>
						<%
							}
						%>
							
							<button id="invitebtn" class="invitebtn" onclick="openinvite()">친구초대</button>
							<button id="filelistbtn" class="filelistbtn" onclick="openfilelist()">파일</button>
							<input type="submit" id="exitbtn" value="나가기" class="exitbtn" onclick="AjaxExitController()">
						</div>
					</div>
					
					<dialog id="invite" class="invite">
						<form method="POST" action="/invitemember_chat">
							<div class="invite-box">
								<div class="inputlabel">방제목</div>
								<input type="hidden" name="chatroom_no" id="chatroom_no" value="<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>">
								<input type="text" name="chatroom_name" id="chatroom_name" value="<%=mychatroominfo.get(0).getChatroom().getChatroom_name() %>" readonly>
								<div id="chatroom_name_error" class="error"></div>
							</div>
							
							<div class="invite-box">
								<div class="inputlabel">아이디</div>
								<input type="text" name="member_id" id="member_id">
								<div id="member_id_error" class="error"></div>
							</div>
							
							<input type="submit" id="submit_btn" value="초대하기"
								class="submit_btn">
		
						</form>
					</dialog>

					<dialog id="downloadFile" class="downloadFile" data-keyboard="false">
  						  		<table id="filelist_table" class="filelist_table">
  						  		<tr>
  						  		<form method="dialog">
  						  			<th class="filelist_table_header_close" colspan="5"><button id="filelistexit" class="filelistexit">X</button></th>
  						  		</form>
  						  		</tr>
  						  		<tr>
									<th class="filelist_table_header" id="filename_width">파일명</th>
									<th class="filelist_table_header">등록자</th>
									<th class="filelist_table_header">시간</th>
									<th class="filelist_table_header">다운로드</th>
									<th class="filelist_table_header">삭제</th>
								</tr>
								<%
									if(chat_filelist != null){
								%>
									<%
										for(int i = 0; i < chat_filelist.size(); i++){
									%>
										<tr class="second_fileblock">
											<td class="chatfile_original_filename"><%=chat_filelist.get(i).getChat_filelist_original_filename() %></td>
											<td class="fileusername"><%=chat_filelist.get(i).getMember().getMember_id() %></td>
											<td class="chatfile_time"><%=chat_filelist.get(i).getChat_filelist_time() %></td>
										    <form method="POST" action="/download">
												<input type="hidden" name="download_member_no" value="<%=chat_filelist.get(i).getMember_no() %>">
												<input type="hidden" name="download_chatroom_no" value="<%=chat_filelist.get(i).getChatroom_no() %>">
												<input type="hidden" name="download_filelist_time" value="<%=chat_filelist.get(i).getChat_filelist_time() %>">
												<input type="hidden" name="download_filelist_original_filename" value="<%=chat_filelist.get(i).getChat_filelist_original_filename() %>">
											    <td><input type="submit" id="downloadbtn" value="다운로드" class="downloadbtn"></td>	
											</form>
											<form method="POST" action="filedelete">
												<input type="hidden" name="chatroom_no" value="<%=chat_filelist.get(i).getChatroom_no() %>">
												<input type="hidden" name="chat_filelist_filename" value="<%=chat_filelist.get(i).getChat_filelist_filename() %>">
											    <td><input type="submit" id="filedeletebtn" value="삭제" class="filedeletebtn"></td>										
											</form>
										</tr>
									<%
										}
									%>
								 <%
								 	 }else{
								 %>
								 
								 <%
								 	 }
								 %>
								</table>
								<div class="chatlistpage">
									<%
										for(int i = 1; i <= count; i++){
									%>
											 <a href="chat?chatroom_no=<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>&page=<%=i %>">[<%=i %>]</a>
											<%-- <a><input type="button" id="page" name="page" value="<%=i %>" onclick="page()"></a> --%>
									<%
										}
									%>
								</div>
					</dialog>
					
				</div>
				
				<div id="chatform" class="chatform">
					<%
						for(int i = 0; i < chatlog.size(); i++){
					%>
						<div>
					<%
						  if(chatlog.get(i).getMember_no() == mychatroominfo.get(0).getMember().getMember_no()) {
					%>
							<div class="myLog">
								<div class="myprofile">
									<div class="myname"><%=chatlog.get(i).getMember().getMember_id() %></div>
								<div class="myimg"><img class="img_inner" src='memberimg/<%=chatlog.get(i).getMember_profileimg().getMember_profileimg_filename() %>'></div>
									
								</div>
							<%
								if(chatlog.get(i).getChatlog_division().equals("text")) {
							%>
								<div class="mymsg"><%=chatlog.get(i).getChatlog_log() %></div>
								<div class="mytime">time : < <%=chatlog.get(i).getChatlog_time() %> ></div>
							<%
								}else if(chatlog.get(i).getChatlog_division().equals("file")) {
							%>
								<%
									if(chatlog.get(i).getChat_filelist().getChat_filelist_filename() != null){
								%>
								<form method='POST' action='/download' id="upfile" class="upfile">
									<input type="hidden"           name="download_member_no" value="<%=chatlog.get(i).getMember_no() %>">
									<input type="hidden"           name="download_chatroom_no" value="<%=chatlog.get(i).getChatroom_no() %>">
									<input type="hidden"           name="download_filelist_time" value="<%=chatlog.get(i).getChat_filelist().getChat_filelist_time() %>">
									<input type="hidden"           name="download_filelist_original_filename" value="<%=chatlog.get(i).getChat_filelist().getChat_filelist_original_filename() %>">
									<div id="sockoriginalfilename" name="download_filelist_original_filename" class="sockoriginalfilename">파일명 : <%=chatlog.get(i).getChat_filelist().getChat_filelist_original_filename() %> </div>
									<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>
								</form>
								<div class="mytime">time : < <%=chatlog.get(i).getChatlog_time() %> ></div>	
								<%
									}else{
								%>
									<div class="mytime">삭제된 파일입니다.</div>
								<%
									}
								%>
							<%
								}
							%>							
							</div>
					<%
						  }else{
					%>
							<div class="yourLog">
								<div class="yourprofile">
									<div class="yourimg"><img class="img_inner" src='memberimg/<%=chatlog.get(i).getMember_profileimg().getMember_profileimg_filename() %>'></div>
									<div class="yourname"><%=chatlog.get(i).getMember().getMember_id() %></div>
								</div>
							<%
								if(chatlog.get(i).getChatlog_division().equals("text")) {
							%>
								<div class="yourmsg"><%=chatlog.get(i).getChatlog_log() %></div>
								<div class="yourtime">time : < <%=chatlog.get(i).getChatlog_time() %> ></div>
							<%
								}else if(chatlog.get(i).getChatlog_division().equals("file")) {
							%>
								<%
									if(chatlog.get(i).getChat_filelist().getChat_filelist_filename() != null){
								%>
								<form method='POST' action='/download' id="upfile" class="upfile">
									<input type="hidden"           name="download_member_no" value="<%=chatlog.get(i).getMember_no() %>">
									<input type="hidden"           name="download_chatroom_no" value="<%=chatlog.get(i).getChatroom_no() %>">
									<input type="hidden"           name="download_filelist_time" value="<%=chatlog.get(i).getChat_filelist().getChat_filelist_time() %>">
									<input type="hidden"           name="download_filelist_original_filename" value="<%=chatlog.get(i).getChat_filelist().getChat_filelist_original_filename() %>">
									<div id="sockoriginalfilename" class="sockoriginalfilename">파일명 : <%=chatlog.get(i).getChat_filelist().getChat_filelist_original_filename() %> </div>
									<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>
								</form>
								<div class="yourtime">time : < <%=chatlog.get(i).getChatlog_time() %> ></div>
								<%
									}else{
								%>
								    <div class="yourtime">삭제된 파일입니다.</div>
								<%
									}
								%>
							<%
								}
							%>		
							</div>
					<%
						  }
					%>
						</div>	
					<%
						}
					%>
				</div>
				<div id="yourMsg" class="yourMsg">
					<table class="inputTable">	
							<form id="upload-file-form">
							    <input type="hidden" id="text" name="member_no" value="<%=mychatroominfo.get(0).getMember().getMember_no() %>">
								<input type="hidden" id="text" name="chatroom_no" value="<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>">
								<th><input id="uploadinput" class="uploadinput" type="file" name="fileupload" accept="*" /></th>
								<th><button onclick="upload()" type="button" id="uploadBtn" class="sendBtn">업로드</button></th>
							</form>	
										
						</tr>
						<tr>
							<th><input id="chatting" class="chatting"  placeholder="보내실 메시지를 입력하세요."></th>
							<th><button onclick="send()" id="sendBtn" class="sendBtn">보내기</button></th>
						</tr>
					</table>
				</div>
				
			</div>
		</div>
	</div>
</body>
	
<script type="text/javascript">
	$(document).ready(function(){
		$("#menu_btn").click(function(){
			$("#slideToggle").animate({width: 'toggle'}, 400);
		})
	})
</script>

<script type="text/javascript">
	var ws;
	var scrolldiv;
	var scrolldiv = document.getElementById("chatform");
	scrolldiv.scrollTop = scrolldiv.scrollHeight;
	
	wsOpen();
	function wsOpen() {
		ws = new WebSocket("ws://" + location.host + "/chating");
		wsEvt();
	}
	function wsEvt() {
		ws.onopen = function(data) {
			//소켓이 열리면 초기화 세팅하기
		}
		ws.onmessage = function(data) {
			var member_no = "<%=mychatroominfo.get(0).getMember().getMember_no() %>";
			var chatroom_no = "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>"
			var msg = data.data;
			console.log(msg);
			
			var msgarr = msg.split(",");
			console.log(msgarr[0]);  // member_no
			console.log(msgarr[1]);  // 구분
			console.log(msgarr[2]);  // member_name
			console.log(msgarr[3]);  // msg or sockfilename
			console.log(msgarr[4]);  // member img
			console.log(msgarr[5]);  // nowTimes
			console.log(msgarr[6]);  // chatroom_no
			console.log(msgarr[7]);  // filelistTimes
		
			if(msgarr[6] == chatroom_no){
			
			if(msgarr[1] == "exit"){
				if( msgarr[0] == member_no){
					console.log("dds : " + msgarr[3]);
					var msgTemp = "<div>"
						msgTemp += msgarr[3];
						msgTemp += "</div>"
						$("#chatform").append(msgTemp);
				}else{
					console.log("dds : " + msgarr[3]);
					var msgTemp = "<div>"
						msgTemp += msgarr[3];
						msgTemp += "</div>"
						$("#chatform").append(msgTemp);					
				}
				
			}else if(msgarr[1] == "file") {
				if( msgarr[0] == member_no ){
					var msgTemp = "<div>"
						msgTemp = "<div class='myLog'>"
						msgTemp += "<div class='myprofile'>"
						msgTemp += "<div class='myname'>"
						msgTemp += msgarr[2];
						msgTemp += "</div>"
						msgTemp += "<div class='myimg'>"
						msgTemp += msgarr[4];
						msgTemp += "</div>"
						msgTemp += "</div>"
						msgTemp += "<form method='POST' action='/download' id='upfile' class='upfile'>"
						msgTemp += "<input type='hidden' name='download_member_no'     value='";
						msgTemp += msgarr[0];
						msgTemp += "'>";
						msgTemp += "<input type='hidden' name='download_chatroom_no'   value='";
						msgTemp += msgarr[6];
						msgTemp += "'>";
						msgTemp += "<input type='hidden' name='download_filelist_time' value='";
						msgTemp += msgarr[7]; 
						msgTemp += "'>";
						msgTemp +="<input type='hidden'  name='download_filelist_original_filename' value='";
						msgTemp += msgarr[3];
						msgTemp += "'>";
						msgTemp += "<div id='sockoriginalfilename' class='sockoriginalfilename'>파일명 : "
						msgTemp += msgarr[3];
						msgTemp += "</div>"
						msgTemp += "<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>"
						msgTemp += "<div class='mytime'>"
						msgTemp += "time : < "
						msgTemp += msgarr[5];
						msgTemp += " >"
						msgTemp += "</div>"
						msgTemp += "</form>"
						msgTemp += "</div>"					
					$("#chatform").append(msgTemp);

				   }else{
					var msgTemp = "<div>"
						msgTemp = "<div class='yourLog'>"
						msgTemp += "<div class='yourprofile'>"
						msgTemp += "<div class='yourimg'>"
						msgTemp += msgarr[4];
						msgTemp += "</div>"
						msgTemp += "<div class='yourname'>"
						msgTemp += msgarr[2];
						msgTemp += "</div>"
						msgTemp += "</div>"
						msgTemp += "<form method='POST' action='/download' id='upfile' class='upfile'>"
						msgTemp += "<input type='hidden' name='download_member_no'     value='";
						msgTemp += msgarr[0];
						msgTemp += "'>";
						msgTemp += "<input type='hidden' name='download_chatroom_no'   value='";
						msgTemp += msgarr[6];
						msgTemp += "'>";
						msgTemp += "<input type='hidden' name='download_filelist_time' value='";
						msgTemp += msgarr[7]; 
						msgTemp += "'>";
						msgTemp +="<input type='hidden'  name='download_filelist_original_filename' value='";
						msgTemp += msgarr[3];
						msgTemp += "'>";
						msgTemp += "<div id='sockoriginalfilename' class='sockoriginalfilename'>파일명 : "
						msgTemp += msgarr[3];
						msgTemp += "</div>"
						msgTemp += "<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>"
						msgTemp += "<div class='yourtime'>"
						msgTemp += "time : < "
						msgTemp += msgarr[5];
						msgTemp += " >"
						msgTemp += "</div>"
						msgTemp += "</form>"
						msgTemp += "</div>"						
					$("#chatform").append(msgTemp);	
						location.reload();
				   }
			}else if(msgarr[1] == "text"){
				if( msgarr[0] == member_no ){
				var msgTemp = "<div>"
					msgTemp = "<div class='myLog'>"
					msgTemp += "<div class='myprofile'>"
					msgTemp += "<div class='myname'>"
					msgTemp += msgarr[2];
					msgTemp += "</div>"
					msgTemp += "<div class='myimg'>"
					msgTemp += msgarr[4];
					msgTemp += "</div>"
					msgTemp += "</div>"
					msgTemp += "<div class='mymsg'>"
					msgTemp += msgarr[3];
					msgTemp += "</div>"
					msgTemp += "<div class='mytime'>"
					msgTemp += "time : < "
					msgTemp += msgarr[5];
					msgTemp += " >"
					msgTemp += "</div>"
					msgTemp += "</div>"				
				$("#chatform").append(msgTemp);

			   }else{
				var msgTemp = "<div>"
					msgTemp = "<div class='yourLog'>"
					msgTemp += "<div class='yourprofile'>"
					msgTemp += "<div class='yourimg'>"
					msgTemp += msgarr[4];
					msgTemp += "</div>"
					msgTemp += "<div class='yourname'>"
					msgTemp += msgarr[2];
					msgTemp += "</div>"
					msgTemp += "</div>"
					msgTemp += "<div class='yourmsg'>"
					msgTemp += msgarr[3];
					msgTemp += "</div>"
					msgTemp += "<div class='yourtime'>"
					msgTemp += "time : < "
					msgTemp += msgarr[5];
					msgTemp += " >"
					msgTemp += "</div>"
					msgTemp += "</div>"						
					$("#chatform").append(msgTemp);	
			   }
			}
		}
			var filesearch = msgarr[2].substring(61, 73); // value='file'
			console.log(filesearch);
			
			if(filesearch == "value='file'"){

			}
			
			var scrolldiv = document.getElementById("chatform");
			scrolldiv.scrollTop = scrolldiv.scrollHeight;
			
		}
		document.addEventListener("keypress", function(e) {
			if (e.keyCode == 13) { //enter press
				send();
			}
		});
	}	
	function send() {
		var member_no     =   "<%=mychatroominfo.get(0).getMember().getMember_no() %>";
		var member_name   =   "<%=mychatroominfo.get(0).getMember().getMember_name() %>";
		var msg           =   $("#chatting").val();
 	    var img           =   "<img class='img_inner' src='/memberimg/${mychatroominfo.get(0).getMember_profileimg().getMember_profileimg_filename()}' >"; 
	
		var today     =  new Date();
		var hours     =  today.getHours();
		var minutes   =  today.getMinutes();
		var seconds   =  today.getSeconds();
		
		var nowTimes = (("00"+hours.toString()).slice(-2)) + ":" + (("00"+minutes.toString()).slice(-2)) + ":" + (("00"+seconds.toString()).slice(-2)); 
		
		// 채팅 공백 시 응답 X
		var chatinput = document.querySelector("#chatting");
		
		var chatroom_no = "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>";
		if(chatinput.value != "") {
			ws.send(member_no+","+"text"+","+member_name+","+msg+","+img+","+nowTimes+","+chatroom_no);
			$('#chatting').val("");
			AjaxInsertChatText(msg, nowTimes);
		}
		
			function AjaxInsertChatText(msg, nowTimes) {
				var chatroom_no    =  "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>"
			
				$.ajax({
					type: 'POST',
					url: '/chat',
					data: {
						chatroom_no: chatroom_no,
						msg: msg,
						nowTimes: nowTimes
					},
					success: function(data){
						
					},
					error: function(data){
						console.log("no", data);
					}
				});
			}
	}
	
	function upload() {
		$.ajax({
			  url: "/uploadFile",
			  type: "POST",
			  data: new FormData($("#upload-file-form")[0]),
			  enctype: 'multipart/form-data',
			  processData: false,
			  contentType: false,
			  cache: false,
			  success: function () {
			    	
			  },
			  error: function () {

			  }
		}); 
		
		var fileValue = $("#uploadinput").val().split("\\");
		var fileName = fileValue[fileValue.length-1]; // 파일명
		var member_no     =   "<%=mychatroominfo.get(0).getMember().getMember_no() %>";
		var member_name   =   "<%=mychatroominfo.get(0).getMember().getMember_name() %>";
		
		var fileValue = $("#uploadinput").val().split("\\");
		var sockfilename = fileValue[fileValue.length-1]; // 파일명
		
 	    var img           =   "<img class='img_inner' src='/memberimg/${mychatroominfo.get(0).getMember_profileimg().getMember_profileimg_filename()}' >"; 
	
		var today     =  new Date();
		var years     =  today.getFullYear();
		var month     =  today.getMonth()+1;
		var date      =  today.getDate();
		var hours     =  today.getHours();
		var minutes   =  today.getMinutes();
		var seconds   =  today.getSeconds();
		
		var nowTimes        =  (("00"+hours.toString()).slice(-2)) + ":" + (("00"+minutes.toString()).slice(-2)) + ":" + (("00"+seconds.toString()).slice(-2)); 
		var filelistTimes   =  years + "-" + (("00"+month.toString()).slice(-2)) + "-" + (("00"+date.toString()).slice(-2)) + "_" + (("00"+hours.toString()).slice(-2)) + ":" + (("00"+minutes.toString()).slice(-2)) + ":" + (("00"+seconds.toString()).slice(-2)); 
		
		console.log("month : " + month);
		var chatroom_no = "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>";
		
		setTimeout(function() {
			ws.send(member_no+","+"file"+","+member_name+","+sockfilename+","+img+","+nowTimes+","+chatroom_no+","+filelistTimes);
		}, 500);
		$('#uploadinput').val("");
		
	}
</script>

<script type="text/javascript">
	var downloadFile = document.getElementById('downloadFile');
	function openfilelist() {
		if(typeof downloadFile.showModal === "function") {
			downloadFile.showModal();
		}else{
			alert("The <dialog> API is not supported by this browser");
		}
	}
</script>

<script type="text/javascript">
	<%
		if(request.getParameter("page") != null){
	%>
			history.scrollRestoration = "auto";
			downloadFile.showModal();
	<%
		}
	%>
</script>

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

<script type="text/javascript">
	function  AjaxExitController() {
		var member_no     =   "<%=mychatroominfo.get(0).getMember().getMember_no() %>";
		var member_name   =   "<%=mychatroominfo.get(0).getMember().getMember_name() %>";
		var msg           =   "<%=mychatroominfo.get(0).getMember().getMember_name() %> 님이 퇴장하였습니다.";
 	    var img           =   "<img class='img_inner' src='/memberimg/${mychatroominfo.get(0).getMember_profileimg().getMember_profileimg_filename()}' >"; 
	
		var today     =  new Date();
		var hours     =  today.getHours();
		var minutes   =  today.getMinutes();
		var seconds   =  today.getSeconds();
		
		var nowTimes = (("00"+hours.toString()).slice(-2)) + ":" + (("00"+minutes.toString()).slice(-2)) + ":" + (("00"+seconds.toString()).slice(-2)); 
		var chatroom_no    =  "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>"
			
		
 		$.ajax({
			type: 'POST',
			url: '/chatexit',
			data: {
				chatroom_no: chatroom_no
			},
			success: function(data){

				ws.send(member_no+","+"exit"+","+member_name+","+msg+","+img+","+nowTimes+","+chatroom_no);	
 				window.location.href="/chatList";  		
			},
			error: function(data){
				console.log("no", data);
			}
		}) 
	}
	
</script>

<script src="/js/AjaxController.js" type="text/javascript" charset="UTF-8"></script>
</html>