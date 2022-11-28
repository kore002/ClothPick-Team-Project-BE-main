package com.example.demo.security.vo;
//로그인 토큰 발급시 매챙 받는 Vo 아이디,비번만 받음
public class AuthRequestVo {
	private String username     ;
	private String password   ;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}