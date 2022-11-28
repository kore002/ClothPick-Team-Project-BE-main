package com.example.demo.board.vo;

public class BoardTextInfoVo {
	private String boardId    ;
	private String boardType    ;
	private String boardTitle   ;
	private String boardContent;
	private String boardCreateAt;
	private String boardUpdateAt;
	private int boardRecommendCount;
	private String userId;
	private String username;
	private String userNickname;
	private int commentCount;
	
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardCreateAt() {
		return boardCreateAt;
	}
	public void setBoardCreateAt(String boardCreateAt) {
		this.boardCreateAt = boardCreateAt;
	}
	public String getBoardUpdateAt() {
		return boardUpdateAt;
	}
	public void setBoardUpdateAt(String boardUpdateAt) {
		this.boardUpdateAt = boardUpdateAt;
	}
	public int getBoardRecommendCount() {
		return boardRecommendCount;
	}
	public void setBoardRecommendCount(int boardRecommendCount) {
		this.boardRecommendCount = boardRecommendCount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	
}