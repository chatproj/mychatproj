package com.example.mychatproj.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mychatproj.model.Chat_filelist;
import com.example.mychatproj.model.Chatlog;
import com.example.mychatproj.model.Chatroom;
import com.example.mychatproj.model.Chatroom_Member;
import com.example.mychatproj.model.Member;
import com.example.mychatproj.service.ChatService;

@Controller
public class ChatController {
	@Autowired
	private ChatService chatservice;
	
	@Autowired
	ServletContext application;
	
	private int getmember_no(String session_id) {
		Optional<Member> member_no = chatservice.getmember_no(session_id); 
		
		return member_no.get().getMember_no();
	}
	
	@RequestMapping("chatList")
	public String chatlist(HttpServletRequest request, Model model) {
		HttpSession session  =  request.getSession();
		String session_id    =  (String) session.getAttribute("session_id");
		int session_no       =  getmember_no(session_id);

		if(session_id != null) {
			List<Chatroom_Member> chatList   =  chatservice.getchatroomlist(session_no);
			HashMap<Integer, String> map     =  new HashMap<Integer, String>();
			
			for(int i = 0; i < chatList.size(); i++) {
				map.put(chatList.get(i).getChatroom_no(), chatList.get(i).getChatroom().getChatroom_name());
			}
	     		model.addAttribute("chatList", map);
			
		}else {
			
			return "redirect:/signin";
		}
		
	    	return "chatList";
	}
	
	@PostMapping("Joinchatroom")
	public String joinchatroom(Chatroom_Member form, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String list = request.getParameter("list");
		String splitlist[] = list.split("[.]");
		
		redirectAttributes.addAttribute("chatroom_no", splitlist[0]);
		return "redirect:/chat";
	}
	
