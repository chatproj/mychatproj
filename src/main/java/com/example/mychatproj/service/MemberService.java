package com.example.mychatproj.service;

import java.util.HashMap;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

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
	
}


