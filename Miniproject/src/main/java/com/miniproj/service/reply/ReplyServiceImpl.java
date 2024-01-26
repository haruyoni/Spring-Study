package com.miniproj.service.reply;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproj.domain.PointLog;
import com.miniproj.domain.Reply;
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
		if(rDao.insertNewReply(newReply) == 1) {
			// 3) member 테이블에 userpoint를 update
			if(mDao.updateUserPoint("답글작성", newReply.getReplier()) == 1) {
				// 4) pointlog 테이블에 insert
				if(plDao.insertPointLog(new PointLog(-1, null, "답글작성", 1, newReply.getReplier())) == 1) {
					result = true;
				}
			}
			
		}
		return result;
	}
}
