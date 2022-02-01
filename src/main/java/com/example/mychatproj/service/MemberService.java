package com.example.mychatproj.service;

import java.util.Optional;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;

public interface MemberService {
	// signup
	int insertmember(Member member);
	void insertmemberimg(Member_profileimg member_profileimg);
	
	// signin
	String getMemberLogin(Member member);
//	HashMap<Integer, String> getMemberSession(String member_id);
	Optional<Member> getId_to_memberinfo(String session_id);
	
	// modify
	int modifyMember(Member member);
	void updatememberimg(Member_profileimg member_profileimg);
	
	// findMember
	String getMemberfind(Member member);
	
	// deleteMember
	void deleteMember(String member_id);
	
}


