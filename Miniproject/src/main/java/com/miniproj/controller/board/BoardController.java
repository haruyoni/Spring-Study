package com.miniproj.controller.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.miniproj.domain.SearchCriteria;
import com.miniproj.domain.UploadedFile;
import com.miniproj.etc.GetUserIPAddr;
import com.miniproj.etc.PagingInfo;
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


	private List<UploadedFile> fileList = new ArrayList<UploadedFile>();
//	private List<UploadedFile> fileList;

	@Inject
	BoardService bService; // BoardService 객체 주입

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	
	// 게시판 목록 조회 (paging + 검색)
	@RequestMapping("listAll")
	public void listAll(Model model, 
			@RequestParam(value="pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value="searchType", defaultValue = "") String searchType,
			@RequestParam(value="searchWord", defaultValue = "") String searchWord
			) {
		logger.info(pageNo+"페이지 "+searchType+" 타입 "+ searchWord+" 검색어");

		SearchCriteria sc = new SearchCriteria(searchWord, searchType);
		Map<String, Object> map;
		try {
			map = bService.getEntireBoard(pageNo, sc);
			model.addAttribute("boardList", (List<Board>)map.get("boardList"));
			model.addAttribute("pagingInfo", (PagingInfo)map.get("pagingInfo"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("writeBoard")
	   public void showWriteBoard(HttpSession ses) {
	      logger.info("writeBoard가 호출됨.");
	      
	      String uuid = UUID.randomUUID().toString();
	      	
	      ses.setAttribute("csrfToken", uuid); // 세션에 바인딩
	   }
	   
	   @RequestMapping(value = "writeBoard", method = RequestMethod.POST)
	   public String writeBoard( Board newBoard, @RequestParam("csrfToken") String inputcsrf, HttpSession ses) {
	      logger.info( "게시판 글 작성 : " + newBoard.toString() );
	      logger.info( "csrf : " + inputcsrf );
	      
	      String redirectPage = "";
	      
	      if( ((String)ses.getAttribute("csrfToken")).equals(inputcsrf) ) {
	         // csrfToken이 같은 경우에만 게시글을 저장
	         try {
	            bService.saveNewBoard(newBoard, fileList);
	            redirectPage = "listAll";
	         } catch (Exception e) {
	            e.printStackTrace();
	            redirectPage = "listAll?status=fail";
	         }  
	      }

	      return "redirect:"+redirectPage;
	   }

	/**
	 * @MethodName : uploadFile
	 * @author : hayeong
	 * @date : 2024. 1. 22.
	 * @description : 파일 업로드
	 * @param uploadFile
	 * @param req
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public @ResponseBody List<UploadedFile> uploadFile(MultipartFile uploadFile, HttpServletRequest req) {

		logger.info("파일을 업로드함");
		logger.info("파일 오리지널 이름 : " + uploadFile.getOriginalFilename());
		logger.info("파일 사이즈 : " + uploadFile.getSize());
		logger.info("파일 컨텐트타입 : " + uploadFile.getContentType());

		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);

		UploadedFile uf = null;
		// 파일 처리
		try {
			uf = UploadFileProcess.fileUpload(uploadFile, realPath);

			if (uf != null) {
				fileList.add(uf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (UploadedFile f : this.fileList) {
			System.out.println("현재 파일 업로드 리스트: " + f.toString());
		}

		return this.fileList;
	}

	@RequestMapping("remFile")
	public ResponseEntity<String> removeFile(@RequestParam("removeFile") String remFile, HttpServletRequest req) {
		System.out.println(remFile + "을 삭제하자");

		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		UploadFileProcess.deleteFile(fileList, remFile, realPath);

		for (UploadedFile f : this.fileList) {
			if (f.getNewFileName().equals(remFile)) {
				this.fileList.remove(f);
				break;
			}
		}

		for (UploadedFile f : this.fileList) {
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
	
	@RequestMapping("viewBoard")
	public void viewBoard(@RequestParam("no") int no, HttpServletRequest request, Model model) throws Exception {
		logger.info(no + "번 글을 상세조회하자!");
		
		Map<String, Object> result = bService.getBoardByNo(no, GetUserIPAddr.getIp(request));
		model.addAttribute("board", (Board)result.get("board"));
		model.addAttribute("upFileList", (List<UploadedFile>)result.get("upFileList"));
	}

}
