package com.example.demo.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.vo.ConnectBoardImgVo;

@Mapper
public interface ConnectBoardImgDao {
	
	public int ConnectBoardImgPostDao(Map<String, String> map);
	//public int ConnectBoardImgPutDao(Map<String, String> map);
	public int ConnectBoardImgDeleteDao(Map<String, String> map);
	
	public List<ConnectBoardImgVo> ConnectBoardImgTypeListDao(Map<String, String> map);
	public List<ConnectBoardImgVo> ConnectBoardImgBoardIdListDao(Map<String, String> map);
	
}