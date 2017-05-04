package com.yyf.www.project_quicknews.bean;


public class CommentBean {

	private int id;
	private NewsBean news;
	private UserBean user;
	private String date;
	private String comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NewsBean getNews() {
		return news;
	}

	public void setNews(NewsBean news) {
		this.news = news;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
