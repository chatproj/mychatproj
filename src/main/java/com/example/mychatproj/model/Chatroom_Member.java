package com.example.mychatproj.model;

public class Chatroom_Member {
	private int chatroom_member_no;
	private int chatroom_no;
	private int member_no;
	
	public int getChatroom_member_no() {
		return chatroom_member_no;
	}
	public void setChatroom_member_no(int chatroom_member_no) {
		this.chatroom_member_no = chatroom_member_no;
	}
	public int getChatroom_no() {
		return chatroom_no;
	}
	public void setChatroom_no(int chatroom_no) {
		this.chatroom_no = chatroom_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	
	private Chatroom chatroom;

	public Chatroom getChatroom() {
		return chatroom;
	}
	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}
	
	
}