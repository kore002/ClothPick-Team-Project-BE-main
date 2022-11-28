package com.example.demo.closet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.closet.vo.ConnectClosetImgVo;

@Mapper
public interface IConnectClosetImgDao {
	public List<ConnectClosetImgVo> ConnectClosetImgGetDao(); // 옷-이미지 연결 get
	public int ConnectClosetImgPostDao(Map<String, String> map); // 옷-이미지 연결 post
	public int ConnectClosetImgDeleteDao(Map<String, String> map); // 옷-이미지 연결 삭제
	// 옷-이미지 연결 수정
}
