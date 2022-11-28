package com.example.demo.security.vo;
//이메일 인증 요청 시도시 Vo 
public class EmailVo {
	private String userEmail;
	private int codeType;

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


}