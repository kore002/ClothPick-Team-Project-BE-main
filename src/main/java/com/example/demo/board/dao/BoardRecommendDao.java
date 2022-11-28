package com.example.demo.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.vo.BoardRecommendVo;

@Mapper
public interface BoardRecommendDao {
	public int BoardRecommendPostDao(Map<String, String> map);
	//public int BoardRecommendPutDao(Map<String, String> map);
	public int BoardRecommendDeleteDao(Map<String, String> map);
	
	
	// 글아디디랑 우저 앙디 동시 검색 할듯 
	public int BoardRecommendSerchDao(Map<String, String> map);
	public int BoardRecommendCountSerchDao(Map<String, String> map);
}