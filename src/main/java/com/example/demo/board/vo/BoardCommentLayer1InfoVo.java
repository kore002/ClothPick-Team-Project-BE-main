 package com.example.demo.board.vo;

public class BoardCommentLayer1InfoVo {
	private String boardId    ;
	private String boardType    ;
	private int userId	    ;
	private String userNickName    ;
	private String commentId ;
	private String commentContent    ;
	private int commentLayer	    ;
	private String commentCreateAt;
	private int childCommentCount;
	
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
	public int getChildCommentCount() {
		return childCommentCount;
	}
	public void setChildCommentCount(int childCommentCount) {
		this.childCommentCount = childCommentCount;
	}
	
	
}