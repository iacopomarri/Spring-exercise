//This class represents the N to N relationship between User and Task

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
@Table(name="tbl_userTask")
@Getter
@Setter
@ToString
public class UserTask {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//Define the N to 1 relationship with User
	@ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    
	//Define the N to 1 relationship with Task
    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;
    
    private float hours;

    
    
    
    //Getter & setter
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	} 
	
	
	
}
