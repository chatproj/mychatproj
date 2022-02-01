package com.example.mychatproj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}
