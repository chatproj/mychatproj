package com.example.mychatproj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		HttpSession session = request.getSession();
		String session_id   = (String) session.getAttribute("session_id");
		int session_no      = getsession_no(session_id);

		if(session_id != null) {
			List<Chatroom_Member> chatList  = chatservice.getchatlist(session_no);
			HashMap<Integer, String> map    = new HashMap<Integer, String>();
			
			for(int i = 0; i < chatList.size(); i++) {
				map.put(chatList.get(i).getChatroom_no(), chatList.get(i).getChatroom().getChatroom_name());
			}
			model.addAttribute("chatList", map);
			
		}else {
			return "redirect:/signin";
		}
		
		return "chatList";
	}
	
	@PostMapping("invitechat")
	public String invitechat(InviteForm inviteform, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_id   = (String) session.getAttribute("session_id");
		int session_no      = getsession_no(session_id);	
		
		String id_check = chatservice.invite_id_check(session_id, inviteform.getMember_id());
		
		if(id_check.equals("존재하지 않는 아이디 입니다.") || id_check.equals("자기 자신 초대 불가능")) {
			return "redirect:/chatList?message=FAILURE_noid";
		}else if(id_check.equals("체크 완료")){
			if(inviteform.getMember_id() != session_id && !inviteform.getMember_id().equals(session_id)) {
				
				List<Chatroom> getChatroom_no       =  chatservice.getChatroom_no();
				int maxChatroom_no = getChatroom_no.size();
				
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
				
				int my_no      = getsession_no(session_id);
				int invite_no  = getsession_no(inviteform.getMember_id());
				
				List<Integer> insertchatroom_memberforSize = new ArrayList<Integer>();
				insertchatroom_memberforSize.add(my_no);
				insertchatroom_memberforSize.add(invite_no);
				
				System.out.println(my_no);
				System.out.println(invite_no);
				
				for(int i=0; i<insertchatroom_memberforSize.size(); i++) {
					Chatroom_Member chatroom_member = new Chatroom_Member();
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
	}
	
}
