package com.miniproj.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {
	private int no;
	private String writer;
	private String title;
	private String content;
	private Timestamp postDate;
	private int readCount;
	private int likeCount;
	private int ref;
	private int step;
	private int reforder;
	private String isDelete;
	private String upFileName;
	private String writerImg;
	private boolean likeExist;
}
