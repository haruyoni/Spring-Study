package com.miniproj.domain;

import lombok.Data;

@Data
public class UploadedFile {
	private String originFileName;
	private String ext;
	private String newFileName;
	private long size;
	private int boardNo;
	private String base64String;
	private String thumbFileName;
}
