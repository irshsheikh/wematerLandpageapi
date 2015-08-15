package com.wemater.modal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wemater.dto.Article;
import com.wemater.dto.Comment;
import com.wemater.dto.User;

@XmlRootElement(name="comment")
public class CommentModel {

	private Long id;
	private String username;
	private String content;
	private String date;
	private ArticleModel articleModel;
	private UserModel userModel;
	private List<Link> links;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlTransient
	public ArticleModel getArticleModel() {
		return articleModel;
	}

	public void setArticleModel(ArticleModel articleModel) {
		this.articleModel = articleModel;
	}

	@XmlTransient
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public List<Link> getLinks() {
		if (this.links == null)
			this.links = new ArrayList<Link>();
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public CommentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public synchronized CommentModel ConstructModel(Comment comment) {

		this.id = comment.getId();
		this.username = comment.getUsername();
		this.content = comment.returnContentString();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		this.date = format.format(comment.getDate());
		return this;

	}

	public synchronized CommentModel addUser(User user) {
		UserModel model = new UserModel().constructModel(user);
		this.userModel = model;
		return this;
	}

	public synchronized CommentModel addArticle(Article article) {
		ArticleModel model = new ArticleModel().constructModel(article);
		this.articleModel = model;
		return this;
	}

	public synchronized CommentModel addLinks(Link... links) {
		List<Link> linkage = new ArrayList<Link>();
		for (Link link : links) {
			linkage.add(link);
		}
		this.setLinks(linkage);
		return this;

	}

}
