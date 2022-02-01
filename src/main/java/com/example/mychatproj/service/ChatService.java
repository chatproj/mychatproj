package com.example.mychatproj.service;

import java.util.List;
import java.util.Optional;

import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

public interface ChatService {
	Optional<Member> getSession_no(String member_id);
	
	List<Chatroom_Member> getchatlist(int member_no);
}
