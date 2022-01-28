package com.example.mychatproj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RestController;

import com.example.mychatproj.mapper.testmapper;
import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.test;

@RestController
public class testcontroller {
	private testmapper mapper;
	
	private Map<String, Member> member;
	
	public testcontroller(testmapper mapper) {
		this.mapper = mapper;
	}
		
	@PostConstruct
	public void init() {
		List<test> ex = mapper.getTest();
		System.out.println("dddddd : " + ex.get(0).getMember_no());
		System.out.println("dddddd : " + ex.get(0).getMember_id());
		System.out.println("dddddd : " + ex.get(0).getMember_profileimg_no());
		System.out.println("dddddd : " + ex.get(0).getMember_profileimg_original_filename());
	}
	
	
}
