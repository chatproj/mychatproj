package com.example.mychatproj.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

@Mapper
public interface ChatMapper {
	Optional<Member> getSession_no(@Param("member_id") String member_id);
	
	List<Chatroom_Member> getchatroomlist(@Param("member_no") int member_no);
	
	List<Chatroom> getChatroom_no();
	void insertChatroom(@Param("chatroom") Chatroom chatroom);
	void insertChatroom_Member(@Param("chatroom_member") Chatroom_Member chatroom_member);
	
	List<Chatroom_Member> getchatroomMemberlistAll();
	List<Chatroom_Member> getincludeMemberlist(@Param("chatroom_no") int chatroom_no);
	
	List<Chatroom_Member> memberlistinfo(@Param("chatroom_no") int chatroom_no);
}
