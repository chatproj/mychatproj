package com.example.mychatproj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mychatproj.mapper.ChatMapper;
import com.example.mychatproj.mapper.MemberMapper;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

@Service
public class ChatServiceimpl implements ChatService{
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
	
}
