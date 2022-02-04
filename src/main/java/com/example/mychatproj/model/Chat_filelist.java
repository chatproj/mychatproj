package com.example.mychatproj.model;

import org.springframework.web.multipart.MultipartFile;

public class Chat_filelist {
	private int chat_filelist_no;
	private int chatroom_no;
	private int member_no;
	private String chat_filelist_filename;
	private String chat_filelist_original_filename;
	private String chat_filelist_time;
	private String chat_filelist_url;
	
	public int getChat_filelist_no() {
		return chat_filelist_no;
	}
	public void setChat_filelist_no(int chat_filelist_no) {
		this.chat_filelist_no = chat_filelist_no;
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
	public String getChat_filelist_filename() {
		return chat_filelist_filename;
	}
	public void setChat_filelist_filename(String chat_filelist_filename) {
		this.chat_filelist_filename = chat_filelist_filename;
	}
	public String getChat_filelist_original_filename() {
		return chat_filelist_original_filename;
	}
	public void setChat_filelist_original_filename(String chat_filelist_original_filename) {
		this.chat_filelist_original_filename = chat_filelist_original_filename;
	}
	public String getChat_filelist_time() {
		return chat_filelist_time;
	}
	public void setChat_filelist_time(String chat_filelist_time) {
		this.chat_filelist_time = chat_filelist_time;
	}
	public String getChat_filelist_url() {
		return chat_filelist_url;
	}
	public void setChat_filelist_url(String chat_filelist_url) {
		this.chat_filelist_url = chat_filelist_url;
	}
	
	MultipartFile fileupload;

	public MultipartFile getFileupload() {
		return fileupload;
	}
	public void setFileupload(MultipartFile fileupload) {
		this.fileupload = fileupload;
	}
	
	Member member;

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
	
}