	@PostMapping("invitemember")
	public String invitechat(InviteForm inviteform, HttpServletRequest request) {
		HttpSession session   =   request.getSession();
		String session_id     =   (String) session.getAttribute("session_id");
		int session_no        =   getmember_no(session_id);	
		
		if(session_id != null) {
			// ID 체크
			String id_check = chatservice.invite_id_check(session_id, inviteform.getMember_id());
			
			System.out.println("id check : " + id_check);
			
			if(id_check.equals("존재하지 않는 아이디 입니다.")) {	
				return "redirect:chatList?message=FAILURE_noid";
			}else if(id_check.equals("자기 자신 초대 불가능")) {
				return "redirect:chatList?message=FAILURE_my";				
			}else if(id_check.equals("체크 완료")) {
				// chatroom insert
				Chatroom chatroom                 =  new Chatroom();
				chatroom.setChatroom_name(inviteform.getChatroom_name());
				
				chatservice.insertChatroom(chatroom);
				
				// chatroom insert get chatroom_no
				int chatroom_no = chatroom.getChatroom_no();
				
				// chatroom member insert
				int my_no       =  getmember_no(session_id);
				int invite_no   =  getmember_no(inviteform.getMember_id());
				
				List<Integer> insertchatroom_memberforSize = new ArrayList<Integer>();
				insertchatroom_memberforSize.add(my_no);
				insertchatroom_memberforSize.add(invite_no);
				
				for(int i = 0; i < insertchatroom_memberforSize.size(); i++) {
					Chatroom_Member chatroom_member = new Chatroom_Member();
					chatroom_member.setMember_no(insertchatroom_memberforSize.get(i));
					chatroom_member.setChatroom_no(chatroom_no);
				
					chatservice.insertChatroom_Member(chatroom_member);
				}					
			}
			return "redirect:/chatList";
			
		}else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/chat")
	public String chatpage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam("chatroom_no") int chatroom_no,			
			@RequestParam(value= "msg",     required=false) String msg,
			@RequestParam(value= "nowTimes", required=false) String nowTimes,
			@RequestParam(value= "sockfilename", required=false) String sockfilename,
			@RequestParam(value= "page" , required=false) String page                 ) {
		
		HttpSession session   =   request.getSession();
		String session_id     =   (String) session.getAttribute("session_id");	
		System.out.println(session_id);
		
		if(session_id != null) {
				int session_no             =   getmember_no(session_id);
				
				// 없는 chatroom_no 매개변수에 대한 redirect
				String chatroom_no_check   =   chatservice.getchatroomMemberlistAll(chatroom_no);
				System.out.println("채팅방 여부 체크 : " + chatroom_no_check);
				if(chatroom_no_check.equals("chatroom not found")) {
					return "redirect:/chatList";
				}
				
				// 채팅방 미포함 member 접근 시 redirect
				String chatroom_member_include_check = chatservice.getchatroom_memberInfo(session_no, chatroom_no);
				System.out.println("비정상 접근 체크 : " + chatroom_member_include_check);
				if(chatroom_member_include_check.equals("비정상적인 접근")) {
					return "redirect:/chatList";
				}
				
				// log 조회
				List<Chatlog> chatlog = chatservice.getchatlog(chatroom_no);
				
				model.addAttribute("chatlog", chatlog);
				
				// log insert Text
				Chatlog insertLog = new Chatlog();
				try {
					if(!msg.equals("") && msg != null) {
						insertLog.setMember_no(session_no);
						insertLog.setChatroom_no(chatroom_no);
						insertLog.setChatlog_log(msg);
						insertLog.setChatlog_time(nowTimes);
						insertLog.setChatlog_division("text");
						
						chatservice.insertLog(insertLog);
					}
				}catch(NullPointerException e) {
					
				}
				
				
				// log insert File
				try {
					if(!sockfilename.equals("") && sockfilename != null) {
						SimpleDateFormat simpledateformat   =   new SimpleDateFormat("HH:mm:ss");
						Calendar now                        =   Calendar.getInstance();
						String time                         =   simpledateformat.format(now.getTime());
						
						insertLog.setMember_no(session_no);
						insertLog.setChatroom_no(chatroom_no);
						insertLog.setChatlog_log(sockfilename);
						insertLog.setChatlog_time(time);
						insertLog.setChatlog_division("file");
						
						chatservice.insertLog(insertLog);
					}
				}catch(NullPointerException e) {
					
				}
				
				// 채팅방 참여자 목록
				List<Chatroom_Member> memberlist           =   chatservice.getmemberlistinfo(chatroom_no);
				ArrayList<Chatroom_Member> mychatroominfo  =   new ArrayList<>();
				ArrayList<Chatroom_Member> memberlistAll   =   new ArrayList<>();
				
				System.out.println("my : " + session_no);
				System.out.println("my : " + memberlist.get(0).getMember().getMember_no());
				System.out.println("my : " + memberlist.get(1).getMember().getMember_no());
				
				for(int i = 0; i<memberlist.size(); i++) {
					if(session_no == memberlist.get(i).getMember().getMember_no()) {
						mychatroominfo.add(memberlist.get(i));
					}else {
						memberlistAll.add(memberlist.get(i));
					}
				}
				
				System.out.println(mychatroominfo);
				
				model.addAttribute("mychatroominfo", mychatroominfo);
				model.addAttribute("memberlistAll", memberlistAll);
				
				// 파일 리스트 목록
				int file_listtotalcount = chatservice.getTotal_filelist(chatroom_no);
				System.out.println("file_listtotalcount : " + file_listtotalcount);
				
				int startPage = 0;
				int onePageCnt = 5;
				int count = (int)Math.ceil((double)file_listtotalcount/(double)onePageCnt);
				
				model.addAttribute("file_listtotalcount", file_listtotalcount);  // 파일 토탈
				model.addAttribute("count", count);   // 페이징번호
				
				if(page != null) {
					if(Integer.parseInt(page) > count || Integer.parseInt(page) <= 0) {
						redirectAttributes.addAttribute("chatroom_no", chatroom_no);
						redirectAttributes.addAttribute("page", 1);
						
						return "redirect:chat";
					}else {
						startPage = (Integer.parseInt(page) - 1)*onePageCnt;
						
						List<Chat_filelist> chat_filelist = chatservice.getchatfilelist(chatroom_no, startPage, onePageCnt);
						
						model.addAttribute("chat_filelist", chat_filelist);  // 파일 리스트 (5개씩)
					}
				}else {
					List<Chat_filelist> chat_filelist = chatservice.getchatfilelist(chatroom_no, startPage, onePageCnt);
					
					model.addAttribute("chat_filelist", chat_filelist);  // 파일 리스트 (5개씩)					
				}
			
			return "chat";
		}else {
			
			return "redirect:/";
		}
	}
	
	@PostMapping("/uploadFile")
	public String upload_file(Chat_filelist form,
							  RedirectAttributes redirectAttributes,
			 			      HttpServletRequest request,
						      @RequestParam("fileupload") MultipartFile files,
							  @RequestParam("chatroom_no") int chatroom_no) throws Exception {
		
		System.out.println("ddd" + files.getOriginalFilename());
		
		Chat_filelist fileupload = new Chat_filelist();
		fileupload.setMember_no(form.getMember_no());
		fileupload.setChatroom_no(form.getChatroom_no());
		
		SimpleDateFormat nowTimes   =   new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Calendar now                =   Calendar.getInstance();
		String time                 =   nowTimes.format(now.getTime());
		fileupload.setChat_filelist_time(time);
		
		String originalfilename           =  files.getOriginalFilename();
		String originalfilenameExtension  =  FilenameUtils.getExtension(originalfilename).toLowerCase();
		File destinationfile;
		String destinationfilename;
		String fileurl                    =  "/uploadfile/";
		String savePath                   =  application.getRealPath(fileurl);
		
		do {
			destinationfilename  =  RandomStringUtils.randomAlphabetic(32) + "." + originalfilenameExtension;
			destinationfile      =  new File(savePath, destinationfilename);
		}while(destinationfile.exists());
		
		try {
			files.transferTo(destinationfile);
		}catch(IOException e) {
			
		}
		
		fileupload.setChat_filelist_filename(destinationfilename);
		fileupload.setChat_filelist_original_filename(originalfilename);
		fileupload.setChat_filelist_url(savePath);
		
		chatservice.insertchatfile(fileupload);
		
		redirectAttributes.addAttribute("chatroom_no", form.getChatroom_no());
		redirectAttributes.addAttribute("sockfilename", destinationfilename);
		
		return "redirect:chat";
	}
	
