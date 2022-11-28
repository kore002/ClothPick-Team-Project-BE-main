package com.example.demo.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.vo.BoardCommentLayer1InfoVo;
import com.example.demo.board.vo.BoardCommentVo;

@Mapper
public interface BoardCommentDao {
	// 댓글 작성
	public int BoardCommentPostDao(Map<String, String> map);
	
	// 댓글 수정
	public int BoardCommentPutDao(Map<String, String> map);
	
	// 댓글 삭제 - commentLayer(1,2)
	public int BoardCommentDeleteDao(Map<String, String> map);
	// 댓글, 대댓글 모두 삭제
	public int BoardCommentDeletelayer2Dao(Map<String, String> map);
	
	// 댓글 ID 검색
	public BoardCommentVo BoardCommentIdSearchDao(Map<String, String> map);
	
	//boardid 마다 댓글 갯수
	//public int BoardCommentCountDao(Map<String, String> map);
	
	//boardid의 댓글, 대댓글 몰 리스트
	public List<BoardCommentVo> BoardCommentAllListDao();
	
	//BoardCommentBoardIdListDao
	public List<BoardCommentVo> BoardCommentBoardIdListDao(Map<String, String> map);
	
	//모바일용 조회 레이어1 댓글 정보와 그 댓글의 레이어2 댓글(대댓글) 수를 포함하여 리턴
	public List<BoardCommentLayer1InfoVo> BoardCommentLayer1ListDao(Map<String, String> map);
	
	//모바일용 조회 댓글에 딸린 레이어2 정보(대댓글) 조회
	public List<BoardCommentVo> BoardCommentLayer2ListDao(Map<String, String> map);
	
	//댓글 수정 삭제시 해당 댓글이 토큰 유저랑 캍은지 확인 하기위해 정보 서치
	public List<BoardCommentVo> BoardCommentcertifyDao(Map<String, String> map);
}