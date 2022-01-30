package com.example.mychatproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mychatproj.mapper.TestMapper;
import com.example.mychatproj.model.MemberDTO;

@Service
public class TestServiceimpl implements TestService{
	
	@Autowired
	private TestMapper testmapper;

	@Override
	public List<MemberDTO> getMember(int exam, String exam1) {

		return testmapper.getMember(exam, exam1);
	}
	
	@Override
	public List<MemberDTO> getjoin() {
		
		return testmapper.getjoin();
	}
	
	@Override
	public List<MemberDTO> gettestjoin() {
		
		return testmapper.gettestjoin();
	}

}
