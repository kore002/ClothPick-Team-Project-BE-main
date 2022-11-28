package com.example.demo.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.vo.BoardTextInfoResultVo;
import com.example.demo.board.vo.BoardTextInfoVo;

@Mapper
public interface BoardTextInfoDao {
	public List<BoardTextInfoVo> BoardTextAllListDao();
	public List<BoardTextInfoResultVo> BoardTextTypeListDao(Map<String, String> map);
	
	public int BoardTextRecommendUpDao(Map<String, String> map);
	public int BoardTextRecommendDawnDao(Map<String, String> map);
	public int BoardTextPostDao(Map<String, String> map);
	public int BoardTextPutDao(Map<String, String> map);
	public int BoardTextDeleteDao(Map<String, String> map); // 게시글 삭제
	// 게시판별 추천수 랭킹
	public List<BoardTextInfoVo> BoardRecommendRankingDao();

}