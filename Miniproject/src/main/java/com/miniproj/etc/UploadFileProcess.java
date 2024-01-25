package com.miniproj.etc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.miniproj.domain.UploadedFile;

/**
 * @packageName : com.miniproj.etc
 * @fileName : UploadFileProcess.java
 * @author : hayeong
 * @date : 2024. 1. 22.
 * @description : 파일 업로드 처리
 */
public class UploadFileProcess {
	
	public static UploadedFile fileUpload(MultipartFile uploadFile, String realPath) throws IOException {
		
		String completePath = makeCalculcatePath(realPath); // 물리적 경로 + \년\월\일
		
		UploadedFile uf = new UploadedFile();
		
		if(uploadFile.getSize() > 0) {
			uf.setNewFileName(getNewFileName(uploadFile, completePath, realPath));
			uf.setOriginFileName(uploadFile.getOriginalFilename());
			uf.setSize(uploadFile.getSize());
			uf.setExt("."+uploadFile.getContentType().substring(uploadFile.getContentType().indexOf("/")+1));
			
			FileCopyUtils.copy(uploadFile.getBytes(), new File(realPath+uf.getNewFileName())); // 원본 파일 저장
			
			System.out.println("컨텐트타입 테스트 : "+uploadFile.getContentType());
			if(ImgMimeType.contentTypeIsImage(uploadFile.getContentType())) {
				// 파일이 이미지인 경우 -> 썸네일 생성 & 저장
				System.out.println("이미지입니다");
				
				makeThumbnailImage(uf, completePath, realPath);
			} else {
				System.out.println("이미지가 아닙니다");
			}
		}
		
		return uf;
		
	}
	
	private static void makeThumbnailImage(UploadedFile uf, String completePath, String realPath) throws IOException {
		// 저장된 원본 파일을 읽어서 스케일 다운하여 썸네일 만들기
		System.out.println("thumbnail 이미지 만들기 : "+ realPath+uf.getNewFileName());
		
		BufferedImage originImg = ImageIO.read(new File(realPath+uf.getNewFileName())); //원본파일 읽기
		
		BufferedImage thumbnailImg = Scalr.resize(originImg, Mode.FIT_TO_HEIGHT, 50);
		
		// 썸네일 저장
		String thumbImgName = "thumb_"+uf.getOriginFileName();
		String ext =  uf.getOriginFileName().substring(uf.getOriginFileName().lastIndexOf(".") + 1);
		File saveTarget = new File(completePath+File.separator + thumbImgName);
		
		if(ImageIO.write(thumbnailImg, ext,  saveTarget) ) { // 썸네일 저장되었다면
	         uf.setThumbFileName(completePath.substring(realPath.length()) + File.separator + thumbImgName);
	      } else {
	         System.out.println("썸네일 이미지 저장 실패.");
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

	public static void deleteFile(List<UploadedFile> fileList, String remFile, String realPath) {
		// (x) 클릭 -> 삭제
		
		for(UploadedFile uf : fileList) {
			if(remFile.equals(uf.getNewFileName())) {
				
				File delFile = new File(realPath + uf.getNewFileName());
				if(delFile.exists()) {
					delFile.delete();
					System.out.println("파일 삭제완료");
				}
				
				if(uf.getThumbFileName()!=null) {
					File thumbFile = new File(realPath + uf.getThumbFileName());
					
					if(thumbFile.exists()) {
						thumbFile.delete();
						System.out.println("썸네일 삭제완료");
					}
				}
			}
		}
		
	}
	
	public static void deleteAllFile(List<UploadedFile> fileList, String realPath) {
		for(UploadedFile uf : fileList) {
			
			File delFile = new File(realPath+uf.getNewFileName());
			
			if(delFile.exists()) {
				delFile.delete();
			}
			
			if(uf.getThumbFileName() != null) {
				File thumbFile = new File(realPath + uf.getThumbFileName());
				
				if(thumbFile.exists()) {
					thumbFile.delete();
				}
			}
		}
	}

	
	
	
	
}
