package com.example.mychatproj.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mychatproj.controller.InviteForm;
import com.example.mychatproj.mapper.ChatMapper;
import com.example.mychatproj.mapper.MemberMapper;
import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

@Service
public class ChatServiceimpl implements ChatService{
	
	@Autowired
	private MemberMapper membermapper;
	@Autowired
	private ChatMapper chatmapper;
	
	@Override
	public Optional<Member> getSession_no(String member_id) {
		
		return chatmapper.getSession_no(member_id);
	}
	
	@Override
	public List<Chatroom_Member> getchatlist(int member_no) {
		
		return chatmapper.getchatlist(member_no); 
	}

	@Override
	public String invite_id_check(String session_id, String member_id) {
		return validateDuplicateId(session_id, member_id);
	}
	private String validateDuplicateId(String session_id, String member_id) {
		String res = null;
		
		try {
			Optional<Member> id_check = membermapper.getById(member_id);
			
			if(session_id.equals(id_check.get().getMember_id())) {
				res = "자기 자신 초대 불가능";
			}else {
				res = "체크 완료";
			}
		}catch(NoSuchElementException e) {
			res = "존재하지 않는 아이디 입니다.";
		}
		
		return res;
	}
	
	@Override
	public List<Chatroom> getChatroom_no() {
		return chatmapper.getChatroom_no();
	}
	@Override
	public void insertChatroom(Chatroom chatroom) {
		chatmapper.insertChatroom(chatroom);
	}
	@Override
	public void insertChatroom_Member(Chatroom_Member chatroom_member) {
		chatmapper.insertChatroom_Member(chatroom_member);
	}
	
}
