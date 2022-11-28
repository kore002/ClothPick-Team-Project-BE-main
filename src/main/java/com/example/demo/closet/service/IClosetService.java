package com.example.demo.closet.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.closet.vo.ClosetGetInfoVo;
import com.example.demo.closet.vo.ClosetInfoVo;
import com.example.demo.closet.vo.ConnectClosetImgVo;

public interface IClosetService {
	// IClosetImgInfoDao - 옷장 이미지 정보
	public String ClosetImgInfoUploadDao(MultipartFile file); // 옷 이미지 추가(작성)
	
	// IClosetInfoDao - 옷장 정보
	public List<ClosetGetInfoVo> ClosetInfoAllListDao(); 
	public String ClosetInfoWrite(String token, ClosetInfoVo closetInfoVo); // 옷 정보 작성
	public int ClosetInfoEditDao(ClosetInfoVo ClosetInfoVo, String clothId); // 옷 정보 수정
	public int ClosetInfoDeleteDao(String clothId); // 옷 정보 삭제
	
	// IConnectClosetImgDao - 옷,이미지 연결 테이블
	public List<ConnectClosetImgVo> ConnectClosetImgGetDao(); // 조회 String token
	public int ConnectClosetImgPostDao(String token, ConnectClosetImgVo ConnectClosetImgVo); // 작성
	public int ConnectClosetImgDeleteDao(String clothId); // 삭제
}