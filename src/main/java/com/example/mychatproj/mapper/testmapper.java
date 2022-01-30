package com.example.mychatproj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.mychatproj.model.MemberDTO;
import com.example.mychatproj.model.test;

@Mapper
public interface TestMapper {
	List<MemberDTO> getMember(@Param("exam") int exam, @Param("exam1") String exam1);
	List<MemberDTO> getjoin();
	List<MemberDTO> gettestjoin();
}
