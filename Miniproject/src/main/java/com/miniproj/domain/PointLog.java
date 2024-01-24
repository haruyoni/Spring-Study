package com.miniproj.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PointLog {
	private int id;
	private Date when;
	private String why;
	private int howmuch;
	private String who;
}
