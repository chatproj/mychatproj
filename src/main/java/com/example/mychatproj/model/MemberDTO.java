package com.example.mychatproj.model;

import java.util.List;

public class MemberDTO {
	private int member_no;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_email;
	private String member_phone;
	
	private Member_profileimgDTO member_profileimgdto;
	private ChatroomInMemberDTO chatroominmemberdto;
	
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_phone() {
		return member_phone;
	}
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	public Member_profileimgDTO getMember_profileimgdto() {
		return member_profileimgdto;
	}
	public void setMember_profileimgdto(Member_profileimgDTO member_profileimgdto) {
		this.member_profileimgdto = member_profileimgdto;
	}
	public ChatroomInMemberDTO getChatroominmemberdto() {
		return chatroominmemberdto;
	}
	public void setChatroominmemberdto(ChatroomInMemberDTO chatroominmemberdto) {
		this.chatroominmemberdto = chatroominmemberdto;
	}
	
}
