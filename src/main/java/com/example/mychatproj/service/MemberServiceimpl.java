package com.example.mychatproj.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

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
	public void insertmemberimg(Member_profileimg member_profileimg) {
		membermapper.insertmemberimg(member_profileimg);
	}
	
	@Override
	public String getMemberLogin(Member member) {
		String member_id   =  member.getMember_id();
		String member_pwd  =  member.getMember_pwd();
		String res = unmatch(member_id, member_pwd, member);
		
		return res;
	}
	private String unmatch(String member_id, String member_pwd, Member member) {
		Optional<Member> validID = membermapper.getById(member.getMember_id());
		
		String res = null;
		
		try {
			if(member_id.equals(validID.get().getMember_id()) && member_pwd.equals(validID.get().getMember_pwd())) {
				res = "success";
			}else {
				res = "fail";
			}
		}catch(NoSuchElementException e) {
			res = "notfound";
		}
		
		return res;
	}
	
	@Override
	public Optional<Member> getId_to_memberinfo(String session_id) {
		return membermapper.getMemberInfo(session_id);
	}
	
//	@Override
//	public HashMap<Integer, String> getMemberSession(String member_id) {
//		Optional<Member> sessionInfo = membermapper.getById(member_id);
//		HashMap<Integer, String> res = new HashMap<Integer, String>();
//		res.put(sessionInfo.get().getMember_no(), sessionInfo.get().getMember_id());
//		
//		return res;
//	}
	
	@Override
	public int modifyMember(Member member) {
		int memberPK = membermapper.modifyMember(member);
		
		return memberPK;
	}

	@Override
	public void updatememberimg(Member_profileimg member_profileimg) {
		System.out.println("se" + member_profileimg.getMember_no());
		membermapper.updatememberimg(member_profileimg);
	}
	
	@Override
	public String getMemberfind(Member member) {
		String res = findMemberId(member);
		
		return res;
	}
	private String findMemberId(Member member) {
		String res = null;
		
		try {
			Optional<Member> findId = membermapper.findId(member.getMember_name(), member.getMember_email());
			res = findId.get().getMember_id();
		}catch(NoSuchElementException e) {
			res = "notfound";
		}
		
		return res;
	}
}
