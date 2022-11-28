package com.example.demo.closet.vo;

public class ConnectClosetImgVo {
	// foreign key
	private String clothImgName;
	private String clothId;
	private String createAt;
	private String updateAt;
	
	// getter and setter
	public String getClothImgName() {
		return clothImgName;
	}
	public void setClothImgName(String clothImgName) {
		this.clothImgName = clothImgName;
	}
	public String getClothId() {
		return clothId;
	}
	public void setClothId(String clothId) {
		this.clothId = clothId;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
}
