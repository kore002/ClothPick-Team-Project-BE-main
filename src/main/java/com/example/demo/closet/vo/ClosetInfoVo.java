package com.example.demo.closet.vo;

public class ClosetInfoVo {
		private String clothId; // primary key
		private String clothType;
		private String clothDetail;
		private String[] clothColor;
		private String clothPattern;
		private String clothTexture;
		private String clothStyle;
		private String clothKeyword;
		private String clothPref;
		
		// foreign key
		private String username;
		private String userNickName;
		private String userId;
		public String getClothId() {
			return clothId;
		}
		
		// getter and setter
		public void setClothId(String clothId) {
			this.clothId = clothId;
		}
		public String getClothType() {
			return clothType;
		}
		public void setClothType(String clothType) {
			this.clothType = clothType;
		}
		public String getClothDetail() {
			return clothDetail;
		}
		public void setClothDetail(String clothDetail) {
			this.clothDetail = clothDetail;
		}
		public String[] getClothColor() {
			return clothColor;
		}
		public void setClothColor(String[] clothColor) {
			this.clothColor = clothColor;
		}
		public String getClothPattern() {
			return clothPattern;
		}
		public void setClothPattern(String clothPattern) {
			this.clothPattern = clothPattern;
		}
		public String getClothTexture() {
			return clothTexture;
		}
		public void setClothTexture(String clothTexture) {
			this.clothTexture = clothTexture;
		}
		public String getClothStyle() {
			return clothStyle;
		}
		public void setClothStyle(String clothStyle) {
			this.clothStyle = clothStyle;
		}
		public String getClothKeyword() {
			return clothKeyword;
		}
		public void setClothKeyword(String clothKeyword) {
			this.clothKeyword = clothKeyword;
		}
		public String getClothPref() {
			return clothPref;
		}
		public void setClothPref(String clothPref) {
			this.clothPref = clothPref;
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
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
}