package com.example.demo.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.vo.BoardImgInfoVo;


@Mapper
public interface BoardImgInfoDao {
	//이미지 파일 정보 업로드
	public int BoardImgInfoUploadDao(Map<String, String> map);
	public List<BoardImgInfoVo> BoardImgInfoSearchDao(Map<String, String> map);
}