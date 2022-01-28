package com.example.mychatproj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.test;

@Mapper
public interface testmapper {
	@Select("select * from member")
	List<Member> getMember();
	
	@Select("select m.member_no, member_id, member_profileimg_no, member_profileimg_original_filename \r\n"
			+ "from mychatproj.member as m inner join mychatproj.member_profileimg as n  \r\n"
			+ "on m.member_no = n.member_no")
	List<test> getTest();

}
