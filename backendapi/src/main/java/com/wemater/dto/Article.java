package com.wemater.dto;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import com.wemater.util.Util;

@Table(name = "ARTICLE")
@Entity
@NamedQueries(value = {

		@NamedQuery(name = "article.getArticleByUsernameAndArticleId", query = "select a "
				+ "from Article a "
				+ "where a.user.username = :username and a.id = :articleId"),

		@NamedQuery(name = "article.getAllArticlesByUsername", query = "select a "
				+ "from Article a " + "where a.user.username = :username order by a.id desc"),
		@NamedQuery(name = "article.getAllArticlesWithNoContentByUsername", query = "select a.id, a.title,a.url,a.date, "
						+ "a.commentCount,a.likes from Article a " + "where a.user.username = :username order by a.id desc"),		

		@NamedQuery(name = "article.IsArticleCommentAvailable", query = "select article.id from Article as article"
				+ " inner join article.comments as comment on comment.id = :id ") })
public class Article {

	private long id;
	private String title;
	private String url;
	private Clob image;
	private Clob content;
	private List<String> tags;
	private Date date;
	private int likes;
	private List<String> likedUser;
	private int commentCount;
	private User user;
	private List<Comment> comments;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTICLE_ID", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "TITLE")
	@NaturalId(mutable = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "IMAGE")
	private Clob getImage() {
		return image;
	}

	private void setImage(Clob image) {
		this.image = image;
	}

	@Column(name = "content")
	private Clob getContent() {
		return content;
	}

	private void setContent(Clob content) {
		this.content = content;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "ARTICLE_TAGS", joinColumns = { @JoinColumn(name = "ARTICLE_ID") })
	@GenericGenerator(name = "hi_lo", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "TAG_ID") }, generator = "hi_lo", type = @Type(type = "long"))
	public List<String> getTags() {
		if (this.tags == null)
			tags = new ArrayList<String>();
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

   
	@ElementCollection
	@JoinTable(name = "LIKED_USER", joinColumns = { @JoinColumn(name = "ARTICLE_ID") })
	@GenericGenerator(name = "hi_lo", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "USER_ID") }, generator = "hi_lo", type = @Type(type = "long"))
	public List<String> getLikedUser() {
		if(this.likedUser == null) this.likedUser = new ArrayList<String>();
		return likedUser;
	}

	public void setLikedUser(List<String> likedUser) {
		this.likedUser = likedUser;
	}

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "LIKES")
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		System.err.println("set likes is called here");
		this.likes = likes;

	}

	@Column(name = "COMMENT_COUNT")
	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	public List<Comment> getComments() {
		if (this.comments == null)
			this.comments = new ArrayList<Comment>();
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;

	}

	// add the tags
	public void addTags(List<String> tags) {
		this.tags = tags;
	}

	// utilty method to map the user and article for onetomany relationship
	public void mapUserAndArticle(User user) {
		user.getArticles().add(this);
		user.setArticleCount(user.getArticles().size());
		this.setUser(user);

	}

	public String returnImageString() {

		return Util.convertClobToString(this.getImage());

	}

	public void createImageString(String image) {

		this.setImage(Util.convertStringToClob(image));

	}

	public String returnContentString() {
		return Util.convertClobToString(this.getContent());

	}

	public void createContentString(String content) {

		this.setContent(Util.convertStringToClob(content));
	}

	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

}
