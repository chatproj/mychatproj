package com.example.mychatproj.service;

import java.util.List;

import com.example.mychatproj.model.MemberDTO;

public interface TestService {	
		List<MemberDTO> getMember(int exam, String exam1);
		List<MemberDTO> getjoin();
		List<MemberDTO> gettestjoin();
	}
