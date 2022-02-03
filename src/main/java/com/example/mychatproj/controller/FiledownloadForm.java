package com.example.mychatproj.controller;

public class FiledownloadForm {
	private int download_member_no;
	private int download_chatroom_no;
	private String download_filelist_time;
	private String download_filelist_original_filename;
	
	public int getDownload_member_no() {
		return download_member_no;
	}
	public void setDownload_member_no(int download_member_no) {
		this.download_member_no = download_member_no;
	}
	public int getDownload_chatroom_no() {
		return download_chatroom_no;
	}
	public void setDownload_chatroom_no(int download_chatroom_no) {
		this.download_chatroom_no = download_chatroom_no;
	}
	public String getDownload_filelist_time() {
		return download_filelist_time;
	}
	public void setDownload_filelist_time(String download_filelist_time) {
		this.download_filelist_time = download_filelist_time;
	}
	public String getDownload_filelist_original_filename() {
		return download_filelist_original_filename;
	}
	public void setDownload_filelist_original_filename(String download_filelist_original_filename) {
		this.download_filelist_original_filename = download_filelist_original_filename;
	}
	
	
}
