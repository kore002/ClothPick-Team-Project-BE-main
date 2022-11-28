package com.example.demo.security.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.security.vo.AuthenticationCodeVo;

@Mapper
public interface AuthenticationDao {
	//인증 정보 작성 수정 삭제
	public int AuthenticationInfoPost(Map<String, String> map);
	public int AuthenticationInfoPut(Map<String, String> map);
	public int AuthenticationInfoDelete(Map<String, String> map);
	
	// 인증 상태 서치
	public AuthenticationCodeVo AuthenticationStatusCheck(Map<String, String> map);
}