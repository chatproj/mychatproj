package com.example.mychatproj.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;

public interface MemberService {
	int insertmember(Member member);
	void insertmemberimg(Member_profileimg member_profileimg);
	
	String getMemberLogin(Member member);
	int getMemberSession(String member_id);
	
}


