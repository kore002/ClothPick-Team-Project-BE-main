package com.example.demo.security.vo;
//기본 유저 테이블 정보 쿼리 서치시 이걸로 받음 
public class UserVo {
	private int userId	    ;
	private String username     ;
	private String password   ;
	private String userRole        ;
	private String userEmail        ;
	private String emailCode;
	private String userNickName    ;
	private String userGender        ;
	private String userDOB        ;
	private String usercreateAT        ;
	private String userupdateAT        ;
	

	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
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
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserDOB() {
		return userDOB;
	}
	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}
	public String getUsercreateAT() {
		return usercreateAT;
	}
	public void setUsercreateAT(String usercreateAT) {
		this.usercreateAT = usercreateAT;
	}
	public String getUserupdateAT() {
		return userupdateAT;
	}
	public void setUserupdateAT(String userupdateAT) {
		this.userupdateAT = userupdateAT;
	}	

}