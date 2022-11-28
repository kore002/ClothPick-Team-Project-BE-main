package com.example.demo.board.vo;

public class BoardImgInfoVo {
	private String fileUniqueName    ;
	private String fileRealName    ;
	private String fileSize   ;
	private String fileExtension;
	private String fileUploadFolder;
	private String fileUploadTime;
	
	
	public String getFileUniqueName() {
		return fileUniqueName;
	}
	public void setFileUniqueName(String fileUniqueName) {
		this.fileUniqueName = fileUniqueName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFileUploadFolder() {
		return fileUploadFolder;
	}
	public void setFileUploadFolder(String fileUploadFolder) {
		this.fileUploadFolder = fileUploadFolder;
	}
	public String getFileUploadTime() {
		return fileUploadTime;
	}
	public void setFileUploadTime(String fileUploadTime) {
		this.fileUploadTime = fileUploadTime;
	}
	
}