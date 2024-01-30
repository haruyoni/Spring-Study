package com.miniproj.service.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproj.domain.PointLog;
import com.miniproj.domain.Reply;
import com.miniproj.etc.PagingInfo;
import com.miniproj.persistence.MemberDAO;
import com.miniproj.persistence.PointLogDAO;
import com.miniproj.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	ReplyDAO rDao;

	@Inject
	MemberDAO mDao;

	@Inject
	PointLogDAO plDao;

	@Override
	public List<Reply> getAllReplies(int boardNo) throws Exception {
		return rDao.selectAllReplies(boardNo);
	}

	@Override
	public boolean saveReply(Reply newReply) throws Exception {
		boolean result = false;
		if (rDao.insertNewReply(newReply) == 1) {
			// 3) member 테이블에 userpoint를 update
			if (mDao.updateUserPoint("답글작성", newReply.getReplier()) == 1) {
				// 4) pointlog 테이블에 insert
				if (plDao.insertPointLog(new PointLog(-1, null, "답글작성", 1, newReply.getReplier())) == 1) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllReplies(int boardNo, int pageNo) throws Exception {
		PagingInfo pi = getPagingInfo(boardNo, pageNo);
		System.out.println(pi.toString());
		
		List<Reply> lst = rDao.selectAllReplies(boardNo, pi);
		
		Map<String, Object> result = new HashMap();
		
		result.put("replyList", lst);
		result.put("pagingInfo", pi);
		
		return result;
	}

	private PagingInfo getPagingInfo(int boardNo, int pageNo) throws Exception {
		PagingInfo result = new PagingInfo();

		// pageNo 세팅
		result.setPageNo(pageNo);

		// 총 댓글수 세팅
		result.setTotalPostCnt(rDao.selectTotalRepliesCnt(boardNo));

		// 총 페이지수 세팅
		result.setTotalPageCnt(result.getTotalPostCnt(), result.getViewPostCntPerPage());

		// 보여주기 시작할 row index 번호 구하기
		result.setStartRowIndex();

		// 전체 페이징 블럭 개수
		result.setTotalPagingBlockCnt();

		// 현재 페이지가 속한 페이징 블럭 번호
		result.setPageBlockOfCurrentPage();

		// 현재 페이징 블럭에서의 출력 시작 페이지 번호
		result.setStartNumOfCurrentPagingBlock();

		// 현재 페이징 블럭에서의 출력 끝 페이지 번호
		result.setEndNumOfCurrentPagingBlock();

		return result;
	}
}
