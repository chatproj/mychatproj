package com.example.mychatproj.model;

public class Chatlog {
	private int chatlog_no;
	private int member_no;
	private int chatroom_no;
	private String chatlog_log;
	private String chatlog_time;
	private String chatlog_division;
	
	public int getChatlog_no() {
		return chatlog_no;
	}
	public void setChatlog_no(int chatlog_no) {
		this.chatlog_no = chatlog_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public int getChatroom_no() {
		return chatroom_no;
	}
	public void setChatroom_no(int chatroom_no) {
		this.chatroom_no = chatroom_no;
	}
	public String getChatlog_log() {
		return chatlog_log;
	}
	public void setChatlog_log(String chatlog_log) {
		this.chatlog_log = chatlog_log;
	}
	public String getChatlog_time() {
		return chatlog_time;
	}
	public void setChatlog_time(String chatlog_time) {
		this.chatlog_time = chatlog_time;
	}
	public String getChatlog_division() {
		return chatlog_division;
	}
	public void setChatlog_division(String chatlog_division) {
		this.chatlog_division = chatlog_division;
	}
	
	public Member member;
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Member_profileimg member_profileimg;
	
	public Member_profileimg getMember_profileimg() {
		return member_profileimg;
	}
	public void setMember_profileimg(Member_profileimg member_profileimg) {
		this.member_profileimg = member_profileimg;
	}
	
	public Chat_filelist chat_filelist;

	public Chat_filelist getChat_filelist() {
		return chat_filelist;
	}
	public void setChat_filelist(Chat_filelist chat_filelist) {
		this.chat_filelist = chat_filelist;
	}
	
	
}
