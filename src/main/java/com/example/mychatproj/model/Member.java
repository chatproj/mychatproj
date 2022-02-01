package com.example.mychatproj.model;

import java.util.List;

public class Member {
	private int member_no;
	private String member_id;
	private String member_pwd;
	private String member_name;
	private String member_email;
	private String member_phone;
	
	private Member_profileimg member_profileimg;
	
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
	public String getMember_pwd() {
		return member_pwd;
	}
	public void setMember_pwd(String member_pwd) {
		this.member_pwd = member_pwd;
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
	
	
	public Member_profileimg getMember_profileimg() {
		return member_profileimg;
	}
	public void setMember_profileimg(int member_no, String member_profileimg_filename, String member_profileimg_original_filename, String member_profileimg_url) {
		this.member_profileimg = member_profileimg;
	}
	

	
}
