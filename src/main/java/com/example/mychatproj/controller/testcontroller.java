package com.example.mychatproj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.mychatproj.mapper.TestMapper;
import com.example.mychatproj.model.MemberDTO;
import com.example.mychatproj.model.test;
import com.example.mychatproj.service.TestService;

@RestController
public class testcontroller {
	
	@Autowired
	private TestService testservice;
	
	@PostConstruct
	public void init() {
		
		int exam = 1;
		String exam1 = "w";
		
		List<MemberDTO> ex = testservice.getMember(exam, exam1);

		
		System.out.println("dddddd : " + ex.get(0).getMember_no());
		System.out.println("dddddd : " + ex.get(0).getMember_id());
		
		List<MemberDTO> ex1 = testservice.getjoin();
		System.out.println("fffffffffff" + ex1.get(0).getMember_no());
		System.out.println("fffffffffff" + ex1.get(0).getMember_id());
		System.out.println("fffffffffff" + ex1.get(0).getMember_profileimgdto());
		System.out.println("fffffffffff" + ex1.get(0).getMember_profileimgdto().getMember_profileimg_original_filename());
		System.out.println("fffffffffff" + ex1.get(0).getMember_profileimgdto().getMember_profileimg_no());
		System.out.println("fffffffffff" + ex1.get(0).getMember_profileimgdto().getMember_profileimg_filename());
		
		List<MemberDTO> ex2 = testservice.gettestjoin();
		System.out.println("dddddddddddd" + ex2.get(0).getChatroominmemberdto().getChatroomINmember_no());
	}
	
	
}
