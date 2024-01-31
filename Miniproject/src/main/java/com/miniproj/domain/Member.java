package com.miniproj.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
}
