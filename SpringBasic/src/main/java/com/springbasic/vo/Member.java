package com.springbasic.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	private String userId;
	private String userPwd;
	private String userEmail;
	private Date registerDate;
	private int userImg;
	private int userPoint;
	private String memberImg;
	private String isAdmin;
	
//	@Override
//	public String toString() {
//		return "Member [userId=" + userId + ", userPwd=" + userPwd + ", userEmail=" + userEmail + ", registerDate="
//				+ registerDate + ", userImg=" + userImg + ", userPoint=" + userPoint + ", memberImg=" + memberImg
//				+ ", isAdmin=" + isAdmin + "]";
//	}
	
	
}
