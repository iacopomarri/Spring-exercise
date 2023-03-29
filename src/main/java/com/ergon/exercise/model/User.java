package com.ergon.exercise.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="tbl_user")
@Getter
@Setter
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String username;
	
	private String password;
	
	private String name;
	
	@CreationTimestamp
	@Column(name="created_at", nullable=false, updatable=false)
	private Date createdAt;
	

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserTask> userTasks = new HashSet<>();

	
	@OneToMany(mappedBy = "user")
	private Set<Comment> postedComments = new HashSet<>();
	
	
	public void addTask(UserTask userTask) {
		this.userTasks.add(userTask);
	}
	
	
	public void addComment(Comment comment) {
		this.postedComments.add(comment);
	}
	
	
	


	
	
	//Getter and setter
	
	public Long getId_user() {
		return id;
	}

	public void setId_user(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/*
	public Set<Task> getTasks() {
		return tasks;
	}*/

	public Set<Comment> getCommentPostings() {
		return postedComments;
	}
	
	public Set<UserTask> getUserTasks() {
		return userTasks;
	}

}