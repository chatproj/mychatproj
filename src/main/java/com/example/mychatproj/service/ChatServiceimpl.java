package com.example.mychatproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mychatproj.mapper.ChatMapper;
import com.example.mychatproj.mapper.MemberMapper;

@Service
public class ChatServiceimpl implements ChatService{
	@Autowired
	private ChatMapper chatmapper;
	
}
