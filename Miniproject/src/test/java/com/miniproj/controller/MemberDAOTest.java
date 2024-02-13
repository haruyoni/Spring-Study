package com.miniproj.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.miniproj.domain.Member;
import com.miniproj.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MemberDAOTest {
	
	@Inject
	private MemberDAO dao;
//	
	@Test
	public void getDateTimeTest() {
		System.out.println(dao.getDate());
	}
//	
//	@Test
//	public void selectMemberByUserId() {
//		System.out.println(dao.selectMemberByUserId("nohha"));
//	}

//	@Test
//	public void insertMember() {
//		Member member = new Member("nohha", "12314", "nohhaa@dkf", null, 1, 100, null, null);
//		dao.insertMember(member);
//	}
	
//	@Test
//	public void selectAllMembers() {
//		List<Member> lst = dao.selectAllMembers();
//		
//		for(Member m : lst) {
//			System.out.println(m.toString());
//		}
//	}
}