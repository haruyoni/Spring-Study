package com.miniproj.controller.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.miniproj.domain.Board;
import com.miniproj.etc.UploadFileProcess;
import com.miniproj.etc.UploadedFile;
import com.miniproj.service.board.BoardService;

/**
 * @packageName : com.miniproj.controller.board
 * @fileName : BoardController.java
 * @author goott5
 * @date : 2024. 1. 22.
 * @description : 게시판 컨트롤러
 */
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
//	private List<UploadedFile> fileList = new ArrayList<UploadedFile>();
	private List<UploadedFile> fileList;
	
	@Inject
	BoardService bService; // BoardService 객체 주입
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping("listAll")
	public void listAll(Model model) {
		logger.info("listAll 호출됨");
		
		List<Board> lst = bService.getEntireBoard();
		for(Board b : lst) {
			System.out.println(b.toString());
		}
		model.addAttribute("boardList", lst);
		
	}
	
	@RequestMapping("writeBoard")
	public void showWriteBoard() {
		logger.info("writeBoard가 호출됨");
	}
	
	
	/**
	 * @MethodName : uploadFile
	 * @author : hayeong
	 * @date : 2024. 1. 22.
	 * @description : 파일 업로드 
	 * @param uploadFile
	 * @param req 	
	 */
	@RequestMapping(value="uploadFile", method=RequestMethod.POST)
	public @ResponseBody List<UploadedFile> uploadFile(MultipartFile uploadFile, HttpServletRequest req) {
		
		logger.info("파일을 업로드함");
		logger.info("파일 오리지널 이름 : "+uploadFile.getOriginalFilename());
		logger.info("파일 사이즈 : "+uploadFile.getSize());
		logger.info("파일 컨텐트타입 : "+uploadFile.getContentType());
		
		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);
		
		UploadedFile uf = null;
		// 파일 처리
		try {
			uf = UploadFileProcess.fileUpload(uploadFile, realPath);
			
			if(uf!=null) {
				fileList.add(uf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(UploadedFile f : this.fileList) {
			System.out.println("현재 파일 업로드 리스트: "+f.toString());
		}
		
		return this.fileList;
	}
	
	@RequestMapping("remFile")
	public ResponseEntity<String> removeFile(@RequestParam("removeFile") String remFile, HttpServletRequest req) {
		System.out.println(remFile+"을 삭제하자");
		
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		UploadFileProcess.deleteFile(fileList, remFile, realPath);
		
		for(UploadedFile f : this.fileList) {
			if(f.getNewFileName().equals(remFile)) {
				this.fileList.remove(f);
				break;
			}
		}
		
		for(UploadedFile f : this.fileList) {
			System.out.println(f.toString());
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@RequestMapping("remAllFile")
	public ResponseEntity<String> removeAllFile(HttpServletRequest req) {
		System.out.println("파일 전부 삭제하자");
		
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		
		
		UploadFileProcess.deleteAllFile(fileList, realPath);
		
		this.fileList.clear();
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}
