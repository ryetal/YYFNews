package com.yyf.www.project_quicknews.bean;

public class UserBean {

	private int id;
	private String userName;
	private String password;
	private String profilePhotoURL; // 头像
	private String description; // 描述

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePhotoURL() {
		return profilePhotoURL;
	}

	public void setProfilePhotoURL(String profilePhotoURL) {
		this.profilePhotoURL = profilePhotoURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
