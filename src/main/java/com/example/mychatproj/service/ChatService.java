package com.example.mychatproj.service;

import java.util.List;
import java.util.Optional;

import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

public interface ChatService {
	Optional<Member> getSession_no(String member_id);
	
	// chatlist
	List<Chatroom_Member> getchatlist(int member_no);

	// inviteMember and create chatroom
		// id check
		String invite_id_check(String session_id, String member_id);
		// get chatroom_id
		List<Chatroom> getChatroom_no();
		// insert chatroom
		void insertChatroom(Chatroom chatroom);
		// insert chatroom_member
		void insertChatroom_Member(Chatroom_Member chatroom_member);
	
}
