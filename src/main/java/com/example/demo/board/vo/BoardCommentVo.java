package com.example.demo.board.vo;

public class BoardCommentVo {
	private String boardId    ;
	private String boardType    ;
	private int userId	    ;
	private String username     ;
	private String userNickName    ;
	private String commentId ;
	private String commentParentsId;
	private String commentContent    ;
	private int commentLayer	    ;
	private String commentCreateAt;
	private String commentUpdateAt;
	
	
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
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentParentsId() {
		return commentParentsId;
	}
	public void setCommentParentsId(String commentParentsId) {
		this.commentParentsId = commentParentsId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getCommentLayer() {
		return commentLayer;
	}
	public void setCommentLayer(int commentLayer) {
		this.commentLayer = commentLayer;
	}
	public String getCommentCreateAt() {
		return commentCreateAt;
	}
	public void setCommentCreateAt(String commentCreateAt) {
		this.commentCreateAt = commentCreateAt;
	}
	public String getCommentUpdateAt() {
		return commentUpdateAt;
	}
	public void setCommentUpdateAt(String commentUpdateAt) {
		this.commentUpdateAt = commentUpdateAt;
	}
}