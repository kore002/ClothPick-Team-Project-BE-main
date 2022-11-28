package com.example.demo.board.vo;

public class BoardTextInfoResultVo {
	private String boardId    ;
	private String boardType    ;
	private String boardTitle   ;
	private String boardContent;
	private String boardCreateAt;
	private int boardRecommendCount;
	private String userId;
	private String userNickname;
	private int commentCount;
	
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
	public int getBoardRecommendCount() {
		return boardRecommendCount;
	}
	public void setBoardRecommendCount(int boardRecommendCount) {
		this.boardRecommendCount = boardRecommendCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	
}