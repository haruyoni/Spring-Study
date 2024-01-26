package com.miniproj.controller.reply;

import java.util.List;

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
import com.miniproj.service.reply.ReplyService;

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	
	@Inject
	private ReplyService rService;
	
	
	@ResponseBody
	@RequestMapping(value="all/{boardNo}", method=RequestMethod.GET)
	public List<Reply> getAllReplies(@PathVariable("boardNo") int boardNo) {
		
		System.out.println(boardNo+"번 글의 댓글을 가져오자");
		List<Reply> lst = null;
		try {
			lst = rService.getAllReplies(boardNo);
			for(Reply r : lst) {
				System.out.println(r.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
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
