package com.miniproj.controller.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.miniproj.domain.Reply;
import com.miniproj.etc.PagingInfo;
import com.miniproj.service.reply.ReplyService;

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	
	@Inject
	private ReplyService rService;
	
	
//	@ResponseBody
	@RequestMapping(value="all/{boardNo}/{pageNo}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllReplies(@PathVariable("boardNo") int boardNo,
			@PathVariable("pageNo") int pageNo) {
		
		System.out.println(boardNo+"번 글의"+pageNo +"번째 페이지 댓글을 가져오자");
		ResponseEntity<Map<String, Object>> result = null;
		try {
			PagingInfo pi = new PagingInfo();
			List<Reply> lst = rService.getAllReplies(boardNo);
			
			Map<String, Object> map = rService.getAllReplies(boardNo, pageNo);
			
			result = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}
	
	
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> saveReply(@RequestBody Reply newReply) {
		System.out.println("댓글 등록");
		System.out.println(newReply.toString());
		
		ResponseEntity<String> result = null;
		
		try {
			if(rService.saveReply(newReply)) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		
		return result;
	}

}
