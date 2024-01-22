package com.miniproj.controller.board;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.miniproj.domain.Board;
import com.miniproj.etc.UploadFileProcess;
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
	public void uploadFile(MultipartFile uploadFile, HttpServletRequest req) {
		logger.info("파일을 업로드함");
		logger.info("파일 오리지널 이름 : "+uploadFile.getOriginalFilename());
		logger.info("파일 사이즈 : "+uploadFile.getSize());
		logger.info("파일 컨텐트타입 : "+uploadFile.getContentType());
		
		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);
		
		// 파일 처리
		try {
			UploadFileProcess.fileUpload(uploadFile, realPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