	@PostMapping("download")
    public ResponseEntity<Object> download(FiledownloadForm form, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException, URISyntaxException {
    	int member_no              =  form.getDownload_member_no();
    	int chatroom_no            =  form.getDownload_chatroom_no();
    	String chat_filelist_original_filename   =  form.getDownload_filelist_original_filename();
    	String chat_filelist_time       =  form.getDownload_filelist_time();
		
    	Optional<Chat_filelist> filename = chatservice.getchatlist_filename(member_no, chatroom_no, chat_filelist_original_filename, chat_filelist_time);
    	
    	System.out.println(member_no);
    	System.out.println(chatroom_no);
    	System.out.println(chat_filelist_original_filename);
    	System.out.println(chat_filelist_time);
    	System.out.println("ddd : " + filename.get().getChat_filelist_filename());
    	String fileurl = "/uploadfile/";
    	String path = application.getRealPath(fileurl) + filename.get().getChat_filelist_filename();   
    	
		try {
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
			System.out.println("rrrrr" + resource);
			String origin_filename = form.getDownload_filelist_original_filename();
			
			HttpHeaders headers = new HttpHeaders();
	//		headers.setContentDisposition(ContentDisposition.builder("attachment").filename("dd", StandardCharsets.UTF_8);  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(origin_filename, StandardCharsets.UTF_8).build());			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch(Exception e) {
//			
//			URI redirectUri = new URI("/chat?cnumPK=" + cnumPK + "&message=delfile");
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(redirectUri);
//			return new ResponseEntity<Object>(headers, HttpStatus.SEE_OTHER);
			System.out.println("Failed");
			return new ResponseEntity<Object>(null, HttpStatus.SEE_OTHER);
		}

    }
	
	@PostMapping("filedelete")
	public String filedelete(RedirectAttributes redirectAttributes, Chat_filelist form) {
		
		chatservice.deletefile(form.getChat_filelist_filename());
		
		String url = "/uploadfile";
		String deletefilepath = application.getRealPath(url) + form.getChat_filelist_filename();
		
		File file = new File(deletefilepath);
		file.delete();
		
		redirectAttributes.addAttribute("chatroom_no", form.getChatroom_no());
		return "redirect:chat";
		
	}
	
	@PostMapping("invitemember_chat")
	public String invitemember_chat(InviteForm inviteform, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session   =   request.getSession();
		String session_id     =   (String) session.getAttribute("session_id");
		int chatroom_no   =  inviteform.getChatroom_no();
		
		if(session_id != null) {
			// ID 체크
			String id_check = chatservice.invite_id_check(session_id, inviteform.getMember_id());
			
			System.out.println("id check : " + id_check);
			
			if(id_check.equals("존재하지 않는 아이디 입니다.")) {	
				redirectAttributes.addAttribute("chatroom_no", chatroom_no);
				return "redirect:chat?message=FAILURE_noid";
			}else if(id_check.equals("자기 자신 초대 불가능")) {
				redirectAttributes.addAttribute("chatroom_no", chatroom_no);
				return "redirect:chat?message=FAILURE_my";				
			}else if(id_check.equals("체크 완료")) {
				
				int invite_no     =  getmember_no(inviteform.getMember_id());				
				String member_valid_check = chatservice.member_valid_check(chatroom_no, invite_no);
				
				System.out.println("member valid check : " + member_valid_check);
				System.out.println("m no : " + invite_no);
				System.out.println("c no : " + chatroom_no);
							
				if(member_valid_check.equals("이미 존재하는 멤버입니다.")) {
					redirectAttributes.addAttribute("chatroom_no", chatroom_no);
					return "redirect:chat?message=FAILURE_include";					
				}else if(member_valid_check.equals("체크 완료")) {
				// chatroom member insert	
					Chatroom_Member chatroom_member = new Chatroom_Member();
					chatroom_member.setMember_no(invite_no);
					chatroom_member.setChatroom_no(chatroom_no);
				
					chatservice.insertChatroom_Member(chatroom_member);					
				}
				
			}
			redirectAttributes.addAttribute("chatroom_no", chatroom_no);
			return "redirect:/chat";
			
		}else {
			return "redirect:/";
		}
	}
}
