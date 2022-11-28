package com.example.demo.security.vo;
//이메일 인증 확인 체크를 위한 Vo
public class AuthenticationCodeVo {
	private int codeType	    ;
	private String emailCode     ;
	private int authenticationStatus;
	private String userEmail;
	
	public int getCodeType() {
		return codeType;
	}
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	public int getAuthenticationStatus() {
		return authenticationStatus;
	}
	public void setAuthenticationStatus(int authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}