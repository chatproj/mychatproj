package com.example.mychatproj.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mychatproj.model.Chat_filelist;
import com.example.mychatproj.model.Chatlog;
import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

@Mapper
public interface ChatMapper {
	Optional<Member> getmember_no(@Param("member_id") String member_id);
	
	List<Chatroom_Member> getchatroomlist(@Param("member_no") int member_no);
	
	List<Chatroom> getChatroom_no();
	void insertChatroom(@Param("chatroom") Chatroom chatroom);
	void insertChatroom_Member(@Param("chatroom_member") Chatroom_Member chatroom_member);
	
	List<Chatroom_Member> getchatroomMemberlistAll();
	List<Chatroom_Member> getincludeMemberlist(@Param("chatroom_no") int chatroom_no);
	
	List<Chatroom_Member> memberlistinfo(@Param("chatroom_no") int chatroom_no);
	
	void insertLog(@Param("chatlog") Chatlog chatlog);
	
	void insertchatfile(@Param("chat_filelist") Chat_filelist chat_filelist);
	
	List<Chatlog> getchatlog(@Param("chatroom_no") int chatroom_no);
	
	Optional<Chat_filelist> getchatlist_filename(@Param("member_no") int member_no,
											     @Param("chatroom_no") int chatroom_no,
											     @Param("chat_filelist_original_filename") String chat_filelist_original_filename,
											     @Param("chat_filelist_time") String chat_filelist_time);
	
	int getTotal_filelist(@Param("chatroom_no") int chatroom_no);
	
	List<Chat_filelist> getchatfilelist(@Param("chatroom_no") int chatroom_no, @Param("startPage") int startPage, @Param("onePageCnt") int onePageCnt);

	void deletefile(@Param("filename") String filename);
	
	Optional<Chatroom_Member> member_valid_check(@Param("chatroom_no") int chatroom_no, @Param("member_no") int member_no);
}
