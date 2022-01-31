package com.example.mychatproj.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.mychatproj.mapper.MemberMapper;
import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;

@Service
public class MemberServiceimpl implements MemberService{
	
	@Autowired
	private MemberMapper membermapper;

	@Override
	public int insertmember(Member member) {
		validateDuplicateMember(member);
		int memberPK = membermapper.insertmember(member);
		
		return memberPK;
	}	
	private String validateDuplicateMember(Member member) {
		String res = null;
		
		membermapper.getById(member.getMember_id())
		.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 아이디입니다.");
		});
		res = "이미 존재하는 아이디입니다.";
		
		membermapper.getByEmail(member.getMember_email())
		.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		});
		res = "이미 존재하는 이메일입니다.";
		
		return res;
	}
	
	@Override
	public void insertmemberimg(Member_profileimg member_profileimg){
		membermapper.insertmemberimg(member_profileimg);
	}
}
