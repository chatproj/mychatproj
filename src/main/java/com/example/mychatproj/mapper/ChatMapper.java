package com.example.mychatproj.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;

@Mapper
public interface ChatMapper {
	Optional<Member> getSession_no(@Param("member_id") String member_id);
	
	List<Chatroom_Member> getchatlist(@Param("member_no") int member_no);
}
