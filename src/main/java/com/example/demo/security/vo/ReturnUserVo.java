package com.example.demo.security.vo;

import java.time.LocalDateTime;
// 유저 정보 확인 작업에서 리턴에 필요한 Vo
public class ReturnUserVo {
	private int userId	    ;
	private String username     ;
	private String userRole        ;
	private String userNickName    ;
	private String userGender        ;
	private String userDOB        ;
	private String userupdateAT        ;
	
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
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
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
	public String getUserupdateAT() {
		return userupdateAT;
	}
	public void setUserupdateAT(String userupdateAT) {
		this.userupdateAT = userupdateAT;
	}
	


}