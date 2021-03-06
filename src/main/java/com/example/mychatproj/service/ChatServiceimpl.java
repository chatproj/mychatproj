package com.example.mychatproj.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mychatproj.controller.InviteForm;
import com.example.mychatproj.mapper.ChatMapper;
import com.example.mychatproj.mapper.MemberMapper;
import com.example.mychatproj.model.Chat_filelist;
import com.example.mychatproj.model.Chatlog;
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
	public Optional<Member> getmember_no(String member_id) {
		
		return chatmapper.getmember_no(member_id);
	}
	
	@Override
	public List<Chatroom_Member> getchatroomlist(int member_no) {
		
		return chatmapper.getchatroomlist(member_no); 
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
	public String invite_id_check(String session_id, String member_id) {
		String res = null;
				
			try {
				Optional<Member> id_check = membermapper.getById(member_id);
				
				if(session_id.equals(id_check.get().getMember_id())) {
					res  =  "자기 자신 초대 불가능";
				}else {
					res  =  "체크 완료";
				}
			}catch(NoSuchElementException e) {
				    res  =  "존재하지 않는 아이디 입니다.";
			}
			
		return res;		
	}
	@Override
	public String member_valid_check(int chatroom_no, int member_no) {
		String res = null;
		
			try {
				Optional<Chatroom_Member> member_valid_check = chatmapper.member_valid_check(chatroom_no, member_no);
				member_valid_check.get().getMember_no();
				
				res = "이미 존재하는 멤버입니다.";
			}catch(NoSuchElementException e) {
				res = "체크 완료";
			}

		return res;
	}
	
	@Override
	public void insertChatroom_Member(Chatroom_Member chatroom_member) {
		
		chatmapper.insertChatroom_Member(chatroom_member);
	}
	
	@Override
	public String getchatroomMemberlistAll(int chatroom_no) {
		String res = chatroom_no_check(chatroom_no);
		
		return res;
	}
	private String chatroom_no_check(int chatroom_no) {
		String res = null;
		
		List<Chatroom_Member> chatroom_memberAll  =  chatmapper.getchatroomMemberlistAll();
		int compare_chatroomlist[]                =  new int[chatroom_memberAll.size()];
		for(int i = 0 ; i < chatroom_memberAll.size(); i++) {
			compare_chatroomlist[i] = chatroom_memberAll.get(i).getChatroom_no();
		}
		
		int chatroom_member_match = 0;
		while(true) {
			if(chatroom_no != compare_chatroomlist[chatroom_member_match]) {
				chatroom_member_match++;
				if(chatroom_member_match == chatroom_memberAll.size()) {
					res = "chatroom not found";
					break;
				}
			}else {
				res = "chatroomOK";
				break;
			}
		}
		
		
		return res;
	}
	
	@Override
	public String getchatroom_memberInfo(int session_no, int chatroom_no) {
		String res = member_include_check(session_no, chatroom_no);
		return res;
	}
	private String member_include_check(int session_no, int chatroom_no) {
		String res = null;

		List<Chatroom_Member> include_memberlist  =  chatmapper.getincludeMemberlist(chatroom_no);
		int member_no_list[] = new int[include_memberlist.size()];
		for(int i = 0; i < include_memberlist.size(); i++) {
			member_no_list[i] = include_memberlist.get(i).getMember_no();
		}

		int member_no_match = 0;
		while(true) {
			if(session_no != member_no_list[member_no_match]) {
				member_no_match++;
				if(member_no_match == include_memberlist.size()) {
					res = "비정상적인 접근";
					break;
				}
			}else {
				res = "접근허용";
				break;
			}
		}
				
		return res;
	}
	
	@Override
	public List<Chatroom_Member> getmemberlistinfo(int chatroom_no){
		
		return chatmapper.memberlistinfo(chatroom_no);

	}
	
	@Override
	public void insertLog(Chatlog chatlog) {
		
		 chatmapper.insertLog(chatlog);
	}
	
	@Override
	public void insertchatfile(Chat_filelist chat_filelist) {
		
		chatmapper.insertchatfile(chat_filelist);
	}
	
	@Override
	public List<Chatlog> getchatlog(int chatroom_no) {
		
		return chatmapper.getchatlog(chatroom_no);
	}
	
	@Override
	public Optional<Chat_filelist> getchatlist_filename(int member_no, int chatroom_no, String chat_filelist_original_filename, String chat_filelist_time) {
		
		return chatmapper.getchatlist_filename(member_no, chatroom_no, chat_filelist_original_filename, chat_filelist_time);
	}
	
	@Override
	public int getTotal_filelist(int chatroom_no) {
		
		return chatmapper.getTotal_filelist(chatroom_no);
	}
	
	@Override
	public List<Chat_filelist> getchatfilelist(int chatroom_no, int startPage, int onePageCnt) {
		
		return chatmapper.getchatfilelist(chatroom_no, startPage, onePageCnt);
	}
	
	@Override
	public void deletefile(String filename) {
		
		chatmapper.deletefile(filename);
	}
	
	@Override
	public void exitmember(int chatroom_no, int member_no) {
		
		chatmapper.exitmember(chatroom_no, member_no);
	}
	
	@Override
	public void deletechatroom(int chatroom_no) {
		
		chatmapper.deletechatroom(chatroom_no);
	}
	
}
