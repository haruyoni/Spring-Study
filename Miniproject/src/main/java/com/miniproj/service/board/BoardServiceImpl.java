package com.miniproj.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.miniproj.domain.Board;
import com.miniproj.persistence.BoardDAO;

@Repository
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO dao;
	
	@Override
	public List<Board> getEntireBoard() {
	
		List<Board> lst = dao.selectAllBoard();
		return lst;
	}

}
