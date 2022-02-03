package com.example.mychatproj.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mychatproj.model.Chat_filelist;
import com.example.mychatproj.model.Chatlog;
import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;
import com.example.mychatproj.service.ChatService;

@Controller
public class ChatController {
	@Autowired
	private ChatService chatservice;
	
	@Autowired
	ServletContext application;
	
	private int getsession_no(String session_id) {
		Optional<Member> session_no = chatservice.getSession_no(session_id); 
		
		return session_no.get().getMember_no();
	}
	
	@RequestMapping("chatList")
	public String chatlist(HttpServletRequest request, Model model) {
		HttpSession session  =  request.getSession();
		String session_id    =  (String) session.getAttribute("session_id");
		int session_no       =  getsession_no(session_id);

		if(session_id != null) {
			List<Chatroom_Member> chatList   =  chatservice.getchatroomlist(session_no);
			HashMap<Integer, String> map     =  new HashMap<Integer, String>();
			
			for(int i = 0; i < chatList.size(); i++) {
				map.put(chatList.get(i).getChatroom_no(), chatList.get(i).getChatroom().getChatroom_name());
			}
	     		model.addAttribute("chatList", map);
			
		}else {
			
			return "redirect:/signin";
		}
		
	    	return "chatList";
	}
	
	@PostMapping("Joinchatroom")
	public String joinchatroom(Chatroom_Member form, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String list = request.getParameter("list");
		String splitlist[] = list.split("[.]");
		
		redirectAttributes.addAttribute("chatroom_no", splitlist[0]);
		return "redirect:/chat";
	}
	
	@PostMapping("invitechat")
	public String invitechat(InviteForm inviteform, HttpServletRequest request) {
		HttpSession session   =   request.getSession();
		String session_id     =   (String) session.getAttribute("session_id");
		int session_no        =   getsession_no(session_id);	
		
		if(session_id != null) {
			
			String id_check       =   chatservice.invite_id_check(session_id, inviteform.getMember_id());
			
			if(id_check.equals("존재하지 않는 아이디 입니다.") || id_check.equals("자기 자신 초대 불가능")) {
				
				return "redirect:/chatList?message=FAILURE_noid";
			
			}else if (id_check.equals("체크 완료")){
				if(inviteform.getMember_id() != session_id && !inviteform.getMember_id().equals(session_id)) {
					
					List<Chatroom> getChatroom_no       =   chatservice.getChatroom_no();
					int maxChatroom_no                  =   getChatroom_no.size();
					
					Chatroom chatroom                   =  new Chatroom();
					
					try {
					
						chatroom.setChatroom_no      (maxChatroom_no + 1);
						chatroom.setChatroom_name    (inviteform.getChatroom_name());
						chatservice.insertChatroom   (chatroom); 
						
					}catch(NoSuchElementException e) {
						
						chatroom.setChatroom_no      (1);
						chatroom.setChatroom_name    (inviteform.getChatroom_name());
						chatservice.insertChatroom   (chatroom);
					
					}
					
					int my_no        = getsession_no(session_id);
					int invite_no    = getsession_no(inviteform.getMember_id());
					
					List<Integer> insertchatroom_memberforSize = new ArrayList<Integer>();
					insertchatroom_memberforSize.add   (my_no);
					insertchatroom_memberforSize.add   (invite_no);
					
					for(int i=0; i<insertchatroom_memberforSize.size(); i++) {
						Chatroom_Member chatroom_member   =   new Chatroom_Member();
						chatroom_member.setMember_no(insertchatroom_memberforSize.get(i));
					
						try {
							chatroom_member.setChatroom_no(maxChatroom_no + 1);	
						}catch(NoSuchElementException e) {
							chatroom_member.setChatroom_no(maxChatroom_no);
						}
						    chatservice.insertChatroom_Member(chatroom_member);
					}	
				}		
			}	
				return "redirect:/chatList";
		}else {
			
			return "redirect:/";
		}
	}
	
