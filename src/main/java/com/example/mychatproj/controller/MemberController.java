package com.example.mychatproj.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;
import com.example.mychatproj.service.ChatService;
import com.example.mychatproj.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberservice;
	@Autowired
	private ChatService chatservice;
	
	@Autowired
	ServletContext application;
	
//	public int getSession_no(HashMap<Integer, String> sessionInfo) {
//		Integer session_no = null;
//		try {
//			for(Map.Entry<Integer, String> entry : sessionInfo.entrySet()) {
//				session_no = entry.getKey();
//			}
//		}catch(NullPointerException e) {
//			return session_no;
//		}
//		
//		return session_no;
//	}
//	
//	public String getSession_id(HashMap<Integer, String> sessionInfo) {
//		String session_id = null;
//		try {
//			for(Map.Entry<Integer, String> entry : sessionInfo.entrySet()) {
//				session_id = entry.getValue();
//			}
//		}catch(NullPointerException e) {
//			return session_id;
//		}
//		return session_id;
//	}
	private int getmember_no(String session_id) {
		Optional<Member> member_no = chatservice.getmember_no(session_id); 
		
		return member_no.get().getMember_no();
	}
	
	
	private Member_profileimg normalimglogic(int memberPK) {
		Member_profileimg memberimg = new Member_profileimg();
		
		String originalfilename   = "normal_img.png";
		String fileurl            = "/memberimg/";
		
		memberimg.setMember_profileimg_filename            (originalfilename);
		memberimg.setMember_profileimg_original_filename   (originalfilename);
		memberimg.setMember_profileimg_url                 (application.getRealPath(fileurl));
		memberimg.setMember_no                             (memberPK);
		
		return memberimg;
	}
	private Member_profileimg imglogic(int memberPK, @RequestPart MultipartFile files) throws Exception{
		Member_profileimg memberimg = new Member_profileimg();
		
		String originalfilename            = files.getOriginalFilename();
		String originalfilenameExtension   = FilenameUtils.getExtension(originalfilename).toLowerCase();
		File destinationfile;
		String destinationfilename;
		String fileurl                     = "/memberimg/";
		String savePath                    = application.getRealPath(fileurl);
		
		do {
			destinationfilename  = RandomStringUtils.randomAlphanumeric(32) + "." + originalfilenameExtension;
			destinationfile      = new File(savePath, destinationfilename);
		}while(destinationfile.exists());
		
		try {
			files.transferTo(destinationfile);
		} catch(IOException e) {
			
		}
		
		memberimg.setMember_profileimg_filename           (destinationfilename);
		memberimg.setMember_profileimg_original_filename  (originalfilename);
		memberimg.setMember_profileimg_url                (savePath);
		memberimg.setMember_no                            (memberPK);
		
		return memberimg;
	}
	
	@RequestMapping("/")
	public String main(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_id = (String) session.getAttribute("session_id");
		
		if(session_id != null) {
			return "redirect:/chatList";
		}else {
			return "redirect:/signin";
		}
	}
	
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String create_member(Member member_form, Member_profileimg member_profileimg_form, Model model, RedirectAttributes redirectAttributes) throws Exception {
		Member member = new Member();
		member.setMember_id    (member_form.getMember_id());
		member.setMember_pwd   (member_form.getMember_pwd());
		member.setMember_name  (member_form.getMember_name());
		member.setMember_email (member_form.getMember_email());
		member.setMember_phone (member_form.getMember_phone());
		
		try {
			memberservice.insertmember(member);
		}catch(IllegalStateException e) {
			if(e.getMessage().equals("이미 존재하는 아이디입니다.")) {
				return "redirect:/signup?message=duplicateId";
			}else if(e.getMessage().equals("이미 존재하는 이메일입니다.")) {
				return "redirect:/signup?message=duplicateEmail";
			}
		}
		
		// insert after memberPK
		int memberPK = member.getMember_no();
		
		if(member_profileimg_form.getMemberimg().getOriginalFilename().equals("")) {
			Member_profileimg memberimg = normalimglogic(memberPK);
			memberservice.insertmemberimg(memberimg);	
		} else {
			Member_profileimg memberimg = imglogic(memberPK, member_profileimg_form.getMemberimg());
			memberservice.insertmemberimg(memberimg);
		}
			
		return "redirect:/signin";
	}
	
	@RequestMapping("/signin")
	public String signin() {
		return "signin";
	}
	
	@PostMapping("/signin")
	public String signin_member(Member member_form, HttpServletRequest request) {
		Member member = new Member();
		member.setMember_id   (member_form.getMember_id());
		member.setMember_pwd  (member_form.getMember_pwd());
		
		String res = memberservice.getMemberLogin(member);
		if(res.equals("fail")) {
			return "redirect:/signin?message=FAILURE_fail";
		}else if(res.equals("notfound")) {
			return "redirect:/signin?message=FAILURE_notfound";
		}else {
//			HashMap<Integer, String> sessionInfo = memberservice.getMemberSession(form.getMember_id());	
//			System.out.println("ddd" + sessionInfo.keySet());
//			System.out.println("fff" + sessionInfo.get(sessionInfo));
			
			
			String session_id    = member_form.getMember_id();		
			HttpSession session  = request.getSession();
			session.setAttribute ("session_id", session_id);
			
			return "redirect:/chatList";
		}
		
	}
	
	@PostMapping("/signout")
	public String signout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/modifymember")
	public String modify(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_id   = (String) session.getAttribute("session_id");
		
		if(session_id != null) {
			Optional<Member> memberinfo = memberservice.getId_to_memberinfo(session_id);
			model.addAttribute("member_no",                           memberinfo.get().getMember_no());
			model.addAttribute("member_id",                           memberinfo.get().getMember_id());
			model.addAttribute("member_name",                         memberinfo.get().getMember_name());
			model.addAttribute("member_email",                        memberinfo.get().getMember_email());
			model.addAttribute("member_phone",                        memberinfo.get().getMember_phone());
			model.addAttribute("member_profileimg_filename",          memberinfo.get().getMember_profileimg().getMember_profileimg_filename());
			model.addAttribute("member_profileimg_original_filename", memberinfo.get().getMember_profileimg().getMember_profileimg_original_filename());
					
			return "modifymember";
		}else {
			return "redirect:/signin";
		}	
	}
	@PostMapping("/modify")
	public String modifyMember(Member member_form, Member_profileimg member_profileimg_form, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();	
		
		Member member = new Member();
		member.setMember_id          (member_form.getMember_id());
		member.setMember_pwd         (member_form.getMember_pwd());
		member.setMember_name        (member_form.getMember_name());
		member.setMember_email       (member_form.getMember_email());
		member.setMember_phone       (member_form.getMember_phone());
		
		memberservice.modifyMember(member);
		
		int memberPK = member_form.getMember_no();

		if(member_profileimg_form.getMemberimg().getOriginalFilename().equals("")) {
			Member_profileimg memberimg = normalimglogic(memberPK);
			memberservice.updatememberimg(memberimg);	
		} else {
			Member_profileimg memberimg = imglogic(memberPK, member_profileimg_form.getMemberimg());
			memberservice.updatememberimg(memberimg);
		}
		
		session.invalidate();
		
		return "redirect:/signin";
	}
	
	@RequestMapping("findId")
	public String findId() {
		
		return "findId";
	}
	@PostMapping("/findId")
	public String find_memberId(Member member_form, RedirectAttributes redirectAttributes) {
		Member member = new Member();
		member.setMember_name   (member_form.getMember_name());
		member.setMember_email  (member_form.getMember_email());
		
		String findmsg = null;
		String res = memberservice.getMemberfind(member);
		if(res.equals("notfound")) {
			findmsg = "존재하지 않는 ID 입니다.";
		}else {
			findmsg = res;
		}
		
		redirectAttributes.addAttribute("findmsg", findmsg);
		
		return "redirect:/memberprocess";
	}
	@RequestMapping("memberprocess")
	public String memberprocess() {
		
		return "memberprocess";
	}
	
	@PostMapping("deletemember")
	public String deletemember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_id   = (String) session.getAttribute("session_id");
		int session_no       =  getmember_no(session_id);
		
		Optional<Member> member_profile_img = memberservice.getId_to_memberinfo(session_id);
		
		String url = "/memberimg/";
		String deletefilepath = application.getRealPath(url) + member_profile_img.get().getMember_profileimg().getMember_profileimg_filename();
		
		File file = new File(deletefilepath);
		file.delete();
		
		memberservice.deleteMember(session_id);
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	
}
