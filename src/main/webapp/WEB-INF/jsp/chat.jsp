<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%@page import="com.example.mychatproj.model.Chatroom_Member"%>
<!DOCTYPE html>
<html>
<head>
<!-- css file -->
<%@ include file="./common/title.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
	<%
			ArrayList<Chatroom_Member> mychatroominfo = (ArrayList) request.getAttribute("mychatroominfo");
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
									<div class="userimg"><img class="img_inner" src=''></div>
									<div class="username"><a class="username_txt"></a></div>
								</div>
							</div>
							<button id="invitebtn" class="invitebtn" onclick="openinvite()">친구초대</button>
							<button id="filelistbtn" class="filelistbtn" onclick="openfilelist()">파일</button>
							<input type="submit" id="exitbtn" value="나가기" class="exitbtn" onclick="AjaxExitController()">
						</div>
					</div>
					
					<dialog id="invite" class="invite">
						<form method="POST" action="/invitechat">
							<div class="invite-box">
								<div class="inputlabel">방제목</div>
								<input type="hidden" name="cnum" id="cnum" value="">
								<input type="text" name="cname" id="cname" value="" readonly>
								<div id="cname_error" class="error"></div>
							</div>
							
							<div class="invite-box">
								<div class="inputlabel">아이디</div>
								<input type="text" name="uid" id="uid">
								<div id="uid_error" class="error"></div>
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
										<tr class="second_fileblock">
											<td class="originalfilename"></td>
											<td class="fileusername"></td>
											<td class="fileuploadtime"></td>
										    <form method="POST" action="/download">
										    	<input type="hidden" name="cnum" value="">
										    	<input type="hidden" name="original_filename" value="">
												<input type="hidden" name="filename" value="">
											    <td><input type="submit" id="downloadbtn" value="다운로드" class="downloadbtn"></td>	
											</form>
											<form method="POST" action="filedelete">
												<input type="hidden" id="text" name="cnum" value="">
												<input type="hidden" name="filename" value="">
											    <td><input type="submit" id="filedeletebtn" value="삭제" class="filedeletebtn"></td>										
											</form>
										</tr>
								</table>
								<div class="chatlistpage">
											 <a href="chat?cnumPK=&page=">[]</a>
											<%-- <a><input type="button" id="page" name="page" value="<%=i %>" onclick="page()"></a> --%>
								</div>
					</dialog>
					
				</div>
				
				<div id="chatform" class="chatform">
						<div>
							<div class="myLog">
								<div class="myprofile">
									<div class="myname"></div>
								<div class="myimg"><img class="img_inner" src=''></div>
									
								</div>
								<div class="mymsg"></div>
								<div class="mytime">time : <  ></div>
								<form method='POST' action='/download' id="upfile" class="upfile">
									<input type="hidden" name="cnum" value="">
									<input type='hidden' id="test" name='filename' value=''>
									<input type='hidden' name='original_filename' value=''>
									<%-- <input type="text" name="sockoriginalfilename" value='<%=chatlog.get(i).getUp_filename() %>' readonly> --%>
									<div id="sockoriginalfilename" class="sockoriginalfilename">파일명 : </div>
									<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>
								</form>
								<div class="mytime">time : <  ></div>								
							</div>
							<div class="yourLog">
								<div class="yourprofile">
									<div class="yourimg"><img class="img_inner" src=''></div>
									<div class="yourname"></div>
								</div>
								<div class="yourmsg"></div>
								<div class="yourtime">time : <  ></div>
								<form method='POST' action='/download' id="upfile" class="upfile">
									<input type="hidden" name="cnum" value="">
									<input type='hidden' name='filename' value=''>
									<input type='hidden' name='original_filename' value=''>
									<%-- <input type="text" name="sockoriginalfilename" value='<%=chatlog.get(i).getUp_filename() %>' readonly> --%>
									<div id="sockoriginalfilename" class="sockoriginalfilename">파일명 : </div>
									<input type='submit' id='downloadbtn' value='다운로드' class='downloadbtn'>
								</form>
								<div class="yourtime">time : <  ></div>	
							</div>
						</div>	
				</div>
				<div id="yourMsg" class="yourMsg">
					<table class="inputTable">	
							<form id="upload-file-form">
								<input type="hidden" id="text" name="cnum" value="">
								<input type="hidden" id="text" name="unum" value="">
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
			var msg = data.data;
			console.log(msg);
			
			var msgarr = msg.split(",");
			console.log(msgarr[0]);
			console.log(msgarr[1]);
			console.log(msgarr[2]);
			console.log(msgarr[3]);
			console.log(msgarr[4]);
			
				if( msgarr[0] == member_no ){
				var msgTemp = "<div>"
					msgTemp = "<div class='myLog'>"
					msgTemp += "<div class='myprofile'>"
					msgTemp += "<div class='myname'>"
					msgTemp += msgarr[1];
					msgTemp += "</div>"
					msgTemp += "<div class='myimg'>"
					msgTemp += msgarr[3];
					msgTemp += "</div>"
					msgTemp += "</div>"
					msgTemp += "<div class='mymsg'>"
					msgTemp += msgarr[2];
					msgTemp += "</div>"
					msgTemp += "<div class='mytime'>"
					msgTemp += "time : < "
					msgTemp += msgarr[4];
					msgTemp += " >"
					msgTemp += "</div>"
					msgTemp += "</div>"				
				$("#chatform").append(msgTemp);

			}else{
				var msgTemp = "<div>"
					msgTemp = "<div class='yourLog'>"
					msgTemp += "<div class='yourprofile'>"
					msgTemp += "<div class='yourimg'>"
					msgTemp += msgarr[3];
					msgTemp += "</div>"
					msgTemp += "<div class='yourname'>"
					msgTemp += msgarr[1];
					msgTemp += "</div>"
					msgTemp += "</div>"
					msgTemp += "<div class='yourmsg'>"
					msgTemp += msgarr[2];
					msgTemp += "</div>"
					msgTemp += "<div class='yourtime'>"
					msgTemp += "time : < "
					msgTemp += msgarr[4];
					msgTemp += " >"
					msgTemp += "</div>"
					msgTemp += "</div>"						
					$("#chatform").append(msgTemp);	
			}
			var filesearch = msgarr[2].substring(86, 98); // value='file'
			console.log(filesearch);
			
			if(filesearch == "value='file'"){
				 location.reload();
				 loaction.reload();
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
		var member_no                   =  "<%=mychatroominfo.get(0).getMember().getMember_no() %>";
		var member_name                 =  "<%=mychatroominfo.get(0).getMember().getMember_name() %>";
		var msg                         =  $("#chatting").val();
 	    var img                         =  "<img class='img_inner' src='/memberimg/${mychatroominfo.get(0).getMember_profileimg().getMember_profileimg_filename()}' >"; 
	
		var today     =  new Date();
		var hours     =  today.getHours();
		var minutes   =  today.getMinutes();
		var seconds   =  today.getSeconds();
		
		var nowTimes = hours + ":" + minutes + ":" + seconds;
		
		// 채팅 공백 시 응답 X
		var chatinput = document.querySelector("#chatting");
		
		var chatroom_no = "<%=mychatroominfo.get(0).getChatroom().getChatroom_no() %>";
		if(chatinput.value != "") {
			ws.send(member_no+","+member_name+","+msg+","+img+","+nowTimes);
			$("#chatting").value();
		}
	}
</script>

<script src="/js/AjaxController.js" type="text/javascript" charset="UTF-8"></script>
</html>