	@RequestMapping("/chat")
	public String chatpage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam("chatroom_no") int chatroom_no,			
			@RequestParam(value= "msg",     required=false) String msg,
			@RequestParam(value= "nowTimes", required=false) String nowTimes,
			@RequestParam(value= "sockfilename", required=false) String sockfilename ) {
		
		HttpSession session   =   request.getSession();
		String session_id     =   (String) session.getAttribute("session_id");	
		System.out.println(session_id);
		
		if(session_id != null) {
				int session_no             =   getsession_no(session_id);
				
				// 없는 chatroom_no 매개변수에 대한 redirect
				String chatroom_no_check   =   chatservice.getchatroomMemberlistAll(chatroom_no);
				System.out.println("채팅방 여부 체크 : " + chatroom_no_check);
				if(chatroom_no_check.equals("chatroom not found")) {
					return "redirect:/chatList";
				}
				
				// 채팅방 미포함 member 접근 시 redirect
				String chatroom_member_include_check = chatservice.getchatroom_memberInfo(session_no, chatroom_no);
				System.out.println("비정상 접근 체크 : " + chatroom_member_include_check);
				if(chatroom_member_include_check.equals("비정상적인 접근")) {
					return "redirect:/chatList";
				}
				
				// log insert Text
				Chatlog insertLog = new Chatlog();
				try {
					if(!msg.equals("") && msg != null) {
						insertLog.setMember_no(session_no);
						insertLog.setChatroom_no(chatroom_no);
						insertLog.setChatlog_log(msg);
						insertLog.setChatlog_time(nowTimes);
						insertLog.setChatlog_division("text");
						
						chatservice.insertLogText(insertLog);
					}
				}catch(NullPointerException e) {
					
				}
				
				// log insert File
				try {
					if(!sockfilename.equals("") && sockfilename != null) {
						insertLog.setMember_no(session_no);
						insertLog.setChatroom_no(chatroom_no);
						insertLog.setChatlog_log(msg);
						insertLog.setChatlog_time(nowTimes);
						insertLog.setChatlog_division("file");
					}
				}catch(NullPointerException e) {
					
				}
				
				// 채팅방 참여자 목록
				List<Chatroom_Member> memberlist           =   chatservice.getmemberlistinfo(chatroom_no);
				ArrayList<Chatroom_Member> mychatroominfo          =   new ArrayList<>();
				ArrayList<Chatroom_Member> memberlistAll   =   new ArrayList<>();
				
				for(int i = 0; i<memberlist.size(); i++) {
					if(session_no == memberlist.get(i).getMember().getMember_no()) {
						mychatroominfo.add(memberlist.get(i));
					}else {
						memberlistAll.add(memberlist.get(i));
					}
				}
				
				System.out.println(mychatroominfo);
				System.out.println(memberlistAll);
				
				model.addAttribute("mychatroominfo", mychatroominfo);
				model.addAttribute("memberlistAll", memberlistAll);
			
			return "chat";
		}else {
			
			return "redirect:/";
		}
	}
	
	@PostMapping("/uploadFile")
	public String upload_file(Chat_filelist form,
							  RedirectAttributes redirectAttributes,
			 			      HttpServletRequest request,
						      @RequestParam("fileupload") MultipartFile files,
							  @RequestParam("chatroom_no") int chatroom_no) throws Exception {
		
		System.out.println("ddd" + files.getOriginalFilename());
		
		Chat_filelist fileupload = new Chat_filelist();
		fileupload.setMember_no(form.getMember_no());
		fileupload.setChatroom_no(form.getChatroom_no());
		
		SimpleDateFormat nowTimes   =   new SimpleDateFormat("yyyy-MM-dd_HH:mm");
		Calendar now                =   Calendar.getInstance();
		String time                 =   nowTimes.format(now.getTime());
		fileupload.setChat_filelist_time(time);
		
		String originalfilename           =  files.getOriginalFilename();
		String originalfilenameExtension  =  FilenameUtils.getExtension(originalfilename).toLowerCase();
		File destinationfile;
		String destinationfilename;
		String fileurl                    =  "/uploadfile/";
		String savePath                   =  application.getRealPath(fileurl);
		
		do {
			destinationfilename  =  RandomStringUtils.randomAlphabetic(32) + "." + originalfilenameExtension;
			destinationfile      =  new File(savePath, destinationfilename);
		}while(destinationfile.exists());
		
		try {
			files.transferTo(destinationfile);
		}catch(IOException e) {
			
		}
		
		fileupload.setChat_filelist_filename(destinationfilename);
		fileupload.setChat_filelist_original_filename(originalfilename);
		fileupload.setChat_filelist_url(savePath);
		
		chatservice.insertchatfile(fileupload);
		
		redirectAttributes.addAttribute("chatroom_no", form.getChatroom_no());
		redirectAttributes.addAttribute("sockfilename", destinationfilename);
		
		System.out.println("dsfsfsdfs");
		
		return "redirect:chat";
	}
	
}
