package com.miniproj.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadCountProcess {
	private int no;
	private String ipAddr;
	private int boardNo;
	private Timestamp readTime;
}
