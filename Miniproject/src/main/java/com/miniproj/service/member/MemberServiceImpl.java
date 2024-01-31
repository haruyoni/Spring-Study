package com.miniproj.service.member;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
import com.miniproj.domain.PointLog;
import com.miniproj.persistence.MemberDAO;
import com.miniproj.persistence.PointLogDAO;


@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO mDao;
	
	@Inject
	PointLogDAO pDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Member login(LoginDTO tmpMember) throws Exception {
		System.out.println("MemberServiceImpl : 로그인 처리하자 "+tmpMember.toString());
		
		// 1) 로그인 한다. (member 테이블에서 userId, userPwd 일치 여부 확인)
		Member loginMember = mDao.login(tmpMember);
		
		// 2) 로그인 성공하면 포인트 부여
		if(loginMember != null) {
			System.out.println(loginMember.toString());
			if(mDao.updateUserPoint("로그인", loginMember.getUserId())==1) {
				// 3) pointlog테이블에 저장
				pDao.insertPointLog(new PointLog(-1, null, "로그인", 5, loginMember.getUserId()));
			}
		}
		
		return loginMember;
	}

}
