package com.ergon.exercise.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;

@Entity
@Table(name="tbl_task")
@Getter
@Setter
@ToString
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String description;
	
	private Long id_project;
	

	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "task")
	private Set<UserTask> userTasks = new HashSet<>();

	
	@JsonIgnore
	@OneToMany(mappedBy = "task")
    private Set<Comment> relatedComments = new HashSet<>();


	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status",  referencedColumnName = "id_status")
    private Status status;
	
	
	public void addTask(UserTask userTask) {
		this.userTasks.add(userTask);
	}
	
	
	public void addComment(Comment comment) {
		this.relatedComments.add(comment);
	}
	
	

	
	
	
	//Getter & setter
	
	public Long getId_task() {
		return id;
	}

	public void setId_task(Long id_task) {
		this.id = id_task;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId_project() {
		return id_project;
	}

	public void setId_project(Long project_id) {
		this.id_project = project_id;
	}

	/*
	public Set<User> getUsers() {
		return users;
	}*/

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	public Set<UserTask> getUserTasks() {
		return userTasks;
	}
	
	public Set<Comment> getRelatedComments() {
		return relatedComments;
	}
}
