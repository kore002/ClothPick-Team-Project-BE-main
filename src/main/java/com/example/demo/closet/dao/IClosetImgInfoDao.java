package com.example.demo.closet.dao;

//import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.closet.vo.ClosetImgInfoVo;

@Mapper
public interface IClosetImgInfoDao {
	public int ClosetImgInfoUploadDao(Map<String, String> map); // 옷 이미지 추가(작성)
//	public String ClosetImgInfoGetDao(); // 옷 이미지 정보 get
}
