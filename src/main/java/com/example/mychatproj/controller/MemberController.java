package com.example.mychatproj.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mychatproj.model.Member;
import com.example.mychatproj.model.Member_profileimg;
import com.example.mychatproj.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberservice;
	
	@Autowired
	ServletContext application;
	
	@RequestMapping("/")
	public String main(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return null;
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
			Member_profileimg memberimg = normalinsertimg(memberPK);
			memberservice.insertmemberimg(memberimg);	
		} else {
			Member_profileimg memberimg = insertimg(memberPK, member_profileimg_form.getMemberimg());
			memberservice.insertmemberimg(memberimg);
		}
			
		return "redirect:/signin";
	}
	private Member_profileimg normalinsertimg(int memberPK) {
		Member_profileimg memberimg = new Member_profileimg();
		
		String originalfilename = "normal_img.png";
		String fileurl = "/memberimg/";
		
		memberimg.setMember_profileimg_filename(originalfilename);
		memberimg.setMember_profileimg_original_filename(originalfilename);
		memberimg.setMember_profileimg_url(application.getRealPath(fileurl));
		memberimg.setMember_no(memberPK);
		
		return memberimg;
	}
	private Member_profileimg insertimg(int memberPK, @RequestPart MultipartFile files) throws Exception{
		Member_profileimg memberimg = new Member_profileimg();
		
		String originalfilename = files.getOriginalFilename();
		String originalfilenameExtension = FilenameUtils.getExtension(originalfilename).toLowerCase();
		File destinationfile;
		String destinationfilename;
		String fileurl = "/memberimg/";
		String savePath = application.getRealPath(fileurl);
		
		do {
			destinationfilename = RandomStringUtils.randomAlphanumeric(32) + "." + originalfilenameExtension;
			destinationfile = new File(savePath, destinationfilename);
		}while(destinationfile.exists());
		
		try {
			files.transferTo(destinationfile);
		} catch(IOException e) {
			
		}
		
		memberimg.setMember_profileimg_filename(destinationfilename);
		memberimg.setMember_profileimg_original_filename(originalfilename);
		memberimg.setMember_profileimg_url(savePath);
		memberimg.setMember_no(memberPK);
		
		return memberimg;
	}
	
	
	
}
