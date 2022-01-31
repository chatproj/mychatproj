package com.example.mychatproj.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;

@Mapper
public interface MemberMapper {
	// Insert Member
	int insertmember(@Param("member") Member member);
	// valid id, email
	Optional<Member> getById(@Param("member_id") String member_id);
	Optional<Member> getByEmail(@Param("member_email") String member_email);
	// Insert Memberimg
	void insertmemberimg(@Param("member_profileimg") Member_profileimg memberimg);
}
