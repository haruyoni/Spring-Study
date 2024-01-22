package com.miniproj.etc;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @packageName : com.miniproj.etc
 * @fileName : UploadFileProcess.java
 * @author : hayeong
 * @date : 2024. 1. 22.
 * @description : 파일 업로드 처리
 */
public class UploadFileProcess {
	
	public static void fileUpload(MultipartFile uploadFile, String realPath) throws IOException {
		
		String completePath = makeCalculcatePath(realPath); // 물리적 경로 + \년\월\일
		
		UploadedFile uf = new UploadedFile();
		
		if(uploadFile.getSize() > 0) {
			uf.setNewFileName(getNewFileName(uploadFile, completePath, realPath));
			uf.setOriginalFileName(uploadFile.getOriginalFilename());
			uf.setSize(uploadFile.getSize());
			uf.setExt("."+uploadFile.getContentType().substring(uploadFile.getContentType().indexOf("/")+1));
			
			FileCopyUtils.copy(uploadFile.getBytes(), new File(realPath+uf.getNewFileName())); // 원본 파일 저장
		}
		
		
	}
	
	private static String getNewFileName(MultipartFile uploadFile, String completePath, String realPath) {
		String uuid = UUID.randomUUID().toString();
		
		String newFileName = uuid + "_" + uploadFile.getOriginalFilename();
		
		// 테이블에 저장될 업로드 파일 이름
		System.out.println(completePath.substring(realPath.length()) + File.separator + newFileName);
		return completePath.substring(realPath.length()) + File.separator + newFileName;
	}

	private static String makeCalculcatePath(String realPath) {
		// 현재 날짜 얻어오기
		Calendar cal = Calendar.getInstance();
//		String year = cal.get(Calendar.YEAR)+"";
//		String month = new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
//		
//		String date = cal.get(Calendar.DATE)+"";
//		
//		System.out.println("year:"+year+", month:"+month +", date:"+date);
		
		// realPath\2024\01\22
		String year = File.separator + cal.get(Calendar.YEAR); // \2024
		String month = year + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1); // \2024\01
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE)); // \2024\01\22
		
		makeDirectory(realPath, year, month, date);
		
		return realPath + date;
	}

	private static void makeDirectory(String realPath, String...strings) {
		// 디렉토리 생성
		if(!new File(realPath + strings[strings.length-1]).exists()) {
			for (String path : strings) {
				File tmp = new File(realPath+path);
				if(!tmp.exists()) {
					tmp.mkdir();
				}
			}
		};
	}

	
	
	
	
}